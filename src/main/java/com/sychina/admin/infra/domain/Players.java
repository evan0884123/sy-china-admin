package com.sychina.admin.infra.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 玩家信息
 *
 * @author Administrator
 */
@Data
@Accessors(chain = true)
@TableName("players")
public class Players {

    /**
     *
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * '账号'
     */
    private String account;

    /**
     * '密码(MD5值)'
     */
    private String password;

    /**
     * '昵称'
     */
    private String nickname;

    /**
     * '可用余额'
     */
    private BigDecimal useBalance;

    /**
     * '可提余额'
     */
    private BigDecimal withdrawBalance;

    /**
     * '推广余额'
     */
    private BigDecimal promoteBalance;

    /**
     * '累计充值'
     */
    private BigDecimal totalRecharge;

    /**
     * 'VIP等级'
     */
    private Long vIp;

    /**
     * '上级ID'
     */
    private Long superior;

    /**
     * '层级信息(上上级/上级)'
     */
    private String levelInfo;

    /**
     * '真实姓名'
     */
    private String realName;

    /**
     * '身份证号码'
     */
    private String idNumber;

    /**
     * '手机号码'
     */
    private String phoneNumber;

    /**
     * '最后登陆IP'
     */
    private String ip;

    /**
     * '是否认证经理人(0-否 1-是)'
     */
    private Integer isVerifyManager;

    /**
     * '邀请码'
     */
    private Long inviteCode;

    /**
     * '状态(0-禁用 1-正常)'
     */
    private Integer status;


    /**
     * '创建时间'
     */
    private Long create;

    /**
     * '修改时间'
     */
    private Long update;
}
