package com.sychina.admin.common;

/**
 * @author Administrator
 */
public interface RedisKeys {

    /**
     * redisLock 的锁名
     */
    String playBalanceChange = "PLAYER_BALANCE_CHANGE_";

    /**
     * 用户信息的缓存
     */
    String playersIDMap = "PlayersIDMap";


    /**
     * hash
     * 公司信息的缓存
     */
    String companies = "Companies";


    /**
     * hash
     * 咨询
     */
    String news = "NEWS";

    /**
     * String
     * 配置
     */
    String config = "CONFIG";

    /**
     * hash
     * banner 图信息
     */
    String banner = "BANNER";

    /**
     * hash
     * 公告 信息
     */
    String aPublic = "PUBLIC";


    /**
     * hash
     * 国债信息
     */
    String debts = "DEBTS";
}
