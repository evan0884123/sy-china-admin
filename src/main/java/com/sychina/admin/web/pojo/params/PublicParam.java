package com.sychina.admin.web.pojo.params;

import com.sychina.admin.infra.domain.Public;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author Administrator
 */
@Data
public class PublicParam {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "内容")
    private String content;

    public Public convert() {

        Public aPublic = new Public();
        aPublic.setContent(this.getContent());

        return aPublic;
    }
}
