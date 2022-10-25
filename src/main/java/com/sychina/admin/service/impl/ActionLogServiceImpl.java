package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.infra.domain.ActionLog;
import com.sychina.admin.infra.mapper.ActionLogMapper;
import com.sychina.admin.service.IActionLogService;
import com.sychina.admin.web.pojo.models.AccessLogTable;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.AccessLogQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class ActionLogServiceImpl extends ServiceImpl<ActionLogMapper, ActionLog> implements IActionLogService {

    public ResultModel loadTable(AccessLogQuery query) {


        QueryWrapper<ActionLog> wrapper = new QueryWrapper<>();
        wrapper.likeRight(StringUtils.isNotBlank(query.getAdminUserName()), "admin_user_name", query.getAdminUserName());
        wrapper.between("`create`", query.getStartTime(), query.getEndTime());

        IPage page = baseMapper.selectPage(query.page(), wrapper);

        List<AccessLogTable> tables = new ArrayList<>();
        List<ActionLog> records = page.getRecords();
        records.forEach(record -> {
            tables.add(new AccessLogTable(record));
        });
        page.setRecords(tables);

        return ResultModel.succeed(page);
    }
}
