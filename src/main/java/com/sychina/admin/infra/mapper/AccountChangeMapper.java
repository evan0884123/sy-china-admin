package com.sychina.admin.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sychina.admin.infra.domain.AccountChanges;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Administrator
 */
@Mapper
public interface AccountChangeMapper extends BaseMapper<AccountChanges> {
}
