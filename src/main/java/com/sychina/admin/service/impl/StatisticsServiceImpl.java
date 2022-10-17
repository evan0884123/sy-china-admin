package com.sychina.admin.service.impl;

import com.sychina.admin.web.pojo.models.HallStageStaModel;
import com.sychina.admin.web.pojo.models.HallTodayStaModel;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.HallStageStaQuery;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
public class StatisticsServiceImpl {

    public ResultModel hallTodaySta() {

        HallTodayStaModel todayStaModel = new HallTodayStaModel();

        return ResultModel.succeed(todayStaModel);
    }

    public ResultModel hallStageSta(HallStageStaQuery query) {

        HallStageStaModel stageStaModel = new HallStageStaModel();

        return ResultModel.succeed(stageStaModel);
    }


}
