package com.sychina.admin.infra.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 公司信息
 *
 * @author Administrator
 */
@Data
@TableName("debts")
public class Debts {

    /**
     *
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * '名称'
     */
    private String name;

    /**
     * '口号'
     */
    private String slogan;


    /**
     * '项目金额'
     */
    private String price;


    /**
     * '国债年利率'
     */
    private String apr;


    /**
     * '年限'
     */
    private String term;

    /**
     * '总计金额'
     */
    private String total;


    /**
     * '挂载项目'
     */
    private String mount;


    /**
     * '状态(0-关闭 1-启用)'
     */
    private String status;

    /**
     * '创建时间'
     */
    private Long create;

    /**
     * '修改时间'
     */
    private Long update;
}
