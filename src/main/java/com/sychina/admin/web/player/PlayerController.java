package com.sychina.admin.web.player;

import com.sychina.admin.service.impl.PlayerServiceImpl;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.PlayerParam;
import com.sychina.admin.web.pojo.params.PlayerQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/player")
@Api(tags = {"玩家管理"})
public class PlayerController {

    private PlayerServiceImpl playerService;

    @GetMapping("/loadTable")
    @ApiOperation("获取所有玩家信息")
    public ResultModel loadTable(PlayerQuery playerQuery) {
        return playerService.loadTable(playerQuery);
    }

    @PostMapping("/edit")
    @ApiOperation("编辑玩家信息")
    public ResultModel edit(@Validated PlayerParam playerParam) {
        return playerService.edit(playerParam);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除玩家信息")
    public ResultModel delete(@PathVariable Integer id) {
        return playerService.delete(id);
    }

    @Autowired
    public void setPlayerService(PlayerServiceImpl playerService) {
        this.playerService = playerService;
    }
}
