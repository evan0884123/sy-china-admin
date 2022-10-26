package com.sychina.admin.web.pojo.models;

import com.sychina.admin.infra.domain.Companies;
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
public class CompanyTable {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "公司名称")
    private String name;

    @ApiModelProperty(value = "创建时间")
    private Long create;

    @ApiModelProperty(value = "修改时间")
    private Long update;

    public CompanyTable(Companies record) {

        this.setId(record.getId())
                .setName(record.getName())
                .setCreate(record.getCreate())
                .setUpdate(record.getUpdate());
    }
}
