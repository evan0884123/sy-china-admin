package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.infra.domain.News;
import com.sychina.admin.infra.mapper.NewsMapper;
import com.sychina.admin.service.INewsService;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.NewsParam;
import com.sychina.admin.web.pojo.params.NewsQuery;

/**
 * @author Administrator
 */
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements INewsService {

    public ResultModel add(NewsParam newsParam) {
    }

    public ResultModel loadTable(NewsQuery newsQuery) {
    }

    public ResultModel edit(NewsParam newsParam) {
    }

    public ResultModel delete(Integer id) {
    }
}
