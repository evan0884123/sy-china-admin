package com.sychina.admin.web.funds;

import com.sychina.admin.service.impl.BankInfoServiceImpl;
import com.sychina.admin.service.impl.WithdrawApplyServiceImpl;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.BankParam;
import com.sychina.admin.web.pojo.params.BankQuery;
import com.sychina.admin.web.pojo.params.WithdrawApplyParam;
import com.sychina.admin.web.pojo.params.WithdrawApplyQuery;
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
@RequestMapping("/withdraw")
@Api(tags = {"提现申请"})
public class WithdrawApplyController {

    private WithdrawApplyServiceImpl withdrawApplyService;

    @PostMapping("/loadTable")
    @ApiOperation("获取所有玩家的提现申请")
    public ResultModel loadTable(@Validated WithdrawApplyQuery withdrawApplyQuery) {
        return withdrawApplyService.loadTable(withdrawApplyQuery);
    }

    @PostMapping("/edit")
    @ApiOperation("编辑申请状态")
    public ResultModel edit(@Validated WithdrawApplyParam withdrawApplyParam) {
        return withdrawApplyService.edit(withdrawApplyParam);
    }

    @Autowired
    public void setWithdrawApplyService(WithdrawApplyServiceImpl withdrawApplyService) {
        this.withdrawApplyService = withdrawApplyService;
    }
}
