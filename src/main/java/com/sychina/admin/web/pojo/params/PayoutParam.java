package com.sychina.admin.web.pojo.params;

import com.sychina.admin.infra.domain.Payouts;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author Administrator
 */
@Data
public class PayoutParam {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "支付方式(0-人工 1-微信 2-支付宝 3-云闪付)")
    private Integer type;

    @ApiModelProperty(value = "支付链接")
    private String url;

    public Payouts convert() {

        Payouts payouts = new Payouts();
        payouts.setType(this.getType())
                .setUrl(this.getUrl());

        return payouts;
    }
}
