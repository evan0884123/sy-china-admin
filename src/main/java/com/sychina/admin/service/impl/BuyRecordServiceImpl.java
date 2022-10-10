package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.infra.domain.BuyRecords;
import com.sychina.admin.infra.mapper.BuyRecordMapper;
import com.sychina.admin.service.IBuyRecordService;
import com.sychina.admin.web.pojo.models.BankTableModel;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.BuyRecordQuery;

/**
 * @author Administrator
 */
public class BuyRecordServiceImpl extends ServiceImpl<BuyRecordMapper, BuyRecords> implements IBuyRecordService {

    /**
     *
     * @param buyRecordQuery
     * @return
     */
    public ResultModel loadTable(BuyRecordQuery buyRecordQuery) {

        Page<BankTableModel> table = baseMapper.loadTable(buyRecordQuery.page(), buyRecordQuery);

        return ResultModel.succeed(table);
    }
}
