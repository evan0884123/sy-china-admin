package com.sychina.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.common.RedisKeys;
import com.sychina.admin.infra.domain.Debts;
import com.sychina.admin.infra.mapper.DebtMapper;
import com.sychina.admin.service.IDebtService;
import com.sychina.admin.utils.LocalDateTimeHelper;
import com.sychina.admin.web.pojo.SelectOption;
import com.sychina.admin.web.pojo.models.DebtTable;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.DebtParam;
import com.sychina.admin.web.pojo.params.DebtQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class DebtServiceImpl extends ServiceImpl<DebtMapper, Debts> implements IDebtService {

    private ProjectServiceImpl projectService;

    private RedisTemplate redisTemplate;

    public ResultModel add(DebtParam debtParam) {

        Assert.isTrue(StringUtils.isNotBlank(debtParam.getNumbering()), "编号不能为空");
        Debts selectOne = baseMapper.selectOne(new QueryWrapper<Debts>().eq("numbering", debtParam.getNumbering()));
        Assert.isNull(selectOne, "编号重复");

        Debts debts = new Debts();
        debtParam.convert(debts)
                .setName(debtParam.getName())
                .setNumbering(debtParam.getNumbering())
                .setMount(JSON.toJSONString(new ArrayList<Long>()))
                .setCreate(LocalDateTimeHelper.toLong(LocalDateTime.now()));

        int insert = baseMapper.insert(debts);
        redisTemplate.opsForHash().put(RedisKeys.debts, debts.getId().toString(), JSON.toJSONString(debts));

        return ResultModel.succeed();
    }

    public ResultModel loadTable(DebtQuery debtQuery) {

        QueryWrapper<Debts> wrapper = new QueryWrapper<>();
        wrapper.likeRight(StringUtils.isNotBlank(debtQuery.getName()), "`name`", debtQuery.getName());
        wrapper.likeRight(StringUtils.isNotBlank(debtQuery.getNumbering()), "`numbering`", debtQuery.getNumbering());
        wrapper.eq(debtQuery.getStatus() != null, "`status`", debtQuery.getStatus());
        wrapper.between(debtQuery.getTimeType() == 0 && debtQuery.getStartTime() != null && debtQuery.getEndTime() != null,
                "`create`", debtQuery.getStartTime(), debtQuery.getEndTime());
        wrapper.between(debtQuery.getTimeType() == 1 && debtQuery.getStartTime() != null && debtQuery.getEndTime() != null,
                "`update`", debtQuery.getStartTime(), debtQuery.getEndTime());
        wrapper.orderByDesc("`create`");

        IPage page = baseMapper.selectPage(debtQuery.page(), wrapper);

        List<DebtTable> tables = new ArrayList<>();
        List<Debts> records = page.getRecords();
        records.forEach(record -> {
            tables.add(new DebtTable(record));
        });
        page.setRecords(tables);

        return ResultModel.succeed(page);
    }

    public ResultModel edit(DebtParam debtParam) {

        Debts debts = getById(debtParam.getId());
        Assert.notNull(debts, "没有这个国债信息");

        debtParam.convert(debts)
                .setId(debtParam.getId())
                .setUpdate(LocalDateTimeHelper.toLong(LocalDateTime.now()));

        baseMapper.updateById(debts);
        redisTemplate.opsForHash().put(RedisKeys.debts, debts.getId().toString(), JSON.toJSONString(debts));

        return ResultModel.succeed();
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultModel delete(Long id) {

        Debts debts = baseMapper.selectById(id);
        Assert.notNull(debts, "未找到该国债信息");

        List<Long> longList = JSON.parseArray(debts.getMount(), Long.class);
        longList.forEach(pid -> {
            projectService.delete(pid);
        });

        baseMapper.deleteById(id);
        redisTemplate.opsForHash().delete(RedisKeys.debts, id.toString());

        return ResultModel.succeed();
    }

    public ResultModel<List<SelectOption>> fetchDebtOptions(String name) {

        QueryWrapper<Debts> wrapper = new QueryWrapper<Debts>()
                .likeRight("name", name)
                .likeRight("numbering", name);

        List<Debts> debtsList = baseMapper.selectList(wrapper);
        List<SelectOption> debtSelect = new ArrayList<>();

        debtsList.forEach(debts -> {
            SelectOption selectOption = new SelectOption();
            selectOption.setLabel(debts.getName());
            selectOption.setValue(debts.getNumbering());
            debtSelect.add(selectOption);
        });

        return ResultModel.succeed(debtSelect);
    }

    @Autowired
    public void setProjectService(ProjectServiceImpl projectService) {
        this.projectService = projectService;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
