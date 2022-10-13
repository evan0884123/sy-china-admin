package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.infra.domain.Debts;
import com.sychina.admin.infra.mapper.DebtMapper;
import com.sychina.admin.service.IDebtService;
import com.sychina.admin.utils.LocalDateTimeHelper;
import com.sychina.admin.web.pojo.models.DebtTable;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.DebtParam;
import com.sychina.admin.web.pojo.params.DebtQuery;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class DebtServiceImpl extends ServiceImpl<DebtMapper, Debts> implements IDebtService {

    public ResultModel add(DebtParam debtParam) {

        Debts debts = debtParam.convert()
                .setCreate(LocalDateTimeHelper.toLong(LocalDateTime.now()));

        baseMapper.insert(debts);

        return ResultModel.succeed();
    }

    public ResultModel loadTable(DebtQuery debtQuery) {

        QueryWrapper<Debts> wrapper = new QueryWrapper<>();
        wrapper.between(debtQuery.getTimeType() == 0, "create", debtQuery.getStartTime(), debtQuery.getEndTime());
        wrapper.between(debtQuery.getTimeType() == 1, "update", debtQuery.getStartTime(), debtQuery.getEndTime());

        IPage page = baseMapper.selectMapsPage(debtQuery.page(), wrapper);

        List<DebtTable> tables = new ArrayList<>();
        List<Debts> records = page.getRecords();
        records.forEach(record -> {
            tables.add(new DebtTable(record));
        });
        page.setRecords(tables);

        return ResultModel.succeed();
    }

    public ResultModel edit(DebtParam debtParam) {

        Debts debts = debtParam.convert()
                .setId(debtParam.getId())
                .setUpdate(LocalDateTimeHelper.toLong(LocalDateTime.now()));

        baseMapper.updateById(debts);

        return ResultModel.succeed();
    }

    public ResultModel delete(Long id) {

        baseMapper.deleteById(id);
        return ResultModel.succeed();
    }
}
