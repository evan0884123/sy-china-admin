package com.sychina.admin.infra.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * banner 信息
 *
 * @author Administrator
 */
@Data
@Accessors(chain = true)
@TableName("chickens")
public class Chickens {

    /**
     *
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
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
     * '来源类型(0-注册即赠送 1-首充1000送 2-认购)'
     */
    private Integer type;

    /**
     * '蛋(0-无 1-有)'
     */
    private Integer egg;

    /**
     * '上次开启时间'
     */
    private Long lastOpenTime;

    /**
     * '创建时间'
     */
    private Long create;

    /**
     * '修改时间'
     */
    private Long update;
}
