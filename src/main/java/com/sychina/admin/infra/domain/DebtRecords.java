package com.sychina.admin.infra.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 *
 *
 * @author Administrator
 */
@Data
@TableName("debt_records")
public class DebtRecords {

    /**
     *
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * '国债ID'
     */
    private Long debt;

    /**
     * '国债名称'
     */
    private String debtName;

    /**
     * '国债编号'
     */
    private String debtNumbering;


    /**
     * '玩家ID'
     */
    private Long player;


    /**
     * '玩家姓名'
     */
    private String playerName;


    /**
     * '投资转化(0-未 1-已)'
     */
    private Integer invest;

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
