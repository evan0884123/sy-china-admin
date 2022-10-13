package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.infra.domain.Projects;
import com.sychina.admin.infra.mapper.ProjectMapper;
import com.sychina.admin.service.IProjectService;
import com.sychina.admin.utils.LocalDateTimeHelper;
import com.sychina.admin.web.pojo.models.ProjectTable;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.ProjectParam;
import com.sychina.admin.web.pojo.params.ProjectQuery;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Projects> implements IProjectService {

    public ResultModel add(ProjectParam projectParam) {

        Projects projects = projectParam.convert()
                .setCreate(LocalDateTimeHelper.toLong(LocalDateTime.now()));

        baseMapper.insert(projects);

        return ResultModel.succeed();
    }

    public ResultModel loadTable(ProjectQuery projectQuery) {

        QueryWrapper<Projects> wrapper = new QueryWrapper<>();
        wrapper.between(projectQuery.getTimeType() == 0, "create", projectQuery.getStartTime(), projectQuery.getEndTime());
        wrapper.between(projectQuery.getTimeType() == 1, "update", projectQuery.getStartTime(), projectQuery.getEndTime());

        IPage page = baseMapper.selectMapsPage(projectQuery.page(), wrapper);

        List<ProjectTable> tables = new ArrayList<>();
        List<Projects> records = page.getRecords();
        records.forEach(record -> {
            tables.add(new ProjectTable(record));
        });
        page.setRecords(tables);

        return ResultModel.succeed();
    }

    public ResultModel edit(ProjectParam projectParam) {

        Projects projects = projectParam.convert()
                .setId(projectParam.getId())
                .setUpdate(LocalDateTimeHelper.toLong(LocalDateTime.now()));

        baseMapper.updateById(projects);

        return ResultModel.succeed();
    }

    public ResultModel delete(Long id) {

        baseMapper.deleteById(id);

        return ResultModel.succeed();
    }
}
