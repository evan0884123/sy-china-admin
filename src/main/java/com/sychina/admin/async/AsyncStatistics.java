package com.sychina.admin.async;

import com.sychina.admin.infra.mapper.AccountChangeMapper;
import com.sychina.admin.infra.mapper.PlayerMapper;
import com.sychina.admin.infra.mapper.WithdrawApplyMapper;
import com.sychina.admin.web.pojo.models.HallStageStaModel;
import com.sychina.admin.web.pojo.models.HallTodayStaModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * @author Administrator
 */
@Component
public class AsyncStatistics {

    private AccountChangeMapper accountChangeMapper;

    private WithdrawApplyMapper withdrawApplyMapper;

    private PlayerMapper playerMapper;

    @Async
    public void staTodayFirstRecharge(HallTodayStaModel todayStaModel, CountDownLatch latch) {

        try {
            todayStaModel.setTFirstRecharge(accountChangeMapper.staTodayFirstRecharge());
        } finally {
            latch.countDown();
        }
    }

    @Async
    public void staTodayRecharge(HallTodayStaModel todayStaModel, CountDownLatch latch) {
        try {
            HallTodayStaModel model = accountChangeMapper.staTodayRecharge();

            todayStaModel.setTRechargeTimes(model.getTRechargeTimes())
                    .setTRechargeUserNum(model.getTRechargeUserNum())
                    .setTTotalRecharge(model.getTTotalRecharge());
        } finally {
            latch.countDown();
        }
    }

    @Async
    public void staTodayWithdraw(HallTodayStaModel todayStaModel, CountDownLatch latch) {

        try {
            HallTodayStaModel model = withdrawApplyMapper.staTodayWithdraw();

            todayStaModel.setTWithdrawCount(model.getTWithdrawCount())
                    .setTWithdrawUsersNum(model.getTWithdrawUsersNum())
                    .setTTotalWithdraw(model.getTTotalWithdraw());
        } finally {
            latch.countDown();
        }
    }

    public void staTodayFinishWithdraw(HallTodayStaModel todayStaModel, CountDownLatch latch) {

        try {
            List<Map<String, Object>> mapList = withdrawApplyMapper.staTodayFinishWithdraw();

            todayStaModel.setTReadyWithdraw(BigDecimal.ZERO)
                    .setTCompleteWithdraw(BigDecimal.ZERO);
            if (!CollectionUtils.isEmpty(mapList)) {
                mapList.forEach(map -> {
                    Integer status = Math.toIntExact((Long) map.get("status"));
                    BigDecimal totalAmount = new BigDecimal(map.get("totalAmount").toString());
                    if (status == 0 || status == 1) {
                        BigDecimal add = todayStaModel.getTReadyWithdraw().add(totalAmount);
                        todayStaModel.setTReadyWithdraw(add);
                    } else if (status == 2) {
                        todayStaModel.setTCompleteWithdraw(totalAmount);
                    }
                });
            }
        } finally {
            latch.countDown();
        }
    }

    @Async
    public void staTodayLogin(HallTodayStaModel todayStaModel, CountDownLatch latch) {

        try {
            todayStaModel.setTRegisterUserNum(playerMapper.staTodayRegist())
                    .setLoginUserNum(playerMapper.staTodayLogin());
        } finally {
            latch.countDown();
        }
    }

    //----------------------------------------------------------------------------------------------------------

    @Async
    public void staTotalRecharge(Long startTime, Long endTime, HallStageStaModel stageStaModel, CountDownLatch latch) {

        try {
            HallStageStaModel model = accountChangeMapper.staTotalRecharge(startTime, endTime);

            stageStaModel.setTotalRechargeAmount(model.getTotalRechargeAmount())
                    .setTotalRechargeUserNum(model.getTotalRechargeUserNum());
        } finally {
            latch.countDown();
        }
    }

    @Async
    public void staTotalWithdraw(Long startTime, Long endTime, HallStageStaModel stageStaModel, CountDownLatch latch) {

        try {
            HallStageStaModel model = withdrawApplyMapper.staTotalWithdraw(startTime, endTime);

            stageStaModel.setTotalWithdrawalAmount(model.getTotalWithdrawalAmount())
                    .setTotalWithdrawalCount(model.getTotalWithdrawalCount())
                    .setTotalWithdrawalUserNum(model.getTotalWithdrawalUserNum());
        } finally {
            latch.countDown();
        }
    }

    @Async
    public void staTotalLogin(Long startTime, Long endTime, HallStageStaModel stageStaModel, CountDownLatch latch) {
        try {

            stageStaModel.setTotalRegisterUserNum(playerMapper.staTotalRegist(startTime, endTime))
                    .setTotalLoginUserNum(playerMapper.staTotalLogin(startTime, endTime));
        } finally {
            latch.countDown();
        }
    }

    @Autowired
    public void setAccountChangeMapper(AccountChangeMapper accountChangeMapper) {
        this.accountChangeMapper = accountChangeMapper;
    }

    @Autowired
    public void setWithdrawApplyMapper(WithdrawApplyMapper withdrawApplyMapper) {
        this.withdrawApplyMapper = withdrawApplyMapper;
    }

    @Autowired
    public void setPlayerMapper(PlayerMapper playerMapper) {
        this.playerMapper = playerMapper;
    }
}
