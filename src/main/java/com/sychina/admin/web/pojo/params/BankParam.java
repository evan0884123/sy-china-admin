package com.sychina.admin.web.pojo.params;

import com.sychina.admin.infra.domain.BankInfos;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Description:
 *
 * @author Administrator
 */
@Data
public class BankParam {

    @ApiModelProperty(value = "id", required = true)
    @NotNull
    private Long id;

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

    public BankInfos convert(){

        BankInfos bankInfo = new BankInfos();
        bankInfo.setId(this.getId())
                .setBank(this.getBank())
                .setBankAddr(this.getBankAddr())
                .setCardType(this.getCardType())
                .setCardNumber(this.getCardNumber())
                .setCardMaster(this.getCardMaster())
                .setCardPhoneNumber(this.getCardPhoneNumber())
                .setCardIdNumber(this.getCardIdNumber());

        return bankInfo;
    }
}
