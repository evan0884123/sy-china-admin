package com.sychina.admin.web.pojo.models;

import com.sychina.admin.infra.domain.WithdrawApply;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * Description:
 *
 * @author Administrator
 */
@Data
@Accessors(chain = true)
public class WithdrawApplyTable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "玩家ID")
    private Long player;

    @ApiModelProperty(value = "玩家名称")
    private String playerName;

    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "类型(0-充值 1-提现)")
    private Integer type;

    @ApiModelProperty(value = "充值支付方式(0-人工客服 1-微信 2-支付宝 3-云闪付 4-注册赠送 5-认购返可用 6-认购返可提现)")
    private Integer chargeChannel;

    @ApiModelProperty(value = "人工审核图片")
    private String chargeImg;

    @ApiModelProperty(value = "提现类型(0-收益提现 1-推广金提现 2-返现金额提现 3-共享金提现)")
    private Integer wdType;

    @ApiModelProperty(value = "提现银行卡真实姓名")
    private String wdCardMaster;

    @ApiModelProperty(value = "提现银行卡号")
    private String wdCardNumber;

    @ApiModelProperty(value = "提现银行")
    private String wdBank;

    @ApiModelProperty(value = "提现关联记录ID")
    private Long wdConn;

    @ApiModelProperty(value = "状态(0-申请 1-操作中 2-通过 3-拒绝)")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private Long create;

    @ApiModelProperty(value = "修改时间")
    private Long update;

    public WithdrawApplyTable(WithdrawApply record) {

        this.setId(record.getId())
                .setPlayer(record.getPlayer())
                .setPlayerName(record.getPlayerName())
                .setAmount(record.getAmount())
                .setType(record.getType())
                .setChargeChannel(record.getChargeChannel())
                .setChargeImg(record.getChargeImg())
                .setWdType(record.getWdType())
                .setWdCardMaster(record.getWdCardMaster())
                .setWdCardNumber(record.getWdCardNumber())
                .setWdBank(record.getWdBank())
                .setWdConn(record.getWdConn())
                .setStatus(record.getStatus())
                .setRemark(record.getRemark())
                .setCreate(record.getCreate())
                .setUpdate(record.getUpdate());
    }

}
