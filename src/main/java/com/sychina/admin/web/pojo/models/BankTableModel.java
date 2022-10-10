package com.sychina.admin.web.pojo.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class BankTableModel {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "玩家账号")
    private String account;

    @ApiModelProperty(value = "开户银行")
    private String bank;

    @ApiModelProperty(value = "开户支行地址")
    private String bankAddr;

    @ApiModelProperty(value = "卡类型(0-储蓄卡 1-信用卡)")
    private Integer cardType;

    @ApiModelProperty(value = "银行卡号")
    private String cardNumber;

    @ApiModelProperty(value = "持卡人姓名")
    private String cardMaster;

    @ApiModelProperty(value = "银行卡绑定手机号码")
    private String cardPhoneNumber;

    @ApiModelProperty(value = "银行卡绑定身份证号码")
    private String cardIdNumber;
}
