package com.sychina.admin.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sychina.admin.infra.domain.Companies;
import com.sychina.admin.infra.domain.Messages;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Administrator
 */
@Mapper
public interface MessagesMapper extends BaseMapper<Messages> {
}
