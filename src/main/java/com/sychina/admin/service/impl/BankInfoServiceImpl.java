package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.infra.domain.BankInfos;
import com.sychina.admin.infra.mapper.BankInfoMapper;
import com.sychina.admin.service.IBankInfoService;
import com.sychina.admin.web.pojo.models.BankTableModel;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.BankParam;
import com.sychina.admin.web.pojo.params.BankQuery;

/**
 * @author Administrator
 */
public class BankInfoServiceImpl extends ServiceImpl<BankInfoMapper, BankInfos> implements IBankInfoService {

    /**
     *
     * @param bankQuery
     * @return
     */
    public ResultModel loadTable(BankQuery bankQuery) {

        Page<BankTableModel> table = baseMapper.loadTable(bankQuery.page(), bankQuery);

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
