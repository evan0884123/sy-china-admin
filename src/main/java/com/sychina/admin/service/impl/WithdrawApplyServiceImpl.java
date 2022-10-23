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
import com.sychina.admin.utils.LocalDateTimeHelper;
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
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
        wrapper.eq(recordQuery.getType() != null, "type", recordQuery.getType());
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
                throw e;
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
                Players players = playerService.getById(withdrawApply.getPlayer());
                AccountChanges accountChanges = null;
                BigDecimal balance = BigDecimal.ZERO;
                if (withdrawApply.getType() == 0) {
                    balance = players.getUseBalance().add(withdrawApply.getAmount());
                    BigDecimal totalRecharge = players.getTotalRecharge().add(withdrawApply.getAmount());

                    List<Players> playersList = new ArrayList<>();
                    List<AccountChanges> changesList = new ArrayList<>();
                    if (StringUtils.isNotBlank(players.getLevelInfo())) {
                        String[] split = players.getLevelInfo().split("/");
                        // 一级返佣 20%
                        Players superior = playerService.getById(Long.valueOf(split[split.length - 1]));
                        BigDecimal superiorRebate = withdrawApply.getAmount().multiply(new BigDecimal("0.2"));
                        BigDecimal superiorBalance = superior.getPromoteBalance().add(superiorRebate);
                        changesList.add(convert(superior, superior.getPromoteBalance(), superiorRebate, superiorBalance, 2, 5, "推广返佣"));
                        superior.setPromoteBalance(superiorBalance);
                        playersList.add(superior);

                        if (split.length >= 2) {
                            // 二级返佣 10%
                            Players superiorTwo = playerService.getById(Long.valueOf(split[split.length - 2]));
                            BigDecimal superiorTwoRebate = withdrawApply.getAmount().multiply(new BigDecimal("0.1"));
                            BigDecimal superiorTwoBalance = superiorTwo.getPromoteBalance().add(superiorTwoRebate);
                            changesList.add(convert(superiorTwo, superiorTwo.getPromoteBalance(), superiorTwoRebate, superiorTwoBalance, 2, 5, "推广返佣"));
                            superiorTwo.setPromoteBalance(superiorTwoBalance);
                            playersList.add(superiorTwo);
                        }

                    }

                    changesList.add(convert(players, withdrawApply, withdrawApplyParam, players.getUseBalance(), balance, 0, 0));
                    players.setUseBalance(balance);
                    players.setTotalRecharge(totalRecharge);
                    playersList.add(players);

                    accountChangeService.saveOrUpdateBatch(changesList);
                    playerService.saveOrUpdateBatch(playersList);

                    playersList.forEach(players1 -> {
                        redisTemplate.opsForHash().put(RedisLock.PlayersIDMap, players1.getId().toString(), JSON.toJSONString(players1));
                    });

                } else if (withdrawApply.getType() == 1) {
                    Integer amountType = null;
                    switch (withdrawApply.getWdType()) {
                        case 0:
                            balance = players.getProjectBalance().subtract(withdrawApply.getAmount());
                            amountType = 3;
                            accountChanges = convert(players, withdrawApply, withdrawApplyParam, players.getProjectBalance(), balance, amountType, 1);
                            players.setProjectBalance(balance);
                            break;
                        case 1:
                            balance = players.getPromoteBalance().subtract(withdrawApply.getAmount());
                            amountType = 2;
                            accountChanges = convert(players, withdrawApply, withdrawApplyParam, players.getPromoteBalance(), balance, amountType, 1);
                            players.setPromoteBalance(balance);
                            break;
                        case 2:
                            balance = players.getWithdrawBalance().subtract(withdrawApply.getAmount());
                            amountType = 1;
                            accountChanges = convert(players, withdrawApply, withdrawApplyParam, players.getWithdrawBalance(), balance, amountType, 1);
                            players.setWithdrawBalance(balance);
                            break;
                        case 3:
                            balance = players.getShareMoneyProfit().subtract(withdrawApply.getAmount());
                            amountType = 1;
                            accountChanges = convert(players, withdrawApply, withdrawApplyParam, players.getShareMoneyProfit(), balance, amountType, 1);
                            players.setShareMoneyProfit(balance);
                            break;
                    }
                    Assert.isTrue(balance.compareTo(BigDecimal.ZERO) >= 0, "变动后金额不能成负数");
                    accountChangeService.saveOrUpdate(accountChanges);
                    playerService.saveOrUpdate(players);
                    redisTemplate.opsForHash().put(RedisLock.PlayersIDMap, players.getId().toString(), JSON.toJSONString(players));
                }
            } else if (withdrawApplyParam.getStatus() == 3) {
                Players players = playerService.getById(withdrawApply.getPlayer());
                AccountChanges accountChanges = null;
                BigDecimal balance = BigDecimal.ZERO;
                Integer amountType = null;
                switch (withdrawApply.getWdType()) {
                    case 0:
                        balance = players.getProjectBalance().add(withdrawApply.getAmount());
                        amountType = 3;
                        accountChanges = convert(players, withdrawApply, withdrawApplyParam, players.getProjectBalance(), balance, amountType, 15);
                        players.setProjectBalance(balance);
                        break;
                    case 1:
                        balance = players.getPromoteBalance().add(withdrawApply.getAmount());
                        amountType = 2;
                        accountChanges = convert(players, withdrawApply, withdrawApplyParam, players.getPromoteBalance(), balance, amountType, 15);
                        players.setPromoteBalance(balance);
                        break;
                    case 2:
                        balance = players.getWithdrawBalance().add(withdrawApply.getAmount());
                        amountType = 1;
                        accountChanges = convert(players, withdrawApply, withdrawApplyParam, players.getWithdrawBalance(), balance, amountType, 15);
                        players.setWithdrawBalance(balance);
                        break;
                    case 3:
                        balance = players.getShareMoneyProfit().add(withdrawApply.getAmount());
                        amountType = 1;
                        accountChanges = convert(players, withdrawApply, withdrawApplyParam, players.getShareMoneyProfit(), balance, amountType, 1);
                        players.setShareMoneyProfit(balance);
                        break;
                }
                Assert.isTrue(balance.compareTo(BigDecimal.ZERO) >= 0, "变动后金额不能成负数");
                accountChangeService.saveOrUpdate(accountChanges);
                playerService.saveOrUpdate(players);
                redisTemplate.opsForHash().put(RedisLock.PlayersIDMap, players.getId().toString(), JSON.toJSONString(players));

            }
            withdrawApply.setStatus(withdrawApplyParam.getStatus());
        }
        withdrawApply.setRemark(withdrawApplyParam.getRemark());

        saveOrUpdate(withdrawApply);
    }

    private AccountChanges convert(Players players, WithdrawApply withdrawApply,
                                   WithdrawApplyParam withdrawApplyParam, BigDecimal bcBalance, BigDecimal balance,
                                   Integer amountType, Integer chargeType) {

        AccountChanges accountChanges = new AccountChanges()
                .setPlayer(players.getId())
                .setPlayerName(players.getAccount())
                .setAmountType(amountType)
                .setBcBalance(bcBalance)
                .setAmount(withdrawApply.getAmount())
                .setAcBalance(balance)
                .setChangeType(chargeType)
                .setChangeDescribe(withdrawApplyParam.getRemark())
                .setConnId(withdrawApply.getId().toString());

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
