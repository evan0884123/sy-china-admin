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
        redisTemplate.opsForHash()
                .put(RedisLock.PlayersIDMap, players.getId().toString(), JSON.toJSONString(players));

        return ResultModel.succeed();
    }

    public ResultModel delete(Long id) {

        Players players = baseMapper.selectById(id);
        Assert.notNull(players, "未查到此玩家");
        players.setStatus(0);

        updateById(players);
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
                switch (scoreParam.getType()) {
                    case 0:
                        updateUseBalance(players, scoreParam);
                        break;
                    case 1:
                        updateWithdrawBalance(players, scoreParam);
                        break;
                    case 2:
                        updatePromoteBalance(players, scoreParam);
                        break;
                    case 3:
                        updateProjectBalance(players, scoreParam);
                        break;
                    case 4:
                        updateShareMoneyProfit(players, scoreParam);
                        break;
                }
                redisTemplate.opsForHash()
                        .put(RedisLock.PlayersIDMap, players.getId().toString(), JSON.toJSONString(players));
            } catch (Exception e) {
                log.error("[WITHDRAW_APPLY][ERROR] action amount error", e);
            } finally {
                lockUtil.unlock(lockKey);
            }
        });

        return ResultModel.succeed();
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateUseBalance(Players players, TopOrLowerScoreParam scoreParam) {

        BigDecimal totalRecharge, acBalance;
        List<Players> playersList = new ArrayList<>();
        List<AccountChanges> changesList = new ArrayList<>();
        if (scoreParam.getOperationType() == 0) {
            acBalance = players.getUseBalance().add(scoreParam.getScore());
            totalRecharge = players.getTotalRecharge().add(scoreParam.getScore());

            if (StringUtils.isNotBlank(players.getLevelInfo())) {
                String[] split = players.getLevelInfo().split("/");
                // 一级返佣 20%
                Players superior = baseMapper.selectById(Long.valueOf(split[split.length - 1]));
                BigDecimal superiorRebate = scoreParam.getScore().multiply(new BigDecimal("0.2"));
                BigDecimal superiorBalance = superior.getPromoteBalance().add(superiorRebate);
                changesList.add(convert(superior, superior.getPromoteBalance(), superiorRebate, superiorBalance, 2, 5, "推广返佣"));
                superior.setPromoteBalance(superiorBalance);
                playersList.add(superior);

                if (split.length >= 2) {
                    // 二级返佣 10%
                    Players superiorTwo = baseMapper.selectById(Long.valueOf(split[split.length - 2]));
                    BigDecimal superiorTwoRebate = scoreParam.getScore().multiply(new BigDecimal("0.1"));
                    BigDecimal superiorTwoBalance = superiorTwo.getPromoteBalance().add(superiorTwoRebate);
                    changesList.add(convert(superiorTwo, superiorTwo.getPromoteBalance(), superiorTwoRebate, superiorTwoBalance, 2, 5, "推广返佣"));
                    superiorTwo.setPromoteBalance(superiorTwoBalance);
                    playersList.add(superiorTwo);
                }

            }
        } else {
            acBalance = players.getUseBalance().subtract(scoreParam.getScore());
            totalRecharge = players.getTotalRecharge().subtract(scoreParam.getScore());
        }
        changesList.add(convert(players, scoreParam, players.getUseBalance(), acBalance));
        players.setUseBalance(acBalance);
        players.setTotalRecharge(totalRecharge);
        playersList.add(players);

        saveOrUpdateBatch(playersList);
        accountChangeService.saveOrUpdateBatch(changesList);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateWithdrawBalance(Players players, TopOrLowerScoreParam scoreParam) {

        BigDecimal acBalance;
        if (scoreParam.getOperationType() == 0) {
            acBalance = players.getWithdrawBalance().add(scoreParam.getScore());
        } else {
            acBalance = players.getWithdrawBalance().subtract(scoreParam.getScore());
        }
        AccountChanges accountChanges = convert(players, scoreParam, players.getWithdrawBalance(), acBalance);
        players.setWithdrawBalance(acBalance);

        saveOrUpdate(players);
        accountChangeService.saveOrUpdate(accountChanges);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updatePromoteBalance(Players players, TopOrLowerScoreParam scoreParam) {

        BigDecimal acBalance;
        if (scoreParam.getOperationType() == 0) {
            acBalance = players.getPromoteBalance().add(scoreParam.getScore());
        } else {
            acBalance = players.getPromoteBalance().subtract(scoreParam.getScore());
        }
        AccountChanges accountChanges = convert(players, scoreParam, players.getPromoteBalance(), acBalance);
        players.setPromoteBalance(acBalance);

        saveOrUpdate(players);
        accountChangeService.saveOrUpdate(accountChanges);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateProjectBalance(Players players, TopOrLowerScoreParam scoreParam) {

        BigDecimal totalRecharge, acBalance;
        if (scoreParam.getOperationType() == 0) {
            acBalance = players.getProjectBalance().add(scoreParam.getScore());
            totalRecharge = players.getProjectTotalBalance().add(scoreParam.getScore());
        } else {
            acBalance = players.getProjectBalance().subtract(scoreParam.getScore());
            totalRecharge = players.getProjectTotalBalance().subtract(scoreParam.getScore());
        }
        AccountChanges accountChanges = convert(players, scoreParam, players.getProjectBalance(), acBalance);
        players.setProjectBalance(acBalance);
        players.setProjectTotalBalance(totalRecharge);

        saveOrUpdate(players);
        accountChangeService.saveOrUpdate(accountChanges);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateShareMoneyProfit(Players players, TopOrLowerScoreParam scoreParam) {

        BigDecimal acBalance;
        if (scoreParam.getOperationType() == 0) {
            acBalance = players.getShareMoneyProfit().add(scoreParam.getScore());
        } else {
            acBalance = players.getShareMoneyProfit().subtract(scoreParam.getScore());
        }
        AccountChanges accountChanges = convert(players, scoreParam, players.getShareMoneyProfit(), acBalance);
        players.setShareMoneyProfit(acBalance);

        saveOrUpdate(players);
        accountChangeService.saveOrUpdate(accountChanges);
    }

    private AccountChanges convert(Players players, TopOrLowerScoreParam scoreParam, BigDecimal bcBalance, BigDecimal acBalance) {

        AccountChanges accountChanges = new AccountChanges()
                .setPlayer(players.getId())
                .setPlayerName(players.getAccount())
                .setAmountType(scoreParam.getType())
                .setBcBalance(bcBalance)
                .setAmount(scoreParam.getScore())
                .setAcBalance(acBalance)
                .setChangeType(scoreParam.getOperationType())
                .setChangeDescribe(scoreParam.getRemark())
                .setConnId(players.getId().toString())
                .setCreate(LocalDateTimeHelper.toLong(LocalDateTime.now()));

        return accountChanges;

    }

    private AccountChanges convert(Players players, BigDecimal bcBalance, BigDecimal balance, BigDecimal acBalance,
                                   Integer amountType, Integer chargeType, String remark) {

        AccountChanges accountChanges = new AccountChanges()
                .setPlayer(players.getId())
                .setPlayerName(players.getAccount())
                .setAmountType(amountType)
                .setBcBalance(bcBalance)
                .setAmount(balance)
                .setAcBalance(acBalance)
                .setChangeType(chargeType)
                .setChangeDescribe(remark)
                .setConnId(players.getId().toString())
                .setCreate(LocalDateTimeHelper.toLong(LocalDateTime.now()));

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
