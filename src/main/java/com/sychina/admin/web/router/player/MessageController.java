package com.sychina.admin.web.router.player;

import com.sychina.admin.aop.Access;
import com.sychina.admin.service.impl.MessageServiceImpl;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.MessageAddParam;
import com.sychina.admin.web.pojo.params.MessageQuery;
import com.sychina.admin.web.pojo.params.MessageUpdateParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/message")
@Api(tags = {"通知消息管理"})
public class MessageController {

    private MessageServiceImpl messageService;

    @PostMapping("/add")
    @ApiOperation("新增通知消息")
    @Access(recordLog = true)
    public ResultModel add(@Validated MessageAddParam messageParam) {
        return messageService.add(messageParam);
    }

    @PostMapping("/loadTable")
    @ApiOperation("获取所有通知消息")
    @Access
    public ResultModel loadTable(@Validated MessageQuery messageQuery) {
        return messageService.loadTable(messageQuery);
    }

    @PostMapping("/edit")
    @ApiOperation("编辑通知消息")
    @Access(recordLog = true)
    public ResultModel edit(@Validated MessageUpdateParam messageParam) {
        return messageService.edit(messageParam);
    }

    @PostMapping("/delete")
    @ApiOperation("删除通知消息")
    @Access(recordLog = true)
    public ResultModel delete(@RequestParam Long id) {
        return messageService.delete(id);
    }

    @Autowired
    public void setMessageService(MessageServiceImpl messageService) {
        this.messageService = messageService;
    }
}
