package com.sychina.admin.web.pojo.models;

import com.sychina.admin.infra.domain.Banners;
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
public class BannerTable {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "栏目banner(0-首页 1-投资 2-咨询)")
    private Integer tab;

    @ApiModelProperty(value = "图片")
    private String img;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "状态(0-关闭 1-开启)")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Long create;

    @ApiModelProperty(value = "修改时间")
    private Long update;

    public BannerTable convert(Banners banner) {

        this.setId(banner.getId())
                .setTab(banner.getTab())
                .setImg(banner.getImg())
                .setContent(banner.getContent())
                .setStatus(banner.getStatus())
                .setCreate(banner.getCreate())
                .setUpdate(banner.getUpdate());

        return this;
    }
}
