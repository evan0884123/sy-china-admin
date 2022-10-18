package com.sychina.admin.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sychina.admin.infra.domain.AccountChanges;
import com.sychina.admin.web.pojo.models.HallStageStaModel;
import com.sychina.admin.web.pojo.models.HallTodayStaModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Administrator
 */
@Mapper
public interface AccountChangeMapper extends BaseMapper<AccountChanges> {

    @Select("SELECT IFNULL(COUNT(1),0) FROM ( " +
            "SELECT MIN(`create`) " +
            "FROM account_changes " +
            "WHERE amount_type = 0  " +
            "AND change_type = 0 " +
            "AND `create` BETWEEN unix_timestamp(TIMESTAMP(CURDATE())) " +
            "AND unix_timestamp(TIMESTAMPADD(MICROSECOND, -1, DATE_ADD(CURDATE(), INTERVAL 1 DAY))) GROUP BY player_name ) a")
    Integer staTodayFirstRecharge();

    @Select("SELECT IFNULL(COUNT(player_name),0) tRechargeUserNum, " +
            "IFNULL(SUM(times),0) tRechargeTimes, " +
            "IFNULL(SUM(totalAmount),0) tTotalRecharge " +
            "FROM ( SELECT player_name, COUNT(id) times, SUM(amount) totalAmount " +
            "FROM account_changes " +
            "WHERE amount_type = 0  " +
            "AND change_type = 0 " +
            "AND `create` BETWEEN unix_timestamp(TIMESTAMP(CURDATE())) " +
            "AND unix_timestamp(TIMESTAMPADD(MICROSECOND, -1, DATE_ADD(CURDATE(), INTERVAL 1 DAY))) GROUP BY player_name ) a;")
    HallTodayStaModel staTodayRecharge();

    @Select("SELECT IFNULL(COUNT(player_name),0) totalRechargeUserNum, " +
            "IFNULL(SUM(totalAmount),0) totalRechargeAmount " +
            "FROM ( SELECT player_name, SUM(amount) totalAmount " +
            "FROM account_changes " +
            "WHERE amount_type = 0  " +
            "AND change_type = 0 " +
            "AND `create` BETWEEN ${startTime} AND ${endTime} GROUP BY player_name ) a;")
    HallStageStaModel staTotalRecharge(@Param("startTime") Long startTime, @Param("endTime") Long endTime);
}
