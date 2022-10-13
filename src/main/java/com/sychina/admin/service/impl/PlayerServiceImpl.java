package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.infra.domain.Players;
import com.sychina.admin.infra.mapper.PlayerMapper;
import com.sychina.admin.service.IPlayerService;
import com.sychina.admin.utils.LocalDateTimeHelper;
import com.sychina.admin.web.pojo.models.PlayerTable;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.PlayerParam;
import com.sychina.admin.web.pojo.params.PlayerQuery;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class PlayerServiceImpl extends ServiceImpl<PlayerMapper, Players> implements IPlayerService {

    public ResultModel loadTable(PlayerQuery playerQuery) {

        QueryWrapper<Players> wrapper = new QueryWrapper<>();
        wrapper.between(playerQuery.getTimeType() == 0, "`create`", playerQuery.getStartTime(), playerQuery.getEndTime());
        wrapper.between(playerQuery.getTimeType() == 1, "`update`", playerQuery.getStartTime(), playerQuery.getEndTime());

        IPage page = baseMapper.selectPage(playerQuery.page(), wrapper);

        List<PlayerTable> tables = new ArrayList<>();
        List<Players> records = page.getRecords();
        records.forEach(record -> {
            tables.add(new PlayerTable(record));
        });
        page.setRecords(tables);

        return ResultModel.succeed(page);
    }

    public ResultModel edit(PlayerParam playerParam) {

        Players players = playerParam.convert()
                .setId(playerParam.getId())
                .setUpdate(LocalDateTimeHelper.toLong(LocalDateTime.now()));

        baseMapper.updateById(players);

        return ResultModel.succeed();
    }

    public ResultModel delete(Long id) {

        baseMapper.deleteById(id);

        return ResultModel.succeed();
    }
}
