package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.infra.domain.Projects;
import com.sychina.admin.infra.mapper.ProjectMapper;
import com.sychina.admin.service.IProjectService;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.ProjectParam;
import com.sychina.admin.web.pojo.params.ProjectQuery;

/**
 * @author Administrator
 */
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Projects> implements IProjectService {

    public ResultModel add(ProjectParam projectParam) {
    }

    public ResultModel loadTable(ProjectQuery projectQuery) {
    }

    public ResultModel edit(ProjectParam projectParam) {
    }

    public ResultModel delete(Integer id) {
    }
}
