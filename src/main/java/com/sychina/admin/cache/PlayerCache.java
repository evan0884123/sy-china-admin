package com.sychina.admin.cache;

import com.alibaba.fastjson.JSON;
import com.sychina.admin.common.RedisKeys;
import com.sychina.admin.infra.domain.Players;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Component
public class PlayerCache {

    private RedisTemplate redisTemplate;


    public void setPlayerCache(Players players) {

        redisTemplate.opsForHash()
                .put(RedisKeys.playersIDMap, players.getId().toString(), JSON.toJSONString(players));

        redisTemplate.opsForHash()
                .put(RedisKeys.playersAccountMap, players.getAccount(), JSON.toJSONString(players));

        redisTemplate.opsForHash()
                .put(RedisKeys.playersAccPassMap, players.getAccount(), players.getPassword());

    }

    public void delPlayerCache(Players players) {

        redisTemplate.opsForHash().delete(RedisKeys.playersIDMap, players.getId().toString());

        redisTemplate.opsForHash().delete(RedisKeys.playersAccountMap, players.getAccount());

        redisTemplate.opsForHash().delete(RedisKeys.playersAccPassMap, players.getAccount());

    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
