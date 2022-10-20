package com.sychina.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.common.RedisLock;
import com.sychina.admin.infra.domain.AccountChanges;
import com.sychina.admin.infra.domain.Players;
import com.sychina.admin.infra.domain.WithdrawApply;
import com.sychina.admin.infra.mapper.WithdrawApplyMapper;
import com.sychina.admin.service.IWithdrawApplyService;
import com.sychina.admin.utils.RedisLockUtil;
import com.sychina.admin.web.pojo.models.WithdrawApplyTable;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.WithdrawApplyParam;
import com.sychina.admin.web.pojo.params.WithdrawApplyQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class WithdrawApplyServiceImpl extends ServiceImpl<WithdrawApplyMapper, WithdrawApply> implements IWithdrawApplyService {

    private PlayerServiceImpl playerService;

    private AccountChangeServiceImpl accountChangeService;

    private RedisTemplate redisTemplate;

    private RedisLockUtil lockUtil;

    public ResultModel loadTable(WithdrawApplyQuery recordQuery) {

        QueryWrapper<WithdrawApply> wrapper = new QueryWrapper<>();
        wrapper.likeRight(StringUtils.isNotBlank(recordQuery.getPlayerName()), "player_name", recordQuery.getPlayerName());
        wrapper.eq(recordQuery.getChargeChannel() != null, "charge_channel", recordQuery.getChargeChannel());
        wrapper.eq(recordQuery.getWdType() != null, "wd_type", recordQuery.getWdType());
        wrapper.eq(recordQuery.getStatus() != null, "status", recordQuery.getStatus());
        wrapper.between(recordQuery.getTimeType() == 0, "`create`", recordQuery.getStartTime(), recordQuery.getEndTime());
        wrapper.between(recordQuery.getTimeType() == 1, "`update`", recordQuery.getStartTime(), recordQuery.getEndTime());

        IPage page = baseMapper.selectPage(recordQuery.page(), wrapper);

        List<WithdrawApplyTable> tables = new ArrayList<>();
        List<WithdrawApply> records = page.getRecords();
        records.forEach(record -> {
            tables.add(new WithdrawApplyTable(record));
        });
        page.setRecords(tables);

        return ResultModel.succeed(page);
    }

    public ResultModel edit(WithdrawApplyParam withdrawApplyParam) {

        List<WithdrawApply> withdrawApplies = baseMapper.selectBatchIds(withdrawApplyParam.getIds());
        withdrawApplies.forEach(withdrawApply -> {

            String lockKey = RedisLock.playBalanceChange + withdrawApply.getPlayer();
            lockUtil.tryLock(lockKey, 15);
            try {
                actionAmount(withdrawApply, withdrawApplyParam);
            } catch (Exception e) {
                log.error("[WITHDRAW_APPLY][ERROR] action amount error", e);
            } finally {
                lockUtil.unlock(lockKey);
            }
        });

        return ResultModel.succeed();
    }

    @Transactional(rollbackFor = Exception.class)
    public void actionAmount(WithdrawApply withdrawApply, WithdrawApplyParam withdrawApplyParam) {

        if (!withdrawApply.getStatus().equals(withdrawApplyParam.getStatus())) {
            if (withdrawApplyParam.getStatus() == 2) {
                BigDecimal balance = BigDecimal.ZERO;
                Integer amountType = null;
                Players players = playerService.getById(withdrawApply.getPlayer());
                switch (withdrawApply.getWdType()) {
                    case 0:
                        balance = players.getProjectBalance().subtract(withdrawApply.getAmount());
                        players.setProjectBalance(balance);
                        amountType = 3;
                        break;
                    case 1:
                        balance = players.getPromoteBalance().subtract(withdrawApply.getAmount());
                        players.setPromoteBalance(balance);
                        amountType = 2;
                        break;
                    case 2:
                        balance = players.getWithdrawBalance().subtract(withdrawApply.getAmount());
                        players.setWithdrawBalance(balance);
                        amountType = 1;
                        break;
                }
                if (balance.compareTo(BigDecimal.ZERO) < 0) {
                    return;
                }
                playerService.saveOrUpdate(players);
                redisTemplate.opsForHash().put(RedisLock.PlayersIDMap, players.getId().toString(), JSON.toJSONString(players));

                AccountChanges accountChanges = convert(withdrawApply, players, withdrawApplyParam, balance, amountType);
                accountChangeService.saveOrUpdate(accountChanges);
            }
            withdrawApply.setStatus(withdrawApplyParam.getStatus());
        }
        withdrawApply.setRemark(withdrawApplyParam.getRemark());

        saveOrUpdate(withdrawApply);
    }

    private AccountChanges convert(WithdrawApply withdrawApply, Players players,
                                   WithdrawApplyParam withdrawApplyParam, BigDecimal balance,
                                   Integer amountType) {

        AccountChanges accountChanges = new AccountChanges()
                .setPlayer(players.getId())
                .setPlayerName(players.getAccount())
                .setAmountType(amountType)
                .setBcBalance(players.getWithdrawBalance())
                .setAmount(withdrawApply.getAmount())
                .setAcBalance(balance)
                .setChangeType(1)
                .setChangeDescribe(withdrawApplyParam.getRemark())
                .setConnId(withdrawApply.getId().toString());

        return accountChanges;

    }

    @Autowired
    public void setLockUtil(RedisLockUtil lockUtil) {
        this.lockUtil = lockUtil;
    }

    @Autowired
    public void setPlayerService(PlayerServiceImpl playerService) {
        this.playerService = playerService;
    }

    @Autowired
    public void setAccountChangeService(AccountChangeServiceImpl accountChangeService) {
        this.accountChangeService = accountChangeService;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
