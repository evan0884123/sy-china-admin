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
@TableName("companies")
public class Companies {

    /**
     *
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * '公司名称'
     */
    private String name;

    /**
     * '创建时间'
     */
    private Long create;

    /**
     * '修改时间'
     */
    private Long update;
}
