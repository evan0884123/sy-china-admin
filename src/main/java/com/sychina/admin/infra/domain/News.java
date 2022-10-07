package com.sychina.admin.infra.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 咨询信息
 *
 * @author Administrator
 */
@Data
@TableName("news")
public class News {

    /**
     *
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * '标题'
     */
    private String title;

    /**
     *
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
     * '创建时间'
     */
    private Long create;

    /**
     * '修改时间'
     */
    private Long update;
}
