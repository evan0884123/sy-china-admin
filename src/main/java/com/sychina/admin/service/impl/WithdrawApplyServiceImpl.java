package com.sychina.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.cache.CompanyCache;
import com.sychina.admin.cache.PlayerCache;
import com.sychina.admin.common.RedisKeys;
import com.sychina.admin.infra.domain.*;
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

    private ConfigServiceImpl configService;

    private PlayerCache playerCache;

    private CompanyCache companyCache;

    private RedisLockUtil lockUtil;

    public ResultModel loadTable(WithdrawApplyQuery recordQuery) {

        QueryWrapper<WithdrawApply> wrapper = new QueryWrapper<>();
        wrapper.likeRight(StringUtils.isNotBlank(recordQuery.getPlayerName()), "player_name", recordQuery.getPlayerName());
        wrapper.eq(recordQuery.getType() != null, "type", recordQuery.getType());
        wrapper.eq(recordQuery.getChargeChannel() != null, "charge_channel", recordQuery.getChargeChannel());
        wrapper.eq(recordQuery.getWdType() != null, "wd_type", recordQuery.getWdType());
        wrapper.eq(recordQuery.getStatus() != null, "status", recordQuery.getStatus());
        wrapper.between(recordQuery.getTimeType() == 0 && recordQuery.getStartTime() != null && recordQuery.getEndTime() != null,
                "`create`", recordQuery.getStartTime(), recordQuery.getEndTime());
        wrapper.between(recordQuery.getTimeType() == 1 && recordQuery.getStartTime() != null && recordQuery.getEndTime() != null,
                "`update`", recordQuery.getStartTime(), recordQuery.getEndTime());
        wrapper.orderByAsc("`status`").orderByDesc("`create`");

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
            boolean tryLock = lockUtil.tryLock(lockKey, 15);
            if (tryLock){
                try {
                    actionAmount(withdrawApply, withdrawApplyParam);
                } finally {
                    lockUtil.unlock(lockKey);
                }
            }
        });

        return ResultModel.succeed("执行中，请再次确认是否成功");
    }

    public ResultModel applicationNotice() {

        QueryWrapper<WithdrawApply> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 0);

        Long count = baseMapper.selectCount(wrapper);

        return ResultModel.succeed(count);
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
            } else if (withdrawApplyParam.getStatus() == 3 && withdrawApply.getType() == 1) {

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
                BigDecimal superiorRebate = withdrawApply.getAmount().multiply(new BigDecimal("0.18"));
                BigDecimal superiorBalance = superior.getPromoteBalance().add(superiorRebate);
                changesList.add(convert(superior, superior.getPromoteBalance(), superiorRebate, superiorBalance, 2, 5, "推广返佣"));
                superior.setPromoteBalance(superiorBalance);
                playersList.add(superior);
                if (split.length >= 2) {
                    // 二级返佣 10%
                    Players superiorTwo = playerService.getById(Long.valueOf(split[split.length - 2]));
                    if (superiorTwo != null) {
                        BigDecimal superiorTwoRebate = withdrawApply.getAmount().multiply(new BigDecimal("0.06"));
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
            playerCache.setPlayerCache(players1);
        });
    }

    public void withdrawApproved(Players players, WithdrawApply withdrawApply) {
        AccountChanges accountChanges = null;
        AccountChanges lastAChanges = accountChangeService.getOne(new QueryWrapper<AccountChanges>()
                .eq("conn_id", withdrawApply.getId().toString())
                .orderByDesc("`create`").last("limit 1"));

        switch (withdrawApply.getWdType()) {
            case 0:
                accountChanges = convert(players, withdrawApply, lastAChanges.getBcBalance(), lastAChanges.getAcBalance(), 3, 1, "收益提现");
                break;
            case 1:
                accountChanges = convert(players, withdrawApply, lastAChanges.getBcBalance(), lastAChanges.getAcBalance(), 2, 1, "推广金提现");
                break;
            case 2:
                accountChanges = convert(players, withdrawApply, lastAChanges.getBcBalance(), lastAChanges.getAcBalance(), 1, 1, "返现金额提现");
                break;
            case 3:
                accountChanges = convert(players, withdrawApply, lastAChanges.getBcBalance(), lastAChanges.getAcBalance(), 0, 1, "共享金提现");
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
                accountChanges = convert(players, withdrawApply, players.getShareMoneyProfit(), balance, amountType, 15, "共享金提现");
                players.setShareMoneyProfit(balance);
                break;
        }
        accountChangeService.saveOrUpdate(accountChanges);
        playerService.saveOrUpdate(players);
        playerCache.setPlayerCache(players);
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

        String company = companyCache.getCompanyInfo();
        Assert.isTrue(StringUtils.isNotEmpty(company), "没有公司信息无法配置股权");
        Config config = configService.getById(1L);
        long millis = System.currentTimeMillis();
        if (millis >= config.getCeStartTime() && millis <= config.getCeStopTime()){
            amount = amount.multiply(new BigDecimal("2"));
        }
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

    @Autowired
    public void setCompanyCache(CompanyCache companyCache) {
        this.companyCache = companyCache;
    }

    @Autowired
    public void setPlayerCache(PlayerCache playerCache) {
        this.playerCache = playerCache;
    }

    @Autowired
    public void setConfigService(ConfigServiceImpl configService) {
        this.configService = configService;
    }
}
