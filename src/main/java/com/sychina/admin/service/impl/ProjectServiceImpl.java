package com.sychina.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.infra.domain.Debts;
import com.sychina.admin.infra.domain.Projects;
import com.sychina.admin.infra.mapper.ProjectMapper;
import com.sychina.admin.service.IProjectService;
import com.sychina.admin.utils.LocalDateTimeHelper;
import com.sychina.admin.web.pojo.SelectOption;
import com.sychina.admin.web.pojo.models.ProjectTable;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.ProjectParam;
import com.sychina.admin.web.pojo.params.ProjectQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Projects> implements IProjectService {

    private DebtServiceImpl debtService;

    @Transactional(rollbackFor = Exception.class)
    public ResultModel add(ProjectParam projectParam) {

        Debts debts = debtService.getOne(new QueryWrapper<Debts>().eq("numbering", projectParam.getDebtNumbering()));
        Assert.notNull(debts, "未找到该国债信息");

        Projects projects = projectParam.convert()
                .setDebtNumbering(projectParam.getDebtNumbering())
                .setDebtName(debts.getName())
                .setCreate(LocalDateTimeHelper.toLong(LocalDateTime.now()));

        int insert = baseMapper.insert(projects);

        List<Long> mountList = JSON.parseArray(debts.getMount(), Long.class);
        mountList.add(projects.getId());
        debts.setMount(JSON.toJSONString(mountList));
        debtService.updateById(debts);

        return ResultModel.succeed();
    }

    public ResultModel loadTable(ProjectQuery projectQuery) {

        QueryWrapper<Projects> wrapper = new QueryWrapper<>();
        wrapper.likeRight(StringUtils.isNotBlank(projectQuery.getName()), "`name`", projectQuery.getName());
        wrapper.likeRight(StringUtils.isNotBlank(projectQuery.getDebtName()), "`debt_name`", projectQuery.getDebtName());
        wrapper.likeRight(StringUtils.isNotBlank(projectQuery.getDebtNumbering()), "`debt_numbering`", projectQuery.getDebtNumbering());
        wrapper.eq(projectQuery.getStatus() != null, "`status`", projectQuery.getStatus());
        wrapper.between(projectQuery.getTimeType() == 0 && projectQuery.getStartTime() != null && projectQuery.getEndTime() != null,
                "`create`", projectQuery.getStartTime(), projectQuery.getEndTime());
        wrapper.between(projectQuery.getTimeType() == 1 && projectQuery.getStartTime() != null && projectQuery.getEndTime() != null,
                "`update`", projectQuery.getStartTime(), projectQuery.getEndTime());
        wrapper.orderByDesc("`create`");

        IPage page = baseMapper.selectPage(projectQuery.page(), wrapper);

        List<ProjectTable> tables = new ArrayList<>();
        List<Projects> records = page.getRecords();
        records.forEach(record -> {
            tables.add(new ProjectTable(record));
        });
        page.setRecords(tables);

        return ResultModel.succeed(page);
    }

    public ResultModel edit(ProjectParam projectParam) {

        Assert.notNull(projectParam.getId(), "id不能为空");

        Projects projects = projectParam.convert()
                .setId(projectParam.getId())
                .setUpdate(LocalDateTimeHelper.toLong(LocalDateTime.now()));

        baseMapper.updateById(projects);

        return ResultModel.succeed();
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultModel delete(Long id) {

        Projects projects = baseMapper.selectById(id);
        Assert.notNull(projects, "未找到该国债信息");

        Debts debts = debtService.getOne(new QueryWrapper<Debts>().eq("numbering", projects.getDebtNumbering()));
        Assert.notNull(debts, "未找到该国债信息");

        List<Long> mountList = JSON.parseArray(debts.getMount(), Long.class);
        mountList.remove(id);

        debtService.saveOrUpdate(debts.setMount(JSON.toJSONString(mountList)));
        baseMapper.deleteById(id);

        return ResultModel.succeed();
    }

    public ResultModel<List<SelectOption>> loadProjectList() {

        List<Projects> projectsList = baseMapper.selectList(new QueryWrapper<>());
        List<SelectOption> projectSelect = new ArrayList<>();

        projectsList.forEach(projects -> {
            SelectOption selectOption = new SelectOption();
            selectOption.setLabel(projects.getName());
            selectOption.setValue(projects.getId() + "");
            projectSelect.add(selectOption);
        });

        return ResultModel.succeed(projectSelect);
    }

    @Autowired
    public void setDebtService(DebtServiceImpl debtService) {
        this.debtService = debtService;
    }
}
