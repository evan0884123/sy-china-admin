package com.sychina.admin.infra.domain;

import com.baomidou.mybatisplus.annotation.IdType;
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
    private String id;

    /**
     * '国债ID'
     */
    private String debt;

    /**
     * '国债名称'
     */
    private String debtName;


    /**
     * '玩家ID'
     */
    private String player;


    /**
     * '玩家姓名'
     */
    private String playerName;


    /**
     * '投资转化(0-未 1-已)'
     */
    private String invest;

    /**
     * '创建时间'
     */
    private Long create;

    /**
     * '修改时间'
     */
    private Long update;
}
