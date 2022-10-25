package com.sychina.admin.web.router.system;

import com.sychina.admin.aop.Access;
import com.sychina.admin.service.impl.ConfigServiceImpl;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.ConfigParam;
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
@RequestMapping("/config")
@Api(tags = {"系统配置"})
public class ConfigController {

    private ConfigServiceImpl configService;

    @PostMapping("/loadTable")
    @ApiOperation("获取配置信息")
    @Access
    public ResultModel loadTable() {
        return configService.loadTable();
    }

    @PostMapping("/edit")
    @ApiOperation("编辑配置信息")
    @Access(recordLog = true)
    public ResultModel edit(@Validated ConfigParam configParam) {
        return configService.edit(configParam);
    }

    @Autowired
    public void setConfigService(ConfigServiceImpl configService) {
        this.configService = configService;
    }
}
