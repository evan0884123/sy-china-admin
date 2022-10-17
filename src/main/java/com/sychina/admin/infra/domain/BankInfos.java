package com.sychina.admin.infra.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 银行卡信息
 *
 * @author Administrator
 */
@Data
@Accessors(chain = true)
@TableName("bank_infos")
public class BankInfos {

    /**
     *
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * '所属玩家ID'
     */
    private Long player;

    /**
     * '玩家账号'
     */
    private String playerName;

    /**
     * '开户银行'
     */
    private String bank;

    /**
     * '开户支行地址'
     */
    private String bankAddr;

    /**
     * '卡类型(0-储蓄卡 1-信用卡)'
     */
    private Integer cardType;

    /**
     * '银行卡号'
     */
    private String cardNumber;

    /**
     * '持卡人姓名'
     */
    private String cardMaster;

    /**
     * '银行卡绑定手机号码'
     */
    private String cardPhoneNumber;

    /**
     * '银行卡绑定身份证号码'
     */
    private String cardIdNumber;

    /**
     * '核实(0-未 1-已)'
     */
    private Integer verified;

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
