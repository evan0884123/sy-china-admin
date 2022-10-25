package com.sychina.admin.infra.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Description:
 *
 */
@Data
@TableName(value = "admin_action_log")
public class ActionLog {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 操作人员 id
     */
    private String userId;

    /**
     * 操作人员账号
     */
    private String adminUserName;

    /**
     * 操作方法
     */
    private String method;

    /**
     * 操作方法 描述
     */
    private String methodDes;

    /**
     * 操作参数
     */
    private String parameters;

    /**
     * 操作结果,参考枚举类：{@link com.sychina.admin.web.pojo.models.response.ResponseStatus#code}
     */
    private Integer result;

    /**
     * 返回值
     */
    private String returnValue;

    /**
     * 操作时间
     */
    private String actionTime;

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
