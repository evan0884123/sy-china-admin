package com.sychina.admin.web.pojo.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author Administrator
 */
@Data
public class HallStageStaQuery {

    @ApiModelProperty(value = "需要查询的阶段参数, 0-周，1-月")
    private Integer stage;

    @ApiModelProperty(value = "距离这周/月的数，比如：0-这个周/月，1-上周/月")
    private Integer count;
}
