package com.sychina.admin.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sychina.admin.infra.domain.News;
import com.sychina.admin.infra.domain.Payouts;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Administrator
 */
@Mapper
public interface PayoutMapper extends BaseMapper<Payouts> {
}
