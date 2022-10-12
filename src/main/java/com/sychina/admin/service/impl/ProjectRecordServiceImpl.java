package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.infra.domain.ProjectRecords;
import com.sychina.admin.infra.domain.Projects;
import com.sychina.admin.infra.mapper.ProjectMapper;
import com.sychina.admin.infra.mapper.ProjectRecordMapper;
import com.sychina.admin.service.IProjectRecordService;
import com.sychina.admin.service.IProjectService;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.ProjectParam;
import com.sychina.admin.web.pojo.params.ProjectQuery;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
public class ProjectRecordServiceImpl extends ServiceImpl<ProjectRecordMapper, ProjectRecords> implements IProjectRecordService {

    public ResultModel add(ProjectParam projectParam) {
        return ResultModel.succeed();
    }

    public ResultModel loadTable(ProjectQuery projectQuery) {
        return ResultModel.succeed();
    }

    public ResultModel edit(ProjectParam projectParam) {
        return ResultModel.succeed();
    }

    public ResultModel delete(Integer id) {
        return ResultModel.succeed();
    }
}
