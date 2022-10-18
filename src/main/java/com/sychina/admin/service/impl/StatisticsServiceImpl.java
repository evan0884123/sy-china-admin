package com.sychina.admin.service.impl;

import com.sychina.admin.async.AsyncStatistics;
import com.sychina.admin.web.pojo.models.HallStageStaModel;
import com.sychina.admin.web.pojo.models.HallTodayStaModel;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.HallStageStaQuery;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 */
@Service
public class StatisticsServiceImpl {

    private AsyncStatistics statistics;

    @SneakyThrows
    public ResultModel hallTodaySta() {

        HallTodayStaModel todayStaModel = new HallTodayStaModel();
        CountDownLatch latch = new CountDownLatch(5);
        //今日首冲人数
        statistics.staTodayFirstRecharge(todayStaModel, latch);
        //今日充值次数、今日充值人数、今日充值总额
        statistics.staTodayRecharge(todayStaModel, latch);
        //今日提现笔数、今日提现人数、今日提现总额
        statistics.staTodayWithdraw(todayStaModel, latch);
        //今日待提现金额、今日已提现金额
        statistics.staTodayFinishWithdraw(todayStaModel, latch);
        //今日注册用户、今日登录人数
        statistics.staTodayLogin(todayStaModel, latch);

        latch.await(15, TimeUnit.SECONDS);
        return ResultModel.succeed(todayStaModel);
    }

    @SneakyThrows
    public ResultModel hallStageSta(HallStageStaQuery query) {

        Long startTime = query.startTime();
        Long endTime = query.endTime();

        HallStageStaModel stageStaModel = new HallStageStaModel();
        CountDownLatch latch = new CountDownLatch(3);
        //总充值金额、总充值人数
        statistics.staTotalRecharge(startTime, endTime, stageStaModel, latch);
        //总提现金额、总提现笔数、总提现人数
        statistics.staTotalWithdraw(startTime, endTime, stageStaModel, latch);
        //总注册人数、累计登录人数
        statistics.staTotalLogin(startTime, endTime, stageStaModel, latch);

        latch.await(15, TimeUnit.SECONDS);
        return ResultModel.succeed(stageStaModel);
    }

    @Autowired
    public void setStatistics(AsyncStatistics statistics) {
        this.statistics = statistics;
    }
}
