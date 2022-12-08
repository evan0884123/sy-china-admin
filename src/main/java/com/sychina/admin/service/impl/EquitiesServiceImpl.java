package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.cache.CompanyCache;
import com.sychina.admin.infra.domain.Equities;
import com.sychina.admin.infra.domain.Players;
import com.sychina.admin.infra.mapper.EquitiesMapper;
import com.sychina.admin.service.IEquitiesService;
import com.sychina.admin.utils.LocalDateTimeHelper;
import com.sychina.admin.web.pojo.models.EquitiesTable;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.EquitiesParam;
import com.sychina.admin.web.pojo.params.EquitiesQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class EquitiesServiceImpl extends ServiceImpl<EquitiesMapper, Equities> implements IEquitiesService {

    private PlayerServiceImpl playerService;

    private CompanyCache companyCache;

    public ResultModel loadTable(EquitiesQuery recordQuery) {

        QueryWrapper<Equities> wrapper = new QueryWrapper<>();
        wrapper.likeRight(StringUtils.isNotBlank(recordQuery.getPlayerName()), "player_name", recordQuery.getPlayerName());
        wrapper.likeRight(StringUtils.isNotBlank(recordQuery.getCompany()), "company", recordQuery.getCompany());
        wrapper.between(recordQuery.getTimeType() == 0 && recordQuery.getStartTime() != null && recordQuery.getEndTime() != null,
                "`create`", recordQuery.getStartTime(), recordQuery.getEndTime());
        wrapper.between(recordQuery.getTimeType() == 1 && recordQuery.getStartTime() != null && recordQuery.getEndTime() != null,
                "`update`", recordQuery.getStartTime(), recordQuery.getEndTime());
        wrapper.orderByDesc("`create`");

        IPage page = baseMapper.selectPage(recordQuery.page(), wrapper);

        List<EquitiesTable> tables = new ArrayList<>();
        List<Equities> records = page.getRecords();
        records.forEach(record -> {
            tables.add(new EquitiesTable(record));
        });
        page.setRecords(tables);

        return ResultModel.succeed(page);
    }

    public ResultModel edit(EquitiesParam param) {

        Equities equities = param.convert()
                .setId(param.getId())
                .setUpdate(LocalDateTimeHelper.toLong(LocalDateTime.now()));

        baseMapper.updateById(equities);

        return ResultModel.succeed();
    }

    public ResultModel add(EquitiesParam param) {

        Players players = playerService.getOne(new QueryWrapper<Players>().eq("account", param.getPlayerName()));
        Assert.notNull(players, "未查到此玩家");
        String company = companyCache.getCompanyInfo();
        Assert.isTrue(StringUtils.isNotEmpty(company), "没有公司信息无法配置股权");

        Equities equities = param.convert()
                .setPlayer(players.getId())
                .setCompany(company)
                .setCreate(LocalDateTimeHelper.toLong(LocalDateTime.now()));

        saveOrUpdate(equities);

        return ResultModel.succeed();
    }

    @Autowired
    public void setPlayerService(PlayerServiceImpl playerService) {
        this.playerService = playerService;
    }

    @Autowired
    public void setCompanyCache(CompanyCache companyCache) {
        this.companyCache = companyCache;
    }
}
