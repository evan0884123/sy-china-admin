package com.sychina.admin.web.news;

import com.sychina.admin.service.impl.NewsServiceImpl;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.NewsParam;
import com.sychina.admin.web.pojo.params.NewsQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/news")
@Api(tags = {"资讯管理"})
public class NewsController {

    private NewsServiceImpl newsService;

    @PostMapping("/add")
    @ApiOperation("新增资讯")
    public ResultModel add(@Validated NewsParam newsParam) {
        return newsService.add(newsParam);
    }

    @PostMapping("/loadTable")
    @ApiOperation("获取资讯")
    public ResultModel loadTable(@Validated NewsQuery newsQuery) {
        return newsService.loadTable(newsQuery);
    }

    @PostMapping("/edit")
    @ApiOperation("编辑资讯")
    public ResultModel edit(@Validated NewsParam newsParam) {
        return newsService.edit(newsParam);
    }

    @PostMapping("/delete")
    @ApiOperation("删除资讯")
    public ResultModel delete(@RequestParam Long id) {
        return newsService.delete(id);
    }

    @Autowired
    public void setNewsService(NewsServiceImpl newsService) {
        this.newsService = newsService;
    }
}
