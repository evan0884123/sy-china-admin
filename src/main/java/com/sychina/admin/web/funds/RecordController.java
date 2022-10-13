package com.sychina.admin.web.funds;

import com.sychina.admin.service.impl.DebtRecordServiceImpl;
import com.sychina.admin.service.impl.EquitiesServiceImpl;
import com.sychina.admin.service.impl.ProjectRecordServiceImpl;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.DebtRecordQuery;
import com.sychina.admin.web.pojo.params.EquitiesQuery;
import com.sychina.admin.web.pojo.params.ProjectRecordQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/buyRecord")
@Api(tags = {"购买管理"})
public class RecordController {

    private ProjectRecordServiceImpl projectRecordService;

    private DebtRecordServiceImpl debtRecordService;

    private EquitiesServiceImpl equitiesService;

    @GetMapping("/projectRecord")
    @ApiOperation("获取项目购买信息")
    public ResultModel getProjectRecord(@PathVariable ProjectRecordQuery recordQuery) {
        return projectRecordService.loadTable(recordQuery);
    }

    @GetMapping("/debtRecord")
    @ApiOperation("获取项目购买信息")
    public ResultModel getDebtRecord(@PathVariable DebtRecordQuery recordQuery) {
        return debtRecordService.loadTable(recordQuery);
    }

    @GetMapping("/equities")
    @ApiOperation("获取股权购买信息")
    public ResultModel getEquities(@PathVariable EquitiesQuery recordQuery) {
        return equitiesService.loadTable(recordQuery);
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
}
