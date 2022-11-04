package com.sychina.admin.web.router.news;

import com.sychina.admin.aop.Access;
import com.sychina.admin.service.impl.PublicServiceImpl;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/public")
@Api(tags = {"公告管理"})
public class PublicController {

    private PublicServiceImpl publicService;

    @PostMapping("/add")
    @ApiOperation("新增公告")
    @Access(recordLog = true)
    public ResultModel add(@RequestParam String content) {
        return publicService.add(content);
    }

    @PostMapping("/loadTable")
    @ApiOperation("获取公告")
    @Access
    public ResultModel loadTable() {
        return publicService.loadTable();
    }

    @PostMapping("/delete")
    @ApiOperation("删除公告")
    @Access(recordLog = true)
    public ResultModel delete(@RequestParam Long id) {
        return publicService.delete(id);
    }

    @Autowired
    public void setPublicService(PublicServiceImpl publicService) {
        this.publicService = publicService;
    }
}
