package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.infra.domain.ProjectRecords;
import com.sychina.admin.infra.mapper.ProjectRecordMapper;
import com.sychina.admin.service.IProjectRecordService;
import com.sychina.admin.web.pojo.models.ProjectRecordTable;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.ProjectRecordQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class ProjectRecordServiceImpl extends ServiceImpl<ProjectRecordMapper, ProjectRecords> implements IProjectRecordService {

    public ResultModel loadTable(ProjectRecordQuery recordQuery) {

        QueryWrapper<ProjectRecords> wrapper = new QueryWrapper<>();
        wrapper.likeRight(StringUtils.isNotBlank(recordQuery.getPlayerName()), "player_name", recordQuery.getPlayerName());
        wrapper.likeRight(StringUtils.isNotBlank(recordQuery.getProjectName()), "project_name", recordQuery.getProjectName());
        wrapper.eq(recordQuery.getStatus() != null, "status", recordQuery.getStatus());
        wrapper.between(recordQuery.getTimeType() == 0, "create", recordQuery.getStartTime(), recordQuery.getEndTime());
        wrapper.between(recordQuery.getTimeType() == 1, "update", recordQuery.getStartTime(), recordQuery.getEndTime());

        IPage page = baseMapper.selectMapsPage(recordQuery.page(), wrapper);

        List<ProjectRecordTable> tables = new ArrayList<>();
        List<ProjectRecords> records = page.getRecords();
        records.forEach(record -> {
            tables.add(new ProjectRecordTable(record));
        });
        page.setRecords(tables);

        return ResultModel.succeed();
    }

}
