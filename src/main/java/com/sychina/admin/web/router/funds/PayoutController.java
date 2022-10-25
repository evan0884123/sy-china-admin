package com.sychina.admin.web.router.funds;

import com.sychina.admin.aop.Access;
import com.sychina.admin.service.impl.PayoutServiceImpl;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.PayoutParam;
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
@RequestMapping("/payout")
@Api(tags = {"支付管理"})
public class PayoutController {

    private PayoutServiceImpl payoutService;

    @PostMapping("/add")
    @ApiOperation("新增支付")
    @Access(recordLog = true)
    public ResultModel add(@Validated PayoutParam payoutParam) {
        return payoutService.add(payoutParam);
    }

    @PostMapping("/loadTable")
    @ApiOperation("获取支付")
    @Access
    public ResultModel loadTable() {
        return payoutService.loadTable();
    }

    @PostMapping("/edit")
    @ApiOperation("编辑支付")
    @Access(recordLog = true)
    public ResultModel edit(@Validated PayoutParam payoutParam) {
        return payoutService.edit(payoutParam);
    }

    @PostMapping("/delete")
    @ApiOperation("删除支付")
    @Access(recordLog = true)
    public ResultModel delete(@RequestParam Long id) {
        return payoutService.delete(id);
    }

    @Autowired
    public void setPayoutService(PayoutServiceImpl payoutService) {
        this.payoutService = payoutService;
    }
}
