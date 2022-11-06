package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.infra.domain.BankInfos;
import com.sychina.admin.infra.mapper.BankInfoMapper;
import com.sychina.admin.service.IBankInfoService;
import com.sychina.admin.utils.LocalDateTimeHelper;
import com.sychina.admin.web.pojo.models.BankTable;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.BankParam;
import com.sychina.admin.web.pojo.params.BankQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class BankInfoServiceImpl extends ServiceImpl<BankInfoMapper, BankInfos> implements IBankInfoService {

    public ResultModel add(BankParam bankParam) {

        BankInfos bankInfos = bankParam.convert()
                .setCreate(LocalDateTimeHelper.toLong(LocalDateTime.now()));

        baseMapper.insert(bankInfos);
        return ResultModel.succeed();
    }

    /**
     * @param bankQuery
     * @return
     */
    public ResultModel loadTable(BankQuery bankQuery) {

        QueryWrapper<BankInfos> wrapper = new QueryWrapper<>();
        wrapper.likeRight(StringUtils.isNotBlank(bankQuery.getPlayerName()), "player_name", bankQuery.getPlayerName());
        wrapper.likeRight(StringUtils.isNotBlank(bankQuery.getBank()), "bank", bankQuery.getBank());
        wrapper.eq(bankQuery.getVerified() != null, "verified", bankQuery.getVerified());
        wrapper.between(bankQuery.getTimeType() == 0  && bankQuery.getStartTime() != null && bankQuery.getEndTime() != null,
                "`create`", bankQuery.getStartTime(), bankQuery.getEndTime());
        wrapper.between(bankQuery.getTimeType() == 1  && bankQuery.getStartTime() != null && bankQuery.getEndTime() != null,
                "`update`", bankQuery.getStartTime(), bankQuery.getEndTime());
        wrapper.orderByDesc("`create`");

        IPage page = baseMapper.selectPage(bankQuery.page(), wrapper);

        List<BankTable> tables = new ArrayList<>();
        List<BankInfos> records = page.getRecords();
        records.forEach(record -> {
            tables.add(new BankTable(record));
        });
        page.setRecords(tables);

        return ResultModel.succeed(page);
    }

    /**
     * @param bankParam
     * @return
     */
    public ResultModel edit(BankParam bankParam) {

        Assert.notNull(bankParam.getId(), "id不能为空");

        BankInfos bankInfos = bankParam.convert()
                .setId(bankParam.getId())
                .setUpdate(LocalDateTimeHelper.toLong(LocalDateTime.now()));

        baseMapper.updateById(bankInfos);
        return ResultModel.succeed();
    }

    /**
     * @param id
     * @return
     */
    public ResultModel delete(Long id) {

        baseMapper.deleteById(id);

        return ResultModel.succeed();
    }
}
