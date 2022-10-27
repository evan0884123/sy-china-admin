package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.infra.domain.News;
import com.sychina.admin.infra.mapper.NewsMapper;
import com.sychina.admin.service.INewsService;
import com.sychina.admin.utils.LocalDateTimeHelper;
import com.sychina.admin.web.pojo.models.NewsTable;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.NewsParam;
import com.sychina.admin.web.pojo.params.NewsQuery;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements INewsService {

    public ResultModel add(NewsParam newsParam) {

        News news = newsParam.convert()
                .setCreate(LocalDateTimeHelper.toLong(LocalDateTime.now()));
        baseMapper.insert(news);

        return ResultModel.succeed();
    }

    public ResultModel loadTable(NewsQuery newsQuery) {

        QueryWrapper<News> wrapper = new QueryWrapper<>();
        wrapper.eq(newsQuery.getType() != null, "type", newsQuery.getType());
        wrapper.eq(newsQuery.getTab() != null, "tab", newsQuery.getTab());
        wrapper.between(newsQuery.getTimeType() == 0, "`create`", newsQuery.getStartTime(), newsQuery.getEndTime());
        wrapper.between(newsQuery.getTimeType() == 1, "`update`", newsQuery.getStartTime(), newsQuery.getEndTime());
        wrapper.orderByDesc("`create`");

        IPage page = baseMapper.selectPage(newsQuery.page(), wrapper);

        List<NewsTable> tables = new ArrayList<>();
        List<News> records = page.getRecords();
        records.forEach(news -> {
            tables.add(new NewsTable(news));
        });
        page.setRecords(tables);

        return ResultModel.succeed(page);
    }

    public ResultModel edit(NewsParam newsParam) {

        News news = newsParam.convert()
                .setUpdate(LocalDateTimeHelper.toLong(LocalDateTime.now()))
                .setId(newsParam.getId());

        baseMapper.updateById(news);

        return ResultModel.succeed();
    }

    public ResultModel delete(Long id) {

        baseMapper.deleteById(id);

        return ResultModel.succeed();
    }
}
