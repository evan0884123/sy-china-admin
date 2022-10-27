package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.infra.domain.Chickens;
import com.sychina.admin.infra.mapper.ChickenMapper;
import com.sychina.admin.service.IChickenService;
import com.sychina.admin.web.pojo.models.ChickenTable;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.ChickenQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class ChickenServiceImpl extends ServiceImpl<ChickenMapper, Chickens> implements IChickenService {

    public ResultModel loadTable(ChickenQuery recordQuery) {

        QueryWrapper<Chickens> wrapper = new QueryWrapper<>();
        wrapper.likeRight(StringUtils.isNotBlank(recordQuery.getPlayerName()), "player_name", recordQuery.getPlayerName());
        wrapper.eq(recordQuery.getType() != null, "type", recordQuery.getType());
        wrapper.eq(recordQuery.getEgg() != null, "egg", recordQuery.getEgg());
        wrapper.between(recordQuery.getTimeType() == 0, "`create`", recordQuery.getStartTime(), recordQuery.getEndTime());
        wrapper.between(recordQuery.getTimeType() == 1, "`update`", recordQuery.getStartTime(), recordQuery.getEndTime());
        wrapper.orderByDesc("`create`");

        IPage page = baseMapper.selectPage(recordQuery.page(), wrapper);

        List<ChickenTable> tables = new ArrayList<>();
        List<Chickens> records = page.getRecords();
        records.forEach(record -> {
            tables.add(new ChickenTable(record));
        });
        page.setRecords(tables);

        return ResultModel.succeed(page);
    }

}
