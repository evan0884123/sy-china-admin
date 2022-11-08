package com.sychina.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.common.RedisKeys;
import com.sychina.admin.infra.domain.Public;
import com.sychina.admin.infra.mapper.PublicMapper;
import com.sychina.admin.service.IPublicService;
import com.sychina.admin.utils.LocalDateTimeHelper;
import com.sychina.admin.web.pojo.models.PublicTable;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class PublicServiceImpl extends ServiceImpl<PublicMapper, Public> implements IPublicService {

    private RedisTemplate redisTemplate;

    public ResultModel add(String content) {

        Public aPublic = new Public().setContent(content)
                .setCreate(LocalDateTimeHelper.toLong(LocalDateTime.now()));

        int insert = baseMapper.insert(aPublic);
        redisTemplate.opsForValue().set(RedisKeys.aPublic, aPublic.getContent());

        return ResultModel.succeed();
    }

    public ResultModel loadTable() {


        List<PublicTable> tables = new ArrayList<>();
        List<Public> publicList = list();
        publicList.forEach(aPublic -> {
            tables.add(new PublicTable(aPublic));
        });

        return ResultModel.succeed(publicList);
    }

    public ResultModel delete(Long id) {

        baseMapper.deleteById(id);
        return ResultModel.succeed();
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
