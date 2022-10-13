package com.sychina.admin.web.funds;

import com.sychina.admin.service.impl.BankInfoServiceImpl;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.BankParam;
import com.sychina.admin.web.pojo.params.BankQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/bank")
@Api(tags = {"银行卡管理"})
public class BankController {

    private BankInfoServiceImpl bankInfoService;

    @PostMapping("/add")
    @ApiOperation("新增玩家的银行卡")
    public ResultModel add(@Validated BankParam bankParam) {
        return bankInfoService.add(bankParam);
    }

    @PostMapping("/loadTable")
    @ApiOperation("获取所有玩家的银行卡")
    public ResultModel loadTable(@Validated BankQuery bankQuery) {
        return bankInfoService.loadTable(bankQuery);
    }

    @PostMapping("/edit")
    @ApiOperation("编辑玩家的银行卡")
    public ResultModel edit(@Validated BankParam bankParam) {
        return bankInfoService.edit(bankParam);
    }

    @PostMapping("/delete")
    @ApiOperation("删除玩家的银行卡")
    public ResultModel delete(@RequestParam Long id) {
        return bankInfoService.delete(id);
    }

    @Autowired
    public void setBankInfoService(BankInfoServiceImpl bankInfoService) {
        this.bankInfoService = bankInfoService;
    }
}
