package com.sychina.admin.web.news;

import com.sychina.admin.service.impl.BannerServiceImpl;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.BannerParam;
import com.sychina.admin.web.pojo.params.BannerQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/banner")
@Api(tags = {"banner管理"})
public class BannerController {

    private BannerServiceImpl bannerService;

    @PostMapping("/add")
    @ApiOperation("新增banner")
    public ResultModel add(@Validated BannerParam bannerParam) {
        return bannerService.add(bannerParam);
    }

    @PostMapping("/loadTable")
    @ApiOperation("获取所有banner")
    public ResultModel loadTable(@Validated BannerQuery bannerQuery) {
        return bannerService.loadTable(bannerQuery);
    }

    @PostMapping("/edit")
    @ApiOperation("编辑banner")
    public ResultModel edit(@Validated BannerParam bannerParam) {
        return bannerService.edit(bannerParam);
    }

    @PostMapping("/delete")
    @ApiOperation("删除banner")
    public ResultModel delete(@RequestParam Long id) {
        return bannerService.delete(id);
    }

    @Autowired
    public void setBannerService(BannerServiceImpl bannerService) {
        this.bannerService = bannerService;
    }
}
