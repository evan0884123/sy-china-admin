package com.sychina.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.cache.CompanyCache;
import com.sychina.admin.cache.PlayerCache;
import com.sychina.admin.common.RedisKeys;
import com.sychina.admin.infra.domain.*;
import com.sychina.admin.infra.mapper.WithdrawApplyMapper;
import com.sychina.admin.service.IWithdrawApplyService;
import com.sychina.admin.utils.LocalDateTimeHelper;
import com.sychina.admin.utils.RedisLockUtil;
import com.sychina.admin.web.pojo.models.WithdrawApplyTable;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.WithdrawApplyParam;
import com.sychina.admin.web.pojo.params.WithdrawApplyQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class WithdrawApplyServiceImpl extends ServiceImpl<WithdrawApplyMapper, WithdrawApply> implements IWithdrawApplyService {

    private OperationAmountServiceImpl operationAmountService;

    private RedisLockUtil lockUtil;

    public ResultModel loadTable(WithdrawApplyQuery recordQuery) {

        QueryWrapper<WithdrawApply> wrapper = new QueryWrapper<>();
        wrapper.likeRight(StringUtils.isNotBlank(recordQuery.getPlayerName()), "player_name", recordQuery.getPlayerName());
        wrapper.eq(recordQuery.getType() != null, "type", recordQuery.getType());
        wrapper.eq(recordQuery.getChargeChannel() != null, "charge_channel", recordQuery.getChargeChannel());
        wrapper.eq(recordQuery.getWdType() != null, "wd_type", recordQuery.getWdType());
        wrapper.eq(recordQuery.getStatus() != null, "status", recordQuery.getStatus());
        wrapper.between(recordQuery.getTimeType() == 0 && recordQuery.getStartTime() != null && recordQuery.getEndTime() != null,
                "`create`", recordQuery.getStartTime(), recordQuery.getEndTime());
        wrapper.between(recordQuery.getTimeType() == 1 && recordQuery.getStartTime() != null && recordQuery.getEndTime() != null,
                "`update`", recordQuery.getStartTime(), recordQuery.getEndTime());
        wrapper.orderByAsc("`status`").orderByDesc("`create`");

        IPage page = baseMapper.selectPage(recordQuery.page(), wrapper);

        List<WithdrawApplyTable> tables = new ArrayList<>();
        List<WithdrawApply> records = page.getRecords();
        records.forEach(record -> {
            tables.add(new WithdrawApplyTable(record));
        });
        page.setRecords(tables);

        return ResultModel.succeed(page);
    }

    public ResultModel edit(WithdrawApplyParam withdrawApplyParam) {

        List<WithdrawApply> withdrawApplies = baseMapper.selectBatchIds(withdrawApplyParam.getIds());
        withdrawApplies.forEach(withdrawApply -> {

            if (Arrays.asList(1, 2).contains(withdrawApply.getStatus())) {
                return;
            }
            String lockKey = RedisKeys.playBalanceChange + withdrawApply.getPlayer();
            boolean tryLock = lockUtil.tryLock(lockKey, 15);
            if (tryLock){
                try {
                    operationAmountService.actionAmount(withdrawApply, withdrawApplyParam);
                } finally {
                    lockUtil.unlock(lockKey);
                }
            }
        });

        return ResultModel.succeed("执行中，请再次确认是否成功");
    }

    public ResultModel applicationNotice() {

        QueryWrapper<WithdrawApply> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 0);

        Long count = baseMapper.selectCount(wrapper);

        return ResultModel.succeed(count);
    }



    @Autowired
    public void setLockUtil(RedisLockUtil lockUtil) {
        this.lockUtil = lockUtil;
    }

    @Autowired
    public void setOperationAmountService(OperationAmountServiceImpl operationAmountService) {
        this.operationAmountService = operationAmountService;
    }
}
