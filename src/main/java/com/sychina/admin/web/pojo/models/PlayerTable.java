package com.sychina.admin.web.pojo.models;

import com.sychina.admin.infra.domain.Players;
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
public class PlayerTable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "可用余额")
    private BigDecimal useBalance;

    @ApiModelProperty(value = "可提余额")
    private BigDecimal withdrawBalance;

    @ApiModelProperty(value = "推广余额")
    private BigDecimal promoteBalance;

    @ApiModelProperty(value = "累计充值")
    private BigDecimal totalRecharge;

    @ApiModelProperty(value = "VIP等级")
    private Long vIp;

    @ApiModelProperty(value = "上级ID")
    private Long superior;

    @ApiModelProperty(value = "层级信息(上上级/上级)")
    private String levelInfo;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "身份证号码")
    private String idNumber;

    @ApiModelProperty(value = "手机号码")
    private String phoneNumber;

    @ApiModelProperty(value = "最后登陆IP")
    private String ip;

    @ApiModelProperty(value = "是否认证经理人(0-否 1-是)")
    private Integer isVerifyManager;

    @ApiModelProperty(value = "邀请码")
    private Long inviteCode;

    @ApiModelProperty(value = "状态(0-禁用 1-正常)")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Long create;

    @ApiModelProperty(value = "修改时间")
    private Long update;

    public PlayerTable(Players record) {

        this.setId(record.getId())
                .setAccount(record.getAccount())
                .setNickname(record.getNickname())
                .setUseBalance(record.getUseBalance())
                .setWithdrawBalance(record.getWithdrawBalance())
                .setPromoteBalance(record.getPromoteBalance())
                .setTotalRecharge(record.getTotalRecharge())
                .setVIp(record.getVIp())
                .setSuperior(record.getSuperior())
                .setLevelInfo(record.getLevelInfo())
                .setRealName(record.getRealName())
                .setIdNumber(record.getIdNumber())
                .setPhoneNumber(record.getPhoneNumber())
                .setId(record.getId())
                .setIsVerifyManager(record.getIsVerifyManager())
                .setInviteCode(record.getInviteCode())
                .setStatus(record.getStatus())
                .setCreate(record.getCreate())
                .setUpdate(record.getUpdate());
    }
}
