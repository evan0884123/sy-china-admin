package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.infra.domain.Banners;
import com.sychina.admin.infra.mapper.BannerMapper;
import com.sychina.admin.service.IBannerService;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.BannerParam;
import com.sychina.admin.web.pojo.params.BannerQuery;

/**
 * @author Administrator
 */
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banners> implements IBannerService {

    public ResultModel add(BannerParam bannerParam) {
    }

    public ResultModel loadTable(BannerQuery bannerQuery) {
    }

    public ResultModel edit(BannerParam bannerParam) {
    }

    public ResultModel delete(Integer id) {
    }
}
