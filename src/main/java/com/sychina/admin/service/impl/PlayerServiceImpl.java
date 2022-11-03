package com.sychina.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.cache.CompanyCache;
import com.sychina.admin.common.RedisKeys;
import com.sychina.admin.infra.domain.AccountChanges;
import com.sychina.admin.infra.domain.Equities;
import com.sychina.admin.infra.domain.Players;
import com.sychina.admin.infra.mapper.PlayerMapper;
import com.sychina.admin.service.IPlayerService;
import com.sychina.admin.utils.LocalDateTimeHelper;
import com.sychina.admin.utils.RedisLockUtil;
import com.sychina.admin.utils.StringGenerator;
import com.sychina.admin.web.pojo.SelectOption;
import com.sychina.admin.web.pojo.models.PlayerTable;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class PlayerServiceImpl extends ServiceImpl<PlayerMapper, Players> implements IPlayerService {

    private AccountChangeServiceImpl accountChangeService;

    private EquitiesServiceImpl equitiesService;

    private RedisTemplate redisTemplate;

    private CompanyCache companyCache;

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
        wrapper.orderByAsc("is_verify_manager").orderByDesc("status").orderByDesc("`create`");

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

        Players players = getById(playerParam.getId());
        Assert.notNull(players, "未找到相关用户信息");

        playerParam.convert(players)
                .setId(playerParam.getId())
                .setUpdate(LocalDateTimeHelper.toLong(LocalDateTime.now()));

        baseMapper.updateById(players);
        redisTemplate.opsForHash()
                .put(RedisKeys.PlayersIDMap, players.getId().toString(), JSON.toJSONString(players));

        return ResultModel.succeed();
    }

    public ResultModel delete(Long id) {

        Players players = baseMapper.selectById(id);
        Assert.notNull(players, "未查到此玩家");
        players.setStatus(0);

        updateById(players);
        redisTemplate.opsForHash().delete(RedisKeys.PlayersIDMap, id.toString());

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

    @Transactional(rollbackFor = Exception.class)
    public ResultModel topOrLowerScore(TopOrLowerScoreParam scoreParam) {

        List<Players> playersList = baseMapper.selectBatchIds(scoreParam.getIds());
        playersList.forEach(players -> {

            String lockKey = RedisKeys.playBalanceChange + players.getId();
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
                        .put(RedisKeys.PlayersIDMap, players.getId().toString(), JSON.toJSONString(players));
            } catch (Exception e) {
                log.error("[WITHDRAW_APPLY][ERROR] action amount error", e);
            } finally {
                lockUtil.unlock(lockKey);
            }
        });

        return ResultModel.succeed();
    }

    public ResultModel resetPassword(ResetPasswordParam param) {

        Players players = baseMapper.selectById(param.getId());
        Assert.notNull(players, "未查到此玩家");

        String password = param.getNewPassword() == null ? StringGenerator.genRandom(8) :
                param.getNewPassword();
        players.setPassword(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)));

        updateById(players);
        redisTemplate.opsForHash().put(RedisKeys.PlayersIDMap, players.getId().toString(), JSON.toJSONString(players));

        return ResultModel.succeed(password);
    }

    public ResultModel batchAudit(PlayerBatchAuditParam param) {

        UpdateWrapper<Players> wrapper = new UpdateWrapper<Players>()
                .set(param.getStatus() != null, "status", param.getStatus())
                .set(param.getIsVerifyManager() != null, "is_verify_manager", param.getIsVerifyManager())
                .in("id", param.getIds());
        update(wrapper);

        return ResultModel.succeed();
    }

    public void updateUseBalance(Players players, TopOrLowerScoreParam scoreParam) {

        BigDecimal totalRecharge, acBalance;
        List<Players> playersList = new ArrayList<>();
        List<AccountChanges> changesList = new ArrayList<>();
        Equities equities = null;
        if (scoreParam.getOperationType() == 0) {
            acBalance = players.getUseBalance().add(scoreParam.getScore());
            totalRecharge = players.getTotalRecharge().add(scoreParam.getScore());
            equities = convert(players, scoreParam.getScore().divide(new BigDecimal("20")));

            if (StringUtils.isNotBlank(players.getLevelInfo())) {
                String[] split = players.getLevelInfo().split("/");
                // 一级返佣 20%
                Players superior = baseMapper.selectById(Long.valueOf(split[split.length - 1]));
                if (superior != null) {

                    BigDecimal superiorRebate = scoreParam.getScore().multiply(new BigDecimal("0.2"));
                    BigDecimal superiorBalance = superior.getPromoteBalance().add(superiorRebate);
                    changesList.add(convert(superior, superior.getPromoteBalance(), superiorRebate, superiorBalance, 2, 5, "推广返佣"));
                    superior.setPromoteBalance(superiorBalance);
                    playersList.add(superior);

                    if (split.length >= 2) {
                        // 二级返佣 10%
                        Players superiorTwo = baseMapper.selectById(Long.valueOf(split[split.length - 2]));
                        if (superiorTwo != null) {

                            BigDecimal superiorTwoRebate = scoreParam.getScore().multiply(new BigDecimal("0.1"));
                            BigDecimal superiorTwoBalance = superiorTwo.getPromoteBalance().add(superiorTwoRebate);
                            changesList.add(convert(superiorTwo, superiorTwo.getPromoteBalance(), superiorTwoRebate, superiorTwoBalance, 2, 5, "推广返佣"));
                            superiorTwo.setPromoteBalance(superiorTwoBalance);
                            playersList.add(superiorTwo);
                        }
                    }
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
        equitiesService.saveOrUpdate(equities);

        playersList.forEach(players1 -> {
            redisTemplate.opsForHash().put(RedisKeys.PlayersIDMap, players1.getId().toString(), JSON.toJSONString(players1));
        });
    }

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
        redisTemplate.opsForHash().put(RedisKeys.PlayersIDMap, players.getId().toString(), JSON.toJSONString(players));
    }

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
        redisTemplate.opsForHash().put(RedisKeys.PlayersIDMap, players.getId().toString(), JSON.toJSONString(players));
    }

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
        redisTemplate.opsForHash().put(RedisKeys.PlayersIDMap, players.getId().toString(), JSON.toJSONString(players));
    }

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
        redisTemplate.opsForHash().put(RedisKeys.PlayersIDMap, players.getId().toString(), JSON.toJSONString(players));
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

    private Equities convert(Players players, BigDecimal amount) {

        String company = companyCache.getCompanyInfo();
        Assert.isTrue(StringUtils.isNotEmpty(company), "没有公司信息无法配置股权");
        Equities equities = new Equities()
                .setPlayer(players.getId())
                .setPlayerName(players.getAccount())
                .setAmount(amount)
                .setCompany(company)
                .setCreate(LocalDateTimeHelper.toLong(LocalDateTime.now()));

        return equities;
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

    @Autowired
    public void setEquitiesService(EquitiesServiceImpl equitiesService) {
        this.equitiesService = equitiesService;
    }

    @Autowired
    public void setCompanyCache(CompanyCache companyCache) {
        this.companyCache = companyCache;
    }
}
