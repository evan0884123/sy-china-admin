package com.sychina.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.common.RedisKeys;
import com.sychina.admin.infra.domain.Config;
import com.sychina.admin.infra.mapper.ConfigMapper;
import com.sychina.admin.service.IConfigService;
import com.sychina.admin.utils.LocalDateTimeHelper;
import com.sychina.admin.web.pojo.models.ConfigTable;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.ConfigParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements IConfigService {

    private ProjectRecordServiceImpl projectRecordService;

    private RedisTemplate redisTemplate;

    public ResultModel loadTable() {

        Config config = baseMapper.selectById(1L);

        List<ConfigTable> configTables = new ArrayList<>();
        configTables.add(new ConfigTable(config));

        return ResultModel.succeed(configTables);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultModel edit(ConfigParam configParam) {

        Config config = getById(configParam.getId());
        Assert.notNull(config, "没有这个配置信息");
        configParam.convert(config)
                .setUpdate(LocalDateTimeHelper.toLong(LocalDateTime.now()));

        projectRecordService.update().set(config.getSmSwitch() == 1, "status", 1);
        baseMapper.updateById(config);
        redisTemplate.opsForValue().set(RedisKeys.config, JSON.toJSONString(config));

        return ResultModel.succeed();
    }

    @Autowired
    public void setProjectRecordService(ProjectRecordServiceImpl projectRecordService) {
        this.projectRecordService = projectRecordService;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
