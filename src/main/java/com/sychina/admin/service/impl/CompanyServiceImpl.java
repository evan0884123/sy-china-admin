package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.common.RedisKeys;
import com.sychina.admin.infra.domain.Companies;
import com.sychina.admin.infra.mapper.CompanyMapper;
import com.sychina.admin.service.ICompanyService;
import com.sychina.admin.utils.LocalDateTimeHelper;
import com.sychina.admin.web.pojo.models.CompanyTable;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Companies> implements ICompanyService {

    private RedisTemplate redisTemplate;

    public ResultModel add(String content) {

        Companies companies = new Companies().setName(content)
                .setCreate(LocalDateTimeHelper.toLong(LocalDateTime.now()));

        baseMapper.insert(companies);
        redisTemplate.opsForSet().add(RedisKeys.companies, content);

        return ResultModel.succeed();
    }

    public ResultModel loadTable() {


        List<CompanyTable> tables = new ArrayList<>();
        List<Companies> companiesList = list();
        companiesList.forEach(companies -> {
            tables.add(new CompanyTable(companies));
        });

        return ResultModel.succeed(tables);
    }

    public ResultModel delete(Long id) {

        Companies companies = getById(id);
        Assert.notNull(companies, "未找到该公司信息");

        baseMapper.deleteById(id);
        redisTemplate.opsForSet().remove(RedisKeys.companies, companies.getName());

        return ResultModel.succeed();
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
