package com.sychina.admin.web.pojo.params;

import com.sychina.admin.web.pojo.params.page.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Description:
 *
 * @author Administrator
 */
@Data
public class DebtRecordQuery extends PageQuery {

    @ApiModelProperty(value = "国债名称")
    private String debtName;

    @ApiModelProperty(value = "玩家姓名")
    private String playerName;

    @ApiModelProperty(value = "投资转化(0-未 1-已)")
    private Integer invest;

    @ApiModelProperty(value = "0-创建时间, 1-修改时间")
    @NotNull
    private Integer timeType;
}
