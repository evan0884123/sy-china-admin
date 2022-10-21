package com.sychina.admin.web.pojo.models;

import com.sychina.admin.infra.domain.Payouts;
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
public class PayoutTable {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "支付方式(0-人工 1-微信 2-支付宝 3-云闪付)")
    private Integer type;

    @ApiModelProperty(value = "支付链接")
    private String url;

    @ApiModelProperty(value = "创建时间")
    private Long create;

    @ApiModelProperty(value = "修改时间")
    private Long update;

    public PayoutTable(Payouts record) {

        this.setId(record.getId())
                .setType(record.getType())
                .setUrl(record.getUrl())
                .setCreate(record.getCreate())
                .setUpdate(record.getUpdate());
    }
}
