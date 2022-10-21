package com.sychina.admin.infra.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
    @TableId(value = "id", type = IdType.AUTO)
    @JSONField(name = "id")
    private Long id;

    /**
     * '账号'
     */
    @JSONField(name = "account")
    private String account;

    /**
     * '密码(MD5值)'
     */
    @JSONField(name = "password")
    private String password;

    /**
     * '昵称'
     */
    @JSONField(name = "nickname")
    private String nickname;

    /**
     * '可用余额'
     */
    @JSONField(name = "use_balance")
    private BigDecimal useBalance;

    /**
     * '可提余额'
     */
    @JSONField(name = "withdraw_balance")
    private BigDecimal withdrawBalance;

    /**
     * '推广余额'
     */
    @JSONField(name = "promote_balance")
    private BigDecimal promoteBalance;

    /**
     * '今日收益'
     */
    @JSONField(name = "today_promote")
    private BigDecimal todayPromote;

    /**
     * '项目可提现收益'
     */
    @JSONField(name = "project_balance")
    private BigDecimal projectBalance;

    /**
     * '累计充值'
     */
    @JSONField(name = "total_recharge")
    private BigDecimal totalRecharge;

    /**
     * 'VIP等级'
     */
    @JSONField(name = "v_ip")
    private Long vIp;

    /**
     * '上级ID'
     */
    @JSONField(name = "superior")
    private Long superior;

    /**
     * '层级信息(上上级/上级)'
     */
    @JSONField(name = "level_info")
    private String levelInfo;

    /**
     * '真实姓名'
     */
    @JSONField(name = "real_name")
    private String realName;

    /**
     * '身份证号码'
     */
    @JSONField(name = "id_number")
    private String idNumber;

    /**
     * '手机号码'
     */
    @JSONField(name = "phone_number")
    private String phoneNumber;

    /**
     * '最后登陆IP'
     */
    @JSONField(name = "ip")
    private String ip;

    /**
     * '是否认证经理人(0-未提交 1-审核中 2-已验证)'
     */
    @JSONField(name = "is_verify_manager")
    private Integer isVerifyManager;

    /**
     * '邀请码'
     */
    @JSONField(name = "invite_code")
    private Long inviteCode;

    /**
     * '状态(0-禁用 1-正常)'
     */
    @JSONField(name = "status")
    private Integer status;

    /**
     * '身份证正面照'
     */
    @JSONField(name = "id_front_img")
    private String idFrontImg;

    /**
     * '身份证反面照'
     */
    @JSONField(name = "id_back_img")
    private String idBackImg;

    /**
     * '本人正面照'
     */
    @JSONField(name = "people_img")
    private String peopleImg;

    /**
     * '本人手持身份证正面照'
     */
    @JSONField(name = "people_with_id_img")
    private String peopleWithIdImg;

    /**
     * '最后登录时间'
     */
    @JSONField(name = "last_login_time")
    private Long lastLoginTime;

    /**
     * '创建时间'
     */
    @TableField("`create`")
    @JSONField(name = "create")
    private Long create;

    /**
     * '修改时间'
     */
    @TableField("`update`")
    @JSONField(name = "update")
    private Long update;
}
