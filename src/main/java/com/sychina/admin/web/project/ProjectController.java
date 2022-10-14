package com.sychina.admin.web.project;

import com.sychina.admin.service.impl.ProjectServiceImpl;
import com.sychina.admin.web.pojo.SelectOption;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.ProjectParam;
import com.sychina.admin.web.pojo.params.ProjectQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/project")
@Api(tags = {"项目管理"})
public class ProjectController {

    private ProjectServiceImpl projectService;

    @PostMapping("/add")
    @ApiOperation("新增项目")
    public ResultModel add(@Validated ProjectParam projectParam) {
        return projectService.add(projectParam);
    }

    @PostMapping("/loadTable")
    @ApiOperation("获取所有项目信息")
    public ResultModel loadTable(@Validated ProjectQuery projectQuery) {
        return projectService.loadTable(projectQuery);
    }

    @PostMapping("/edit")
    @ApiOperation("编辑项目信息")
    public ResultModel edit(@Validated ProjectParam projectParam) {
        return projectService.edit(projectParam);
    }

    @PostMapping("/delete")
    @ApiOperation("删除项目信息")
    public ResultModel delete(@RequestParam Long id) {
        return projectService.delete(id);
    }

//    @PostMapping("/loadProjectList")
    @ApiOperation("获取菜单信息")
    public ResultModel<List<SelectOption>> loadProjectList() {
        return projectService.loadProjectList();
    }

    @Autowired
    public void setProjectService(ProjectServiceImpl projectService) {
        this.projectService = projectService;
    }
}
