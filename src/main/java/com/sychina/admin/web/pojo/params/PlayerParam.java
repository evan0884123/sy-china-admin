package com.sychina.admin.web.pojo.params;

import com.sychina.admin.infra.domain.Players;
import com.sychina.admin.infra.domain.Projects;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Description:
 */
@Data
public class PlayerParam {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "VIP等级")
    private Long vIp;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "身份证号码")
    private String idNumber;

    @ApiModelProperty(value = "手机号码")
    private String phoneNumber;

    @ApiModelProperty(value = "是否认证经理人(0-未提交 1-审核中 2-已验证 3-审核不通过)")
    private Integer isVerifyManager;

    @ApiModelProperty(value = "状态(0-禁用 1-正常)")
    private Integer status;

    public Players convert(Players player) {

        player.setNickname(this.getNickname())
                .setVIp(this.getVIp())
                .setRealName(this.getRealName())
                .setIdNumber(this.getIdNumber())
                .setPhoneNumber(this.getPhoneNumber())
                .setIsVerifyManager(this.getIsVerifyManager())
                .setStatus(this.getStatus());

        return player;
    }

}
