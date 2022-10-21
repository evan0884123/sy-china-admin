package com.sychina.admin.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sychina.admin.infra.domain.Companies;
import com.sychina.admin.infra.domain.Players;
import com.sychina.admin.web.pojo.models.HallStageStaModel;
import com.sychina.admin.web.pojo.models.HallTodayStaModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Administrator
 */
@Mapper
public interface PlayerMapper extends BaseMapper<Players> {

    @Select("SELECT IFNULL(COUNT(id),0) " +
            "FROM players " +
            "WHERE  `create` BETWEEN unix_timestamp(TIMESTAMP(CURDATE())) " +
            "AND unix_timestamp(TIMESTAMPADD(MICROSECOND, -1, DATE_ADD(CURDATE(), INTERVAL 1 DAY)));")
    Integer staTodayRegist();

    @Select("SELECT IFNULL(COUNT(id),0) " +
            "FROM players " +
            "WHERE  `last_login_time` BETWEEN unix_timestamp(TIMESTAMP(CURDATE())) * 1000 " +
            "AND unix_timestamp(TIMESTAMPADD(MICROSECOND, -1, DATE_ADD(CURDATE(), INTERVAL 1 DAY))) * 1000 ;")
    Integer staTodayLogin();

    @Select("SELECT IFNULL(COUNT(id),0) " +
            "FROM players " +
            "WHERE  `create` BETWEEN ${startTime}  AND  ${endTime};")
    Integer staTotalRegist(@Param("startTime") Long startTime, @Param("endTime") Long endTime);

    @Select("SELECT IFNULL(COUNT(id),0) " +
            "FROM players " +
            "WHERE  `last_login_time` BETWEEN ${startTime}  AND  ${endTime};")
    Integer staTotalLogin(@Param("startTime") Long startTime, @Param("endTime") Long endTime);
}
