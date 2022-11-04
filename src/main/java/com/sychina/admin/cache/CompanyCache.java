package com.sychina.admin.cache;

import com.sychina.admin.common.RedisKeys;
import com.sychina.admin.infra.domain.Companies;
import com.sychina.admin.service.impl.CompanyServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@Component
public class CompanyCache {

    private CompanyServiceImpl companyService;

    private RedisTemplate redisTemplate;

    public String getCompanyInfo() {
        String company = (String) redisTemplate.opsForSet().randomMember(RedisKeys.companies);
        if (StringUtils.isEmpty(company)) {
            synchronized (this) {
                company = (String) redisTemplate.opsForSet().randomMember(RedisKeys.companies);
                if (StringUtils.isEmpty(company)) {
                    Set<String> strings = companyService.list().stream().map(Companies::getName).collect(Collectors.toSet());
                    strings.forEach(s -> {
                        redisTemplate.opsForSet().add(RedisKeys.companies, s);
                    });
                    company = strings.stream().findAny().get();
                }
            }
        }
        return company;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setCompanyService(CompanyServiceImpl companyService) {
        this.companyService = companyService;
    }
}
