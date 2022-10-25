package com.sychina.admin.web.router.system;

import com.sychina.admin.aop.Access;
import com.sychina.admin.service.impl.ActionLogServiceImpl;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.AccessLogQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/log")
@Api(tags = {"日志查询"})
public class LogController {

    private ActionLogServiceImpl actionLogService;

    @PostMapping("/actionLog")
    @ApiOperation("操作日志查询")
    @Access
    public ResultModel loadTable(@Validated AccessLogQuery query) {
        return actionLogService.loadTable(query);
    }

    @Autowired
    public void setActionLogService(ActionLogServiceImpl actionLogService) {
        this.actionLogService = actionLogService;
    }
}
