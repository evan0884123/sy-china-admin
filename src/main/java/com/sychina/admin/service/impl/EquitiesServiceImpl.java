package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.infra.domain.Debts;
import com.sychina.admin.infra.domain.Equities;
import com.sychina.admin.infra.mapper.DebtMapper;
import com.sychina.admin.infra.mapper.EquitiesMapper;
import com.sychina.admin.service.IDebtService;
import com.sychina.admin.service.IEquitiesService;
import com.sychina.admin.web.pojo.models.BankTable;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.BankParam;
import com.sychina.admin.web.pojo.params.BankQuery;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
public class EquitiesServiceImpl extends ServiceImpl<EquitiesMapper, Equities> implements IEquitiesService {

    /**
     *
     * @param bankQuery
     * @return
     */
    public ResultModel loadTable(BankQuery bankQuery) {

        Page<BankTable> table = baseMapper.loadTable(bankQuery.page(), bankQuery);

        return ResultModel.succeed(table);
    }

    /**
     *
     * @param bankParam
     * @return
     */
    public ResultModel edit(BankParam bankParam) {

        baseMapper.updateById(bankParam.convert());
        return ResultModel.succeed();
    }

    /**
     *
     * @param id
     * @return
     */
    public ResultModel delete(Integer id) {

        baseMapper.deleteById(id);
        return ResultModel.succeed();
    }
}
