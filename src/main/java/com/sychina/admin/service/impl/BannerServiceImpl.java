package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.infra.domain.Banners;
import com.sychina.admin.infra.mapper.BannerMapper;
import com.sychina.admin.service.IBannerService;
import com.sychina.admin.utils.LocalDateTimeHelper;
import com.sychina.admin.web.pojo.models.BannerTable;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.BannerParam;
import com.sychina.admin.web.pojo.params.BannerQuery;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banners> implements IBannerService {

    public ResultModel add(BannerParam bannerParam) {

        Banners banner = bannerParam.convert()
                .setCreate(LocalDateTimeHelper.toLong(LocalDateTime.now()));
        baseMapper.insert(banner);

        return ResultModel.succeed();
    }

    public ResultModel loadTable(BannerQuery bannerQuery) {

        QueryWrapper<Banners> wrapper = new QueryWrapper<>();
        wrapper.eq(bannerQuery.getTab() != null, "tab", bannerQuery.getTab());
        wrapper.between(bannerQuery.getTimeType() == 0, "create", bannerQuery.getStartTime(), bannerQuery.getEndTime());
        wrapper.between(bannerQuery.getTimeType() == 1, "update", bannerQuery.getStartTime(), bannerQuery.getEndTime());

        IPage page = baseMapper.selectMapsPage(bannerQuery.page(), wrapper);

        List<BannerTable> tables = new ArrayList<>();
        List<Banners> records = page.getRecords();
        records.forEach(banner -> {
            tables.add(new BannerTable().convert(banner));
        });
        page.setRecords(tables);

        return ResultModel.succeed(page);
    }

    public ResultModel edit(BannerParam bannerParam) {

        Banners banner = bannerParam.convert()
                .setUpdate(LocalDateTimeHelper.toLong(LocalDateTime.now()))
                .setId(bannerParam.getId());

        baseMapper.updateById(banner);

        return ResultModel.succeed();
    }

    public ResultModel delete(Integer id) {

        baseMapper.deleteById(id);

        return ResultModel.succeed();
    }
}
