package com.sychina.admin.web.pojo.models;

import com.sychina.admin.infra.domain.Debts;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Description:
 *
 * @author Administrator
 */
@Data
@Accessors(chain = true)
public class DebtTable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "编号")
    private String numbering;

    @ApiModelProperty(value = "口号")
    private String slogan;

    @ApiModelProperty(value = "项目金额")
    private String price;

    @ApiModelProperty(value = "国债年利率")
    private String apr;

    @ApiModelProperty(value = "年限")
    private String term;

    @ApiModelProperty(value = "总计金额")
    private String total;

    @ApiModelProperty(value = "挂载项目")
    private String mount;

    @ApiModelProperty(value = "状态(0-关闭 1-启用)")
    private Integer status;

    @ApiModelProperty(value = "是否售罄(0-否 1-是)")
    private Integer soldOut;

    @ApiModelProperty(value = "创建时间")
    private Long create;

    @ApiModelProperty(value = "修改时间")
    private Long update;

    public DebtTable(Debts record) {

        this.setId(record.getId())
                .setName(record.getName())
                .setNumbering(record.getNumbering())
                .setSlogan(record.getSlogan())
                .setPrice(record.getPrice())
                .setApr(record.getApr())
                .setTerm(record.getTerm())
                .setTotal(record.getTotal())
                .setMount(record.getMount() != null ? record.getMount().substring(1, record.getMount().length() - 1) : "")
                .setStatus(record.getStatus())
                .setSoldOut(record.getSoldOut())
                .setCreate(record.getCreate())
                .setUpdate(record.getUpdate());
    }

}
