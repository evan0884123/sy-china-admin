package com.sychina.admin.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sychina.admin.infra.domain.WithdrawApply;
import com.sychina.admin.web.pojo.models.HallStageStaModel;
import com.sychina.admin.web.pojo.models.HallTodayStaModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Mapper
public interface WithdrawApplyMapper extends BaseMapper<WithdrawApply> {

    @Select("SELECT IFNULL(COUNT(player_name),0) tWithdrawUsersNum, " +
            "IFNULL(SUM(times),0) tWithdrawCount, " +
            "IFNULL(SUM(totalAmount),0) tTotalWithdraw " +
            "FROM ( " +
            "SELECT player_name, COUNT(id) times, SUM(amount) totalAmount " +
            "FROM charge_withdraw_reply " +
            "WHERE type = 1 " +
            "AND `create` BETWEEN unix_timestamp(TIMESTAMP(CURDATE())) * 1000 " +
            "AND unix_timestamp(TIMESTAMPADD(MICROSECOND, -1, DATE_ADD(CURDATE(), INTERVAL 1 DAY))) * 1000  GROUP BY player_name ) a;")
    HallTodayStaModel staTodayWithdraw();

    @Select("SELECT `status`, IFNULL(SUM(amount),0) totalAmount " +
            "FROM charge_withdraw_reply " +
            "WHERE type = 1 " +
            "AND `create` BETWEEN unix_timestamp(TIMESTAMP(CURDATE())) * 1000 " +
            "AND unix_timestamp(TIMESTAMPADD(MICROSECOND, -1, DATE_ADD(CURDATE(), INTERVAL 1 DAY))) * 1000  GROUP BY `status`;")
    List<Map<String, Object>> staTodayFinishWithdraw();

    @Select("SELECT IFNULL(COUNT(player_name),0) totalWithdrawalUserNum, " +
            "IFNULL(SUM(times),0) totalWithdrawalCount, " +
            "IFNULL(SUM(totalAmount),0) totalWithdrawalAmount " +
            "FROM ( " +
            "SELECT player_name, COUNT(id) times, SUM(amount) totalAmount " +
            "FROM charge_withdraw_reply " +
            "WHERE type = 1 " +
            "AND `create` BETWEEN ${startTime} AND ${endTime} GROUP BY player_name ) a;")
    HallStageStaModel staTotalWithdraw(@Param("startTime") Long startTime, @Param("endTime") Long endTime);
}
