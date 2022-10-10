package com.sychina.admin.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sychina.admin.infra.domain.BankInfos;
import com.sychina.admin.infra.domain.BuyRecords;
import com.sychina.admin.web.pojo.models.BankTableModel;
import com.sychina.admin.web.pojo.params.BuyRecordQuery;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Administrator
 */
@Mapper
public interface BuyRecordMapper extends BaseMapper<BuyRecords> {

    Page<BankTableModel> loadTable(IPage page, BuyRecordQuery buyRecordQuery);
}
