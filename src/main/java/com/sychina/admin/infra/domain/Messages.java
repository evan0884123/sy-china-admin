package com.sychina.admin.infra.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 通知 信息
 *
 * @author Administrator
 */
@Data
@Accessors(chain = true)
@TableName("messages")
public class Messages {

    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * '玩家ID'
     */
    private Long player;

    /**
     * '玩家昵称'
     */
    private String playerName;

    /**
     * '标题
     */
    private String title;

    /**
     * '内容'
     */
    private String content;

    /**
     * '已读(0-未读 1-已读)'
     */
    private Integer hadRead;

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
