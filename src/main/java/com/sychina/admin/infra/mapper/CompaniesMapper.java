package com.sychina.admin.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sychina.admin.infra.domain.BuyRecords;
import com.sychina.admin.infra.domain.Companies;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Administrator
 */
@Mapper
public interface CompaniesMapper extends BaseMapper<Companies> {
}
