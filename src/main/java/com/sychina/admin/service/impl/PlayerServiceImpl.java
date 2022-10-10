package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.infra.domain.Players;
import com.sychina.admin.infra.mapper.PlayerMapper;
import com.sychina.admin.service.IPlayerService;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.PlayerParam;
import com.sychina.admin.web.pojo.params.PlayerQuery;

/**
 * @author Administrator
 */
public class PlayerServiceImpl extends ServiceImpl<PlayerMapper, Players> implements IPlayerService {
    
    
    public ResultModel loadTable(PlayerQuery playerQuery) {
    }

    public ResultModel edit(PlayerParam playerParam) {
    }

    public ResultModel delete(Integer id) {
    }
}
