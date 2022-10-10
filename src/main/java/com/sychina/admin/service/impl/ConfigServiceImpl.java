package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.infra.domain.Config;
import com.sychina.admin.infra.mapper.ConfigMapper;
import com.sychina.admin.service.IConfigService;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.ConfigParam;
import com.sychina.admin.web.pojo.params.ConfigQuery;

/**
 * @author Administrator
 */
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements IConfigService {
    public ResultModel loadTable(ConfigQuery configQuery) {
    }

    public ResultModel edit(ConfigParam configParam) {
    }
}
