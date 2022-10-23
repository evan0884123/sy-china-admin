package com.sychina.admin.web.pojo.models;

import com.sychina.admin.infra.domain.Public;
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
public class PublicTable {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "创建时间")
    private Long create;

    @ApiModelProperty(value = "修改时间")
    private Long update;

    public PublicTable(Public record) {

        this.setId(record.getId())
                .setContent(record.getContent())
                .setCreate(record.getCreate())
                .setUpdate(record.getUpdate());
    }
}
