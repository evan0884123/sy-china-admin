package com.sychina.admin.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sychina.admin.infra.domain.BankInfos;
import com.sychina.admin.web.pojo.models.BankTableModel;
import com.sychina.admin.web.pojo.params.BankQuery;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Administrator
 */
@Mapper
public interface BankInfoMapper extends BaseMapper<BankInfos> {

    Page<BankTableModel> loadTable(IPage page, BankQuery bankQuery);

}
