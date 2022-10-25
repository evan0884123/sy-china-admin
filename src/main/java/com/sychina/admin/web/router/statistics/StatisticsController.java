package com.sychina.admin.web.router.statistics;

import com.sychina.admin.aop.Access;
import com.sychina.admin.service.impl.StatisticsServiceImpl;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.HallStageStaQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/sta")
@Api(tags = {"统计"})
public class StatisticsController {

    private StatisticsServiceImpl statisticsService;

    @PostMapping("/hallTodaySta")
    @ApiOperation("首页今日统计")
    @Access
    public ResultModel hallTodaySta() {
        return statisticsService.hallTodaySta();
    }

    @PostMapping("/hallStageSta")
    @ApiOperation("首页周/月统计")
    @Access
    public ResultModel hallStageSta(@Validated HallStageStaQuery query) {
        return statisticsService.hallStageSta(query);
    }

    @Autowired
    public void setStatisticsService(StatisticsServiceImpl statisticsService) {
        this.statisticsService = statisticsService;
    }
}
