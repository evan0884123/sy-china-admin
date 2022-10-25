package com.sychina.admin.web.router.player;

import com.sychina.admin.aop.Access;
import com.sychina.admin.service.impl.PlayerServiceImpl;
import com.sychina.admin.web.pojo.SelectOption;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.PlayerParam;
import com.sychina.admin.web.pojo.params.PlayerQuery;
import com.sychina.admin.web.pojo.params.TopOrLowerScoreParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/player")
@Api(tags = {"玩家管理"})
public class PlayerController {

    private PlayerServiceImpl playerService;

    @PostMapping("/loadTable")
    @ApiOperation("获取所有玩家信息")
    @Access
    public ResultModel loadTable(@Validated PlayerQuery playerQuery) {
        return playerService.loadTable(playerQuery);
    }

    @PostMapping("/edit")
    @ApiOperation("编辑玩家信息")
    @Access(recordLog = true)
    public ResultModel edit(@Validated PlayerParam playerParam) {
        return playerService.edit(playerParam);
    }

    @PostMapping("/delete")
    @ApiOperation("删除玩家信息")
    @Access(recordLog = true)
    public ResultModel delete(@RequestParam Long id) {
        return playerService.delete(id);
    }

    //    @PostMapping("/fetchPlayerOptions")
    @ApiOperation("按照用户名获取信息")
    public ResultModel<List<SelectOption>> fetchPlayerOptions(@RequestParam String account) {
        return playerService.fetchPlayerOptions(account);
    }

    @PostMapping("/topOrLowerScore")
    @ApiOperation("用户上分")
    @Access(recordLog = true)
    public ResultModel topOrLowerScore(@Validated TopOrLowerScoreParam scoreParam) {
        return playerService.topOrLowerScore(scoreParam);
    }

    @PostMapping("/resetPassword")
    @ApiOperation("重置玩密码")
    @Access(recordLog = true)
    public ResultModel resetPassword(@RequestParam String id) {
        return playerService.resetPassword(id);
    }

    @Autowired
    public void setPlayerService(PlayerServiceImpl playerService) {
        this.playerService = playerService;
    }
}
