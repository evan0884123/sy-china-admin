package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.infra.domain.Messages;
import com.sychina.admin.infra.domain.Players;
import com.sychina.admin.infra.mapper.MessageMapper;
import com.sychina.admin.service.IMessageService;
import com.sychina.admin.utils.LocalDateTimeHelper;
import com.sychina.admin.web.pojo.models.MessageTable;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.MessageAddParam;
import com.sychina.admin.web.pojo.params.MessageQuery;
import com.sychina.admin.web.pojo.params.MessageUpdateParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Messages> implements IMessageService {

    private PlayerServiceImpl playerService;

    public ResultModel add(MessageAddParam messageParam) {

        List<Players> players;
        if (StringUtils.isBlank(messageParam.getPlayer())) {
            players = playerService.list();
        } else {
            QueryWrapper<Players> wrapper = new QueryWrapper<Players>().in("account", messageParam.getPlayer().split(","));
            players = playerService.list(wrapper);
        }

        List<Messages> messagesList = new ArrayList<>();
        players.forEach(player -> {
            Messages message = messageParam.convert()
                    .setPlayer(player.getId())
                    .setPlayerName(player.getAccount());

            messagesList.add(message);
        });

        saveBatch(messagesList);

        return ResultModel.succeed();
    }

    public ResultModel loadTable(MessageQuery messageQuery) {

        QueryWrapper<Messages> wrapper = new QueryWrapper<>();
        wrapper.likeRight(StringUtils.isNotBlank(messageQuery.getPlayerName()), "player_name", messageQuery.getPlayerName());
        wrapper.likeRight(StringUtils.isNotBlank(messageQuery.getTitle()), "title", messageQuery.getTitle());
        wrapper.eq(messageQuery.getHadRead() != null, "had_read", messageQuery.getHadRead());
        wrapper.between(messageQuery.getTimeType() == 0 && messageQuery.getStartTime() != null && messageQuery.getEndTime() != null,
                "`create`", messageQuery.getStartTime(), messageQuery.getEndTime());
        wrapper.between(messageQuery.getTimeType() == 1 && messageQuery.getStartTime() != null && messageQuery.getEndTime() != null,
                "`update`", messageQuery.getStartTime(), messageQuery.getEndTime());
        wrapper.orderByDesc("`create`");

        IPage page = baseMapper.selectPage(messageQuery.page(), wrapper);

        List<MessageTable> tables = new ArrayList<>();
        List<Messages> records = page.getRecords();
        records.forEach(message -> {
            tables.add(new MessageTable(message));
        });
        page.setRecords(tables);

        return ResultModel.succeed(page);
    }

    public ResultModel edit(MessageUpdateParam messageParam) {

        Assert.notNull(messageParam.getId(), "id不能为空");

        Messages messages = messageParam.convert()
                .setId(messageParam.getId())
                .setUpdate(LocalDateTimeHelper.toLong(LocalDateTime.now()));

        baseMapper.updateById(messages);

        return ResultModel.succeed();
    }

    public ResultModel delete(Long id) {

        baseMapper.deleteById(id);

        return ResultModel.succeed();
    }

    @Autowired
    public void setPlayerService(PlayerServiceImpl playerService) {
        this.playerService = playerService;
    }
}
