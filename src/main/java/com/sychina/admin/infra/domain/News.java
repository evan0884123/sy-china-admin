package com.sychina.admin.infra.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 咨询信息
 *
 * @author Administrator
 */
@Data
@Accessors(chain = true)
@TableName("news")
public class News {

    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * '标题'
     */
    private String title;

    /**
     * 缩略图
     */
    private String thumbnail;

    /**
     * '类型(0-纯文字 1-图文 2-视频)'
     */
    private Integer type;

    /**
     * '内容'
     */
    private String content;

    /**
     * '视频链接'
     */
    private String videoLink;

    /**
     * '出处'
     */
    private String author;

    /**
     * '标签(0-首页 1-资讯)'
     */
    private Integer tab;

    /**
     * '创建时间'
     */
    @TableField("`create`")
    private Long create;

    /**
     * '修改时间'
     */
    @TableField("`update`")
    private Long update;
}
