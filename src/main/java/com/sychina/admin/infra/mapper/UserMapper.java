package com.sychina.admin.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sychina.admin.infra.domain.User;
import com.sychina.admin.web.model.UserInfoModel;
import com.sychina.admin.web.model.UserTableModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Administrator
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    List<UserTableModel> findTable(@Param("name") String name, @Param("roleId") Integer roleId,
                                   @Param("limit") Integer limit,
                                   @Param("offset") Integer offset);

    Integer count(@Param("name") String name, @Param("unitId") String unitId,
                  @Param("roleId") Integer roleId);

    UserTableModel findByLoginName(String loginName);

    UserInfoModel loadUserInfo(String userId);
}
