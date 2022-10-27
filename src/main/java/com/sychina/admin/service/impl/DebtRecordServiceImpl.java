package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.infra.domain.DebtRecords;
import com.sychina.admin.infra.mapper.DebtRecordMapper;
import com.sychina.admin.service.IDebtRecordService;
import com.sychina.admin.web.pojo.models.DebtRecordTable;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.DebtRecordQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class DebtRecordServiceImpl extends ServiceImpl<DebtRecordMapper, DebtRecords> implements IDebtRecordService {

    public ResultModel loadTable(DebtRecordQuery recordQuery) {

        QueryWrapper<DebtRecords> wrapper = new QueryWrapper<>();
        wrapper.likeRight(StringUtils.isNotBlank(recordQuery.getDebtName()), "debt_name", recordQuery.getDebtName());
        wrapper.likeRight(StringUtils.isNotBlank(recordQuery.getPlayerName()), "player_name", recordQuery.getPlayerName());
        wrapper.likeRight(StringUtils.isNotBlank(recordQuery.getDebtNumbering()), "debt_numbering", recordQuery.getDebtNumbering());
        wrapper.eq(recordQuery.getInvest() != null, "invest", recordQuery.getInvest());
        wrapper.between(recordQuery.getTimeType() == 0, "`create`", recordQuery.getStartTime(), recordQuery.getEndTime());
        wrapper.between(recordQuery.getTimeType() == 1, "`update`", recordQuery.getStartTime(), recordQuery.getEndTime());
        wrapper.orderByDesc("`create`");

        IPage page = baseMapper.selectPage(recordQuery.page(), wrapper);

        List<DebtRecordTable> tables = new ArrayList<>();
        List<DebtRecords> records = page.getRecords();
        records.forEach(record -> {
            tables.add(new DebtRecordTable(record));
        });
        page.setRecords(tables);

        return ResultModel.succeed(page);
    }
}
