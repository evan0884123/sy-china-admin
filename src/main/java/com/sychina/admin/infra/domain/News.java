package com.sychina.admin.infra.domain;

import com.alibaba.fastjson.annotation.JSONField;
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
    @JSONField(name = "id")
    private Long id;

    /**
     * '标题'
     */
    @JSONField(name = "title")
    private String title;

    /**
     * 缩略图
     */
    @JSONField(name = "thumbnail")
    private String thumbnail;

    /**
     * '类型(0-纯文字 1-图文 2-视频)'
     */
    @JSONField(name = "type")
    private Integer type;

    /**
     * '内容'
     */
    @JSONField(name = "content")
    private String content;

    /**
     * '视频链接'
     */
    @JSONField(name = "video_link")
    private String videoLink;

    /**
     * '出处'
     */
    @JSONField(name = "author")
    private String author;

    /**
     * '标签(0-首页 1-资讯)'
     */
    @JSONField(name = "tab")
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
