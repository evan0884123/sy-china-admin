package com.sychina.admin.service.impl;

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
import com.sychina.admin.web.pojo.params.TopScoreParam;
import org.springframework.beans.factory.annotation.Autowired;
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

    private RedisLockUtil lockUtil;

    public ResultModel loadTable(PlayerQuery playerQuery) {

        QueryWrapper<Players> wrapper = new QueryWrapper<>();
        wrapper.between(playerQuery.getTimeType() == 0, "`create`", playerQuery.getStartTime(), playerQuery.getEndTime());
        wrapper.between(playerQuery.getTimeType() == 1, "`update`", playerQuery.getStartTime(), playerQuery.getEndTime());

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

        return ResultModel.succeed();
    }

    public ResultModel delete(Long id) {

        baseMapper.deleteById(id);

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

    public ResultModel topScore(TopScoreParam topScoreParam) {

        List<Players> playersList = baseMapper.selectBatchIds(topScoreParam.getIds());
        playersList.forEach(players -> {

            String lockKey = RedisLock.playBalanceChange + players.getId();
            lockUtil.tryLock(lockKey, 15);
            try {
                actionAmount(players, topScoreParam);
            } catch (Exception e) {
                log.error("[WITHDRAW_APPLY][ERROR] action amount error", e);
            } finally {
                lockUtil.unlock(lockKey);
            }
        });

        return ResultModel.succeed();
    }

    @Transactional(rollbackFor = Exception.class)
    public void actionAmount(Players players, TopScoreParam topScoreParam) {

        BigDecimal useBalance = players.getUseBalance().add(topScoreParam.getScore());
        AccountChanges accountChanges = convert(players, topScoreParam, useBalance);
        players.setUseBalance(useBalance);

        saveOrUpdate(players);
        accountChangeService.saveOrUpdate(accountChanges);

    }

    private AccountChanges convert(Players players, TopScoreParam topScoreParam, BigDecimal useBalance) {

        AccountChanges accountChanges = new AccountChanges()
                .setPlayer(players.getId())
                .setPlayerName(players.getAccount())
                .setAmountType(0)
                .setBcBalance(players.getUseBalance())
                .setAmount(topScoreParam.getScore())
                .setAcBalance(useBalance)
                .setChangeType(0)
                .setChangeDescribe(topScoreParam.getRemark())
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
}
