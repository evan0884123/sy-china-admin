package com.sychina.admin.infra.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 通知 信息
 *
 * @author Administrator
 */
@Data
@TableName("messages")
public class Messages {

    /**
     *
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * '玩家ID'
     */
    private Long player;

    /**
     * '标题
     */
    private Long title;

    /**
     * '内容'
     */
    private Integer content;

    /**
     * '已读(0-未读 1-已读)'
     */
    private Integer hadRead;

    /**
     * '创建时间'
     */
    private Long create;

    /**
     * '修改时间'
     */
    private Long update;
}
