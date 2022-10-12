//package com.sychina.admin.infra.domain;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableId;
//import com.baomidou.mybatisplus.annotation.TableName;
//import lombok.Data;
//
//import java.math.BigDecimal;
//
///**
// * 购买记录
// *
// * @author Administrator
// */
//@Data
//@TableName("buy_records")
//public class BuyRecords {
//
//    /**
//     *
//     */
//    @TableId(value = "id", type = IdType.ASSIGN_ID)
//    private String id;
//
//    /**
//     * '订单记录'
//     */
//    private String orderNumber;
//
//    /**
//     * '项目ID'
//     */
//    private Long project;
//
//    /**
//     * '玩家ID'
//     */
//    private Long player;
//
//    /**
//     * '关联认购记录ID'
//     */
//    private Long connRecordId;
//
//    /**
//     * '来源(0-默认来源 1-充值赠送来源 2-限购认购来源)'
//     */
//    private Integer type;
//
//    /**
//     * '是否收取(0-未收取 1-已经收取)'
//     */
//    private Integer collect;
//
//    /**
//     * '累计收益'
//     */
//    private BigDecimal totalProfit;
//
//    /**
//     * '提现门槛'
//     */
//    private BigDecimal withdrawThreshold;
//
//    /**
//     * '配置'
//     */
//    private String config;
//
//    /**
//     * '失效时间(0-无限有效)'
//     */
//    private Integer stopTime;
//
//    /**
//     * '状态(0-过期 1-正常)'
//     */
//    private Integer status;
//
//    /**
//     * '创建时间'
//     */
//    private Long create;
//
//    /**
//     * '修改时间'
//     */
//    private Long update;
//}
