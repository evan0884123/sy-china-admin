package com.sychina.admin.web.pojo.params;

import com.sychina.admin.infra.domain.Banners;
import com.sychina.admin.infra.domain.Equities;
import com.sychina.admin.web.pojo.params.page.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.Assert;

import java.math.BigDecimal;

/**
 * Description:
 *
 * @author Administrator
 */
@Data
public class EquitiesParam {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "玩家姓名")
    private String playerName;

    @ApiModelProperty(value = "数量")
    private BigDecimal amount;

    public Equities convert(){

        Assert.isTrue(this.getAmount().compareTo(BigDecimal.ZERO) > 0, "股权不能为负数");
        Equities equities = new Equities();
        equities.setPlayerName(this.getPlayerName())
                .setAmount(this.getAmount());

        return equities;
    }
}
