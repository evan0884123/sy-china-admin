package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.infra.domain.AccountChanges;
import com.sychina.admin.infra.mapper.AccountChangeMapper;
import com.sychina.admin.service.IAccountChangeService;
import com.sychina.admin.web.pojo.models.AccountChangeTable;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.AccountChangeQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class AccountChangeServiceImpl extends ServiceImpl<AccountChangeMapper, AccountChanges> implements IAccountChangeService {

    public ResultModel loadTable(AccountChangeQuery recordQuery) {

        QueryWrapper<AccountChanges> wrapper = new QueryWrapper<>();
        wrapper.likeRight(StringUtils.isNotBlank(recordQuery.getPlayerName()), "player_name", recordQuery.getPlayerName());
        wrapper.eq(recordQuery.getAmountType() != null, "amount_type", recordQuery.getAmountType());
        wrapper.eq(recordQuery.getChangeType() != null, "change_type", recordQuery.getChangeType());
        wrapper.eq(StringUtils.isNotBlank(recordQuery.getConnId()), "conn_id", recordQuery.getConnId());
        wrapper.between(recordQuery.getTimeType() == 0, "`create`", recordQuery.getStartTime(), recordQuery.getEndTime());
        wrapper.between(recordQuery.getTimeType() == 1, "`update`", recordQuery.getStartTime(), recordQuery.getEndTime());

        IPage page = baseMapper.selectPage(recordQuery.page(), wrapper);

        List<AccountChangeTable> tables = new ArrayList<>();
        List<AccountChanges> records = page.getRecords();
        records.forEach(record -> {
            tables.add(new AccountChangeTable(record));
        });
        page.setRecords(tables);

        return ResultModel.succeed(page);
    }

}
