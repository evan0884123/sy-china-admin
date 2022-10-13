package com.sychina.admin.infra.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 *
 * @author Administrator
 */
@Data
@Accessors(chain = true)
@TableName("equities")
public class Equities {

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
     * '数量'
     */
    private BigDecimal amount;

    /**
     * '公司名称'
     */
    private String company;

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
