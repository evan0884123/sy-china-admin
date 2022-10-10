package com.sychina.admin.infra.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
     * '创建时间'
     */
    private Long create;

    /**
     * '修改时间'
     */
    private Long update;
}
