package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.infra.domain.Config;
import com.sychina.admin.infra.mapper.ConfigMapper;
import com.sychina.admin.service.IConfigService;
import com.sychina.admin.utils.LocalDateTimeHelper;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.ConfigParam;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements IConfigService {

    public ResultModel loadTable() {

        List<Config> configs = baseMapper.selectList(new QueryWrapper<>());

        return ResultModel.succeed(configs);
    }

    public ResultModel edit(ConfigParam configParam) {

        Config config = configParam.convert()
                .setUpdate(LocalDateTimeHelper.toLong(LocalDateTime.now()));

        baseMapper.updateById(config);

        return ResultModel.succeed();
    }
}
