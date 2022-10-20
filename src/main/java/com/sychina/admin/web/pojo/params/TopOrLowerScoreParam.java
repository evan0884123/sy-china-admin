package com.sychina.admin.web.pojo.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * Description:
 *
 * @author Administrator
 */
@Data
public class TopOrLowerScoreParam {

    @ApiModelProperty(value = "id list", required = true)
    @NotNull
    private List<Long> ids;

    @ApiModelProperty(value = "操作类型，0-上分，1-下分", required = true)
    @NotNull
    private Integer operationType;

    @ApiModelProperty(value = "操作金额类型，0-可用余额，1-可提余额，2-推广余额，3-项目可提现收益 ", required = true)
    @NotNull
    private Integer type;

    @ApiModelProperty(value = "变动金额", required = true)
    @NotNull
    private BigDecimal score;

    @ApiModelProperty(value = "备注")
    private String remark;

}
