package com.sychina.admin.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sychina.admin.infra.domain.BankInfos;
import com.sychina.admin.infra.domain.Banners;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Administrator
 */
@Mapper
public interface BannersMapper extends BaseMapper<Banners> {
}
