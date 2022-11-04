package com.sychina.admin;

import com.sychina.admin.common.RedisKeys;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

@SpringBootTest
class SyChinaAdminApplicationTests {

    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() {

        for (int i = 0 ; i < 100 ; i++){
            Thread thread = new Thread(()->{

                String company = (String) redisTemplate.opsForSet().randomMember(RedisKeys.companies);
                System.out.println(company);
                Assert.isTrue(StringUtils.isNotBlank(company), "没有公司信息无法配置股权");
            });
            thread.run();
        }
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
