package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.infra.domain.Payouts;
import com.sychina.admin.infra.mapper.PayoutMapper;
import com.sychina.admin.service.IPayoutService;
import com.sychina.admin.utils.LocalDateTimeHelper;
import com.sychina.admin.web.pojo.models.PayoutTable;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.PayoutParam;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class PayoutServiceImpl extends ServiceImpl<PayoutMapper, Payouts> implements IPayoutService {

    public ResultModel add(PayoutParam payoutParam) {

        Payouts payouts = payoutParam.convert()
                .setCreate(LocalDateTimeHelper.toLong(LocalDateTime.now()));
        baseMapper.insert(payouts);

        return ResultModel.succeed();
    }

    public ResultModel loadTable() {

        List<Payouts> payouts = list();

        List<PayoutTable> tables = new ArrayList<>();
        payouts.forEach(payout -> {
            tables.add(new PayoutTable(payout));
        });

        return ResultModel.succeed(tables);
    }

    public ResultModel edit(PayoutParam payoutParam) {

        Payouts payouts = payoutParam.convert()
                .setUpdate(LocalDateTimeHelper.toLong(LocalDateTime.now()))
                .setId(payoutParam.getId());

        baseMapper.updateById(payouts);

        return ResultModel.succeed();
    }

    public ResultModel delete(Long id) {

        baseMapper.deleteById(id);

        return ResultModel.succeed();
    }
}
