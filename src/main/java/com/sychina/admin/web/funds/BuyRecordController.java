//package com.sychina.admin.web.funds;
//
//import com.sychina.admin.service.impl.BuyRecordServiceImpl;
//import com.sychina.admin.web.pojo.models.response.ResultModel;
//import com.sychina.admin.web.pojo.params.BuyRecordQuery;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @author Administrator
// */
//@RestController
//@RequestMapping("/buyRecord")
//@Api(tags = {"购买管理"})
//public class BuyRecordController {
//
//    private BuyRecordServiceImpl buyRecordService;
//
//    @GetMapping("/loadTable")
//    @ApiOperation("获取所有玩家的购买记录")
//    public ResultModel loadTable(BuyRecordQuery buyRecordQuery) {
//        return buyRecordService.loadTable(buyRecordQuery);
//    }
//
//
//    @Autowired
//    public void setBuyRecordService(BuyRecordServiceImpl buyRecordService) {
//        this.buyRecordService = buyRecordService;
//    }
//}
