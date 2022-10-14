package com.sychina.admin.infra.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 *
 * @author Administrator
 */
@Data
@Accessors(chain = true)
@TableName("charge_withdraw_reply")
public class AccountChanges {

    /**
     *
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
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
     * 变动金额类型(0-可用余额 1-可提现余额 2-推广金额 3-项目收益)
     */
    private BigDecimal amountType;

    /**
     * '变动前金额'
     */
    private BigDecimal bcBalance;

    /**
     * '变动金额'
     */
    private BigDecimal amount;

    /**
     * '变动后金额'
     */
    private BigDecimal acBalance;

    /**
     * '变动类型(0-充值 1-提现 2-项目收益 3-鸡蛋收益 4-项目返现 5-推广充值返现)'
     */
    private Integer changeType;

    /**
     * '变动说明'
     */
    private String changeDescribe;

    /**
     * '关联ID'
     */
    private String connId;

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
