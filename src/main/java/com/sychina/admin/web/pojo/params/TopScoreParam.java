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
public class TopScoreParam {

    @ApiModelProperty(value = "id list", required = true)
    @NotNull
    private List<Long> ids;

    @ApiModelProperty(value = "需要添加的金额", required = true)
    @NotNull
    private BigDecimal score;

    @ApiModelProperty(value = "备注")
    private String remark;

}
