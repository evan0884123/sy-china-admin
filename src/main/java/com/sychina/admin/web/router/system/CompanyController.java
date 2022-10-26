package com.sychina.admin.web.router.system;

import com.sychina.admin.aop.Access;
import com.sychina.admin.service.impl.CompanyServiceImpl;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/company")
@Api(tags = {"公司管理"})
public class CompanyController {

    private CompanyServiceImpl companyService;

    @PostMapping("/add")
    @ApiOperation("新增公司")
    @Access(recordLog = true)
    public ResultModel add(@RequestParam String content) {
        return companyService.add(content);
    }

    @PostMapping("/loadTable")
    @ApiOperation("获取公司")
    @Access
    public ResultModel loadTable() {
        return companyService.loadTable();
    }

    @PostMapping("/delete")
    @ApiOperation("删除公司")
    @Access(recordLog = true)
    public ResultModel delete(@RequestParam Long id) {
        return companyService.delete(id);
    }

    @Autowired
    public void setCompanyService(CompanyServiceImpl companyService) {
        this.companyService = companyService;
    }
}
