package com.sychina.admin.infra.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author Administrator
 */
@Data
@Accessors(chain = true)
@TableName("charge_withdraw_reply")
public class WithdrawApply {

    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * '玩家ID'
     */
    private Long player;

    /**
     * 玩家名称
     */
    private String playerName;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * '类型(0-充值 1-提现)'
     */
    private Integer type;

    /**
     * '充值支付方式(0-人工客服 1-微信 2-支付宝 3-云闪付 4-注册赠送 5-认购返可用 6-认购返可提现)'
     */
    private Integer chargeChannel;

    /**
     * 人工审核图片
     */
    private String chargeImg;

    /**
     * '提现类型(0-收益提现 1-推广金提现 2-返现金额提现)'
     */
    private Integer wdType;

    /**
     * '提现银行卡真实姓名'
     */
    private String wdCardMaster;

    /**
     * '提现银行卡号'
     */
    private String wdCardNumber;

    /**
     * '提现银行'
     */
    private String wdBank;

    /**
     * 提现关联记录ID
     */
    private Long wdConn;

    /**
     * '状态(0-申请 1-操作中 2-通过 3-拒绝)'
     */
    private Integer status;

    /**
     * '备注'
     */
    private String remark;

    /**
     * '创建时间'
     */
    @TableField("`create`")
    private Long create;

    /**
     * '修改时间'
     */
    @TableField("`update`")
    private Long update;
}
