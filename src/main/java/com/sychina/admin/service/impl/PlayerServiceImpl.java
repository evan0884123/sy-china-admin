package com.sychina.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.common.RedisLock;
import com.sychina.admin.infra.domain.AccountChanges;
import com.sychina.admin.infra.domain.Players;
import com.sychina.admin.infra.mapper.PlayerMapper;
import com.sychina.admin.service.IPlayerService;
import com.sychina.admin.utils.LocalDateTimeHelper;
import com.sychina.admin.utils.RedisLockUtil;
import com.sychina.admin.web.pojo.SelectOption;
import com.sychina.admin.web.pojo.models.PlayerTable;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.PlayerParam;
import com.sychina.admin.web.pojo.params.PlayerQuery;
import com.sychina.admin.web.pojo.params.TopOrLowerScoreParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class PlayerServiceImpl extends ServiceImpl<PlayerMapper, Players> implements IPlayerService {

    private AccountChangeServiceImpl accountChangeService;

    private RedisTemplate redisTemplate;

    private RedisLockUtil lockUtil;

    public ResultModel loadTable(PlayerQuery playerQuery) {

        QueryWrapper<Players> wrapper = new QueryWrapper<>();
        wrapper.likeRight(StringUtils.isNotBlank(playerQuery.getAccount()), "account", playerQuery.getAccount());
        wrapper.eq(playerQuery.getVIp() != null, "v_ip", playerQuery.getVIp());
        wrapper.like(StringUtils.isNotBlank(playerQuery.getLevelInfo()), "level_info", playerQuery.getLevelInfo());
        wrapper.likeRight(StringUtils.isNotBlank(playerQuery.getRealName()), "real_name", playerQuery.getRealName());
        wrapper.likeRight(StringUtils.isNotBlank(playerQuery.getIdNumber()), "id_number", playerQuery.getIdNumber());
        wrapper.likeRight(StringUtils.isNotBlank(playerQuery.getPhoneNumber()), "phone_number", playerQuery.getPhoneNumber());
        wrapper.eq(playerQuery.getIsVerifyManager() != null, "is_verify_manager", playerQuery.getIsVerifyManager());
        wrapper.eq(playerQuery.getStatus() != null, "status", playerQuery.getStatus());
        wrapper.between(playerQuery.getTimeType() == 0, "`create`", playerQuery.getStartTime(), playerQuery.getEndTime());
        wrapper.between(playerQuery.getTimeType() == 1, "`update`", playerQuery.getStartTime(), playerQuery.getEndTime());
        wrapper.between(playerQuery.getTimeType() == 2, "`last_login_time`", playerQuery.getStartTime(), playerQuery.getEndTime());

        IPage page = baseMapper.selectPage(playerQuery.page(), wrapper);

        List<PlayerTable> tables = new ArrayList<>();
        List<Players> records = page.getRecords();
        records.forEach(record -> {
            tables.add(new PlayerTable(record));
        });
        page.setRecords(tables);

        return ResultModel.succeed(page);
    }

    public ResultModel edit(PlayerParam playerParam) {

        Assert.notNull(playerParam.getId(), "id不能为空");

        Players players = playerParam.convert()
                .setId(playerParam.getId())
                .setUpdate(LocalDateTimeHelper.toLong(LocalDateTime.now()));

        baseMapper.updateById(players);
        redisTemplate.opsForHash().put(RedisLock.PlayersIDMap, players.getId().toString(), JSON.toJSONString(players));

        return ResultModel.succeed();
    }

    public ResultModel delete(Long id) {

        baseMapper.deleteById(id);

        redisTemplate.opsForHash().delete(RedisLock.PlayersIDMap, id.toString());

        return ResultModel.succeed();
    }

    public ResultModel<List<SelectOption>> fetchPlayerOptions(String account) {

        List<Players> playersList = baseMapper.selectList(new QueryWrapper<Players>().likeRight("account", account));
        List<SelectOption> playerSelect = new ArrayList<>();

        playersList.forEach(players -> {
            SelectOption selectOption = new SelectOption();
            selectOption.setLabel(players.getAccount());
            selectOption.setValue(players.getId().toString());
            playerSelect.add(selectOption);
        });

        return ResultModel.succeed(playerSelect);
    }

    public ResultModel topOrLowerScore(TopOrLowerScoreParam scoreParam) {

        List<Players> playersList = baseMapper.selectBatchIds(scoreParam.getIds());
        playersList.forEach(players -> {

            String lockKey = RedisLock.playBalanceChange + players.getId();
            lockUtil.tryLock(lockKey, 15);
            try {
                actionAmount(players, scoreParam);
                redisTemplate.opsForHash().put(RedisLock.PlayersIDMap, players.getId().toString(), JSON.toJSONString(players));
            } catch (Exception e) {
                log.error("[WITHDRAW_APPLY][ERROR] action amount error", e);
            } finally {
                lockUtil.unlock(lockKey);
            }
        });

        return ResultModel.succeed();
    }

    @Transactional(rollbackFor = Exception.class)
    public void actionAmount(Players players, TopOrLowerScoreParam scoreParam) {
        BigDecimal balance = BigDecimal.ZERO;
        switch (scoreParam.getType()) {
            case 0:
                BigDecimal totalRecharge = BigDecimal.ZERO;
                if (scoreParam.getOperationType() == 0) {
                    balance = players.getUseBalance().add(scoreParam.getScore());
                    totalRecharge = players.getTotalRecharge().add(scoreParam.getScore());
                } else {
                    balance = players.getUseBalance().subtract(scoreParam.getScore());
                    totalRecharge = players.getTotalRecharge().subtract(scoreParam.getScore());
                }
                players.setUseBalance(balance);
                players.setTotalRecharge(totalRecharge);
                break;
            case 1:
                if (scoreParam.getOperationType() == 0) {
                    balance = players.getWithdrawBalance().add(scoreParam.getScore());
                } else {
                    balance = players.getWithdrawBalance().subtract(scoreParam.getScore());
                }
                players.setWithdrawBalance(balance);
                break;
            case 2:
                if (scoreParam.getOperationType() == 0) {
                    balance = players.getPromoteBalance().add(scoreParam.getScore());
                } else {
                    balance = players.getPromoteBalance().subtract(scoreParam.getScore());
                }
                players.setPromoteBalance(balance);
                break;
            case 3:
                if (scoreParam.getOperationType() == 0) {
                    balance = players.getProjectBalance().add(scoreParam.getScore());
                } else {
                    balance = players.getProjectBalance().subtract(scoreParam.getScore());
                }
                players.setProjectBalance(balance);
                break;
        }
        AccountChanges accountChanges = convert(players, scoreParam, balance, scoreParam.getType(), scoreParam.getOperationType());

        saveOrUpdate(players);
        accountChangeService.saveOrUpdate(accountChanges);

    }

    private AccountChanges convert(Players players, TopOrLowerScoreParam scoreParam, BigDecimal useBalance,
                                   Integer amountType, Integer chargeType) {

        AccountChanges accountChanges = new AccountChanges()
                .setPlayer(players.getId())
                .setPlayerName(players.getAccount())
                .setAmountType(amountType)
                .setBcBalance(players.getUseBalance())
                .setAmount(scoreParam.getScore())
                .setAcBalance(useBalance)
                .setChangeType(chargeType)
                .setChangeDescribe(scoreParam.getRemark())
                .setConnId(players.getId().toString());

        return accountChanges;

    }

    @Autowired
    public void setAccountChangeService(AccountChangeServiceImpl accountChangeService) {
        this.accountChangeService = accountChangeService;
    }

    @Autowired
    public void setLockUtil(RedisLockUtil lockUtil) {
        this.lockUtil = lockUtil;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
