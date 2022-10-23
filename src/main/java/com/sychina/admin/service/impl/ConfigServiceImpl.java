package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.infra.domain.Config;
import com.sychina.admin.infra.mapper.ConfigMapper;
import com.sychina.admin.service.IConfigService;
import com.sychina.admin.utils.LocalDateTimeHelper;
import com.sychina.admin.web.pojo.models.ConfigTable;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.ConfigParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements IConfigService {

    private ProjectRecordServiceImpl projectRecordService;

    public ResultModel loadTable() {

        Config config = baseMapper.selectById(1L);

        List<ConfigTable> configTables = new ArrayList<>();
        configTables.add(new ConfigTable(config));

        return ResultModel.succeed(configTables);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultModel edit(ConfigParam configParam) {

        Config config = configParam.convert()
                .setUpdate(LocalDateTimeHelper.toLong(LocalDateTime.now()));

        projectRecordService.update().set(config.getSmSwitch() == 1,"status", 1);
        baseMapper.updateById(config);

        return ResultModel.succeed();
    }

    @Autowired
    public void setProjectRecordService(ProjectRecordServiceImpl projectRecordService) {
        this.projectRecordService = projectRecordService;
    }
}
