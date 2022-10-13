package com.sychina.admin.web.funds;

import com.sychina.admin.service.impl.ChickenServiceImpl;
import com.sychina.admin.service.impl.DebtRecordServiceImpl;
import com.sychina.admin.service.impl.EquitiesServiceImpl;
import com.sychina.admin.service.impl.ProjectRecordServiceImpl;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.ChickenQuery;
import com.sychina.admin.web.pojo.params.DebtRecordQuery;
import com.sychina.admin.web.pojo.params.EquitiesQuery;
import com.sychina.admin.web.pojo.params.ProjectRecordQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/record")
@Api(tags = {"购买管理"})
public class RecordController {

    private ProjectRecordServiceImpl projectRecordService;

    private DebtRecordServiceImpl debtRecordService;

    private EquitiesServiceImpl equitiesService;

    private ChickenServiceImpl chickenService;

    @PostMapping("/projectRecord")
    @ApiOperation("获取项目购买信息")
    public ResultModel getProjectRecord(@PathVariable ProjectRecordQuery recordQuery) {
        return projectRecordService.loadTable(recordQuery);
    }

    @PostMapping("/debtRecord")
    @ApiOperation("获取项目购买信息")
    public ResultModel getDebtRecord(@PathVariable DebtRecordQuery recordQuery) {
        return debtRecordService.loadTable(recordQuery);
    }

    @PostMapping("/equities")
    @ApiOperation("获取股权购买信息")
    public ResultModel getEquities(@PathVariable EquitiesQuery recordQuery) {
        return equitiesService.loadTable(recordQuery);
    }

    @PostMapping("/chicken")
    @ApiOperation("获取农场鸡信息")
    public ResultModel getChicken(@PathVariable ChickenQuery recordQuery) {
        return chickenService.loadTable(recordQuery);
    }


    @Autowired
    public void setProjectRecordService(ProjectRecordServiceImpl projectRecordService) {
        this.projectRecordService = projectRecordService;
    }

    @Autowired
    public void setDebtRecordService(DebtRecordServiceImpl debtRecordService) {
        this.debtRecordService = debtRecordService;
    }

    @Autowired
    public void setEquitiesService(EquitiesServiceImpl equitiesService) {
        this.equitiesService = equitiesService;
    }

    @Autowired
    public void setChickenService(ChickenServiceImpl chickenService) {
        this.chickenService = chickenService;
    }
}
