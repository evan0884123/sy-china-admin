package com.sychina.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.common.RedisKeys;
import com.sychina.admin.infra.domain.AccountChanges;
import com.sychina.admin.infra.domain.Equities;
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
import java.util.Arrays;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class WithdrawApplyServiceImpl extends ServiceImpl<WithdrawApplyMapper, WithdrawApply> implements IWithdrawApplyService {

    private PlayerServiceImpl playerService;

    private AccountChangeServiceImpl accountChangeService;

    private EquitiesServiceImpl equitiesService;

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

    @Transactional(rollbackFor = Exception.class)
    public ResultModel edit(WithdrawApplyParam withdrawApplyParam) {

        List<WithdrawApply> withdrawApplies = baseMapper.selectBatchIds(withdrawApplyParam.getIds());
        withdrawApplies.forEach(withdrawApply -> {

            if (Arrays.asList(1, 2).contains(withdrawApply.getStatus())) {
                return;
            }
            String lockKey = RedisKeys.playBalanceChange + withdrawApply.getPlayer();
            lockUtil.tryLock(lockKey, 15);
            try {
                actionAmount(withdrawApply, withdrawApplyParam);
            } finally {
                lockUtil.unlock(lockKey);
            }
        });

        return ResultModel.succeed();
    }

    public void actionAmount(WithdrawApply withdrawApply, WithdrawApplyParam withdrawApplyParam) {

        if (!withdrawApply.getStatus().equals(withdrawApplyParam.getStatus())) {
            if (withdrawApplyParam.getStatus() == 2) {

                Players players = playerService.getById(withdrawApply.getPlayer());
                if (withdrawApply.getType() == 0) {

                    rechargeApproved(players, withdrawApply);
                } else if (withdrawApply.getType() == 1) {

                    withdrawApproved(players, withdrawApply);
                }
            } else if (withdrawApplyParam.getStatus() == 3) {

                withdrawReject(withdrawApply);
            }
            withdrawApply.setStatus(withdrawApplyParam.getStatus());
        }
        withdrawApply.setRemark(withdrawApplyParam.getRemark());

        saveOrUpdate(withdrawApply);
    }

    public void rechargeApproved(Players players, WithdrawApply withdrawApply) {
        BigDecimal balance = players.getUseBalance().add(withdrawApply.getAmount());
        BigDecimal totalRecharge = players.getTotalRecharge().add(withdrawApply.getAmount());

        List<Players> playersList = new ArrayList<>();
        List<AccountChanges> changesList = new ArrayList<>();
        if (StringUtils.isNotBlank(players.getLevelInfo())) {
            String[] split = players.getLevelInfo().split("/");
            // 一级返佣 20%
            Players superior = playerService.getById(Long.valueOf(split[split.length - 1]));
            if (superior != null) {
                BigDecimal superiorRebate = withdrawApply.getAmount().multiply(new BigDecimal("0.2"));
                BigDecimal superiorBalance = superior.getPromoteBalance().add(superiorRebate);
                changesList.add(convert(superior, superior.getPromoteBalance(), superiorRebate, superiorBalance, 2, 5, "推广返佣"));
                superior.setPromoteBalance(superiorBalance);
                playersList.add(superior);
                if (split.length >= 2) {
                    // 二级返佣 10%
                    Players superiorTwo = playerService.getById(Long.valueOf(split[split.length - 2]));
                    if (superiorTwo != null) {
                        BigDecimal superiorTwoRebate = withdrawApply.getAmount().multiply(new BigDecimal("0.1"));
                        BigDecimal superiorTwoBalance = superiorTwo.getPromoteBalance().add(superiorTwoRebate);
                        changesList.add(convert(superiorTwo, superiorTwo.getPromoteBalance(), superiorTwoRebate, superiorTwoBalance, 2, 5, "推广返佣"));
                        superiorTwo.setPromoteBalance(superiorTwoBalance);
                        playersList.add(superiorTwo);
                    }
                }
            }


        }

        changesList.add(convert(players, withdrawApply, players.getUseBalance(), balance, 0, 0, "充值"));
        players.setUseBalance(balance);
        players.setTotalRecharge(totalRecharge);
        playersList.add(players);

        accountChangeService.saveOrUpdateBatch(changesList);
        equitiesService.saveOrUpdate(convert(players, withdrawApply.getAmount().divide(new BigDecimal("20"))));
        playerService.saveOrUpdateBatch(playersList);

        playersList.forEach(players1 -> {
            redisTemplate.opsForHash().put(RedisKeys.PlayersIDMap, players1.getId().toString(), JSON.toJSONString(players1));
        });
    }

    public void withdrawApproved(Players players, WithdrawApply withdrawApply) {
        AccountChanges accountChanges = null;
        BigDecimal balance;
        switch (withdrawApply.getWdType()) {
            case 0:
                balance = players.getProjectBalance().add(withdrawApply.getAmount());
                accountChanges = convert(players, withdrawApply, balance, players.getProjectBalance(), 3, 1, "收益提现");
                break;
            case 1:
                balance = players.getPromoteBalance().add(withdrawApply.getAmount());
                accountChanges = convert(players, withdrawApply, balance, players.getPromoteBalance(), 2, 1, "推广金提现");
                break;
            case 2:
                balance = players.getWithdrawBalance().add(withdrawApply.getAmount());
                accountChanges = convert(players, withdrawApply, balance, players.getWithdrawBalance(), 1, 1, "返现金额提现");
                break;
            case 3:
                balance = players.getShareMoneyProfit().add(withdrawApply.getAmount());
                accountChanges = convert(players, withdrawApply, balance, players.getShareMoneyProfit(), 0, 1, "共享金提现");
                break;
        }
        accountChangeService.saveOrUpdate(accountChanges);
    }

    public void withdrawReject(WithdrawApply withdrawApply) {
        Players players = playerService.getById(withdrawApply.getPlayer());
        AccountChanges accountChanges = null;
        BigDecimal balance;
        Integer amountType = null;
        switch (withdrawApply.getWdType()) {
            case 0:
                balance = players.getProjectBalance().add(withdrawApply.getAmount());
                amountType = 3;
                accountChanges = convert(players, withdrawApply, players.getProjectBalance(), balance, amountType, 15, "收益提现");
                players.setProjectBalance(balance);
                break;
            case 1:
                balance = players.getPromoteBalance().add(withdrawApply.getAmount());
                amountType = 2;
                accountChanges = convert(players, withdrawApply, players.getPromoteBalance(), balance, amountType, 15, "推广金提现");
                players.setPromoteBalance(balance);
                break;
            case 2:
                balance = players.getWithdrawBalance().add(withdrawApply.getAmount());
                amountType = 1;
                accountChanges = convert(players, withdrawApply, players.getWithdrawBalance(), balance, amountType, 15, "返现金额提现");
                players.setWithdrawBalance(balance);
                break;
            case 3:
                balance = players.getShareMoneyProfit().add(withdrawApply.getAmount());
                amountType = 1;
                accountChanges = convert(players, withdrawApply, players.getShareMoneyProfit(), balance, amountType, 1, "共享金提现");
                players.setShareMoneyProfit(balance);
                break;
        }
        accountChangeService.saveOrUpdate(accountChanges);
        playerService.saveOrUpdate(players);
        redisTemplate.opsForHash().put(RedisKeys.PlayersIDMap, players.getId().toString(), JSON.toJSONString(players));
    }

    private AccountChanges convert(Players players, WithdrawApply withdrawApply, BigDecimal bcBalance, BigDecimal balance,
                                   Integer amountType, Integer chargeType, String changeDes) {

        AccountChanges accountChanges = new AccountChanges()
                .setPlayer(players.getId())
                .setPlayerName(players.getAccount())
                .setAmountType(amountType)
                .setBcBalance(bcBalance)
                .setAmount(withdrawApply.getAmount())
                .setAcBalance(balance)
                .setChangeType(chargeType)
                .setChangeDescribe(changeDes)
                .setConnId(withdrawApply.getId().toString())
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

    private Equities convert(Players players, BigDecimal amount) {

        String company = (String) redisTemplate.opsForSet().randomMember(RedisKeys.Companies);
        Assert.isTrue(StringUtils.isNotBlank(company), "没有公司信息无法配置股权");
        Equities equities = new Equities()
                .setPlayer(players.getId())
                .setPlayerName(players.getAccount())
                .setAmount(amount)
                .setCompany(company)
                .setCreate(LocalDateTimeHelper.toLong(LocalDateTime.now()));

        return equities;
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

    @Autowired
    public void setEquitiesService(EquitiesServiceImpl equitiesService) {
        this.equitiesService = equitiesService;
    }
}
