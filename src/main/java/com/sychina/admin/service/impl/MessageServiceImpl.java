package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.infra.domain.Messages;
import com.sychina.admin.infra.mapper.MessageMapper;
import com.sychina.admin.service.IMessageService;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.MessageParam;
import com.sychina.admin.web.pojo.params.MessageQuery;

/**
 * @author Administrator
 */
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Messages> implements IMessageService {

    public ResultModel add(MessageParam messageParam) {
    }

    public ResultModel loadTable(MessageQuery messageQuery) {
    }

    public ResultModel edit(MessageParam messageParam) {
    }

    public ResultModel delete(Integer id) {
    }
}
