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
    String PlayersIDMap = "PlayersIDMap";


    /**
     * 公司信息的缓存
     */
    String Companies = "Companies";
}
