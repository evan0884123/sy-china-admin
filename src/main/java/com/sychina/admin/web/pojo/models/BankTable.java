package com.sychina.admin.web.pojo.models;

import com.sychina.admin.infra.domain.BankInfos;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Administrator
 */
@Data
@Accessors(chain = true)
public class BankTable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "玩家账号")
    private String playerName;

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

    @ApiModelProperty(value = "创建时间")
    private Long create;

    @ApiModelProperty(value = "修改时间")
    private Long update;

    public BankTable(BankInfos record) {

        this.setId(record.getId())
                .setPlayerName(record.getPlayerName())
                .setBank(record.getBank())
                .setBankAddr(record.getBankAddr())
                .setCardType(record.getCardType())
                .setCardNumber(record.getCardNumber())
                .setCardMaster(record.getCardMaster())
                .setCardPhoneNumber(record.getCardPhoneNumber())
                .setCardIdNumber(record.getCardIdNumber())
                .setCreate(record.getCreate())
                .setUpdate(record.getUpdate());
    }
}
