package com.sychina.admin.web.project;

import com.sychina.admin.service.impl.DebtServiceImpl;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.DebtParam;
import com.sychina.admin.web.pojo.params.DebtQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/debt")
@Api(tags = {"国债管理"})
public class DebtController {

    private DebtServiceImpl debtService;

    @PostMapping("/add")
    @ApiOperation("新增国债")
    public ResultModel add(@Validated DebtParam debtParam) {
        return debtService.add(debtParam);
    }

    @PostMapping("/loadTable")
    @ApiOperation("获取所有国债")
    public ResultModel loadTable(@Validated DebtQuery debtQuery) {
        return debtService.loadTable(debtQuery);
    }

    @PostMapping("/edit")
    @ApiOperation("编辑国债")
    public ResultModel edit(@Validated DebtParam debtParam) {
        return debtService.edit(debtParam);
    }

    @PostMapping("/delete")
    @ApiOperation("删除国债")
    public ResultModel delete(@RequestParam Long id) {
        return debtService.delete(id);
    }

    @Autowired
    public void setDebtService(DebtServiceImpl debtService) {
        this.debtService = debtService;
    }
}
