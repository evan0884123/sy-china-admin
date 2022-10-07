package com.sychina.admin.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sychina.admin.infra.domain.AdminUser;
import com.sychina.admin.web.pojo.models.AdminUserInfoModel;
import com.sychina.admin.web.pojo.models.AdminUserTableModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Administrator
 */
@Mapper
public interface AdminUserMapper extends BaseMapper<AdminUser> {

    List<AdminUserTableModel> findTable(@Param("name") String name, @Param("roleId") Integer roleId,
                                        @Param("limit") Integer limit, @Param("offset") Integer offset);

    Integer count(@Param("name") String name, @Param("unitId") String unitId,
                  @Param("roleId") Integer roleId);

    AdminUserTableModel findByLoginName(String loginName);

    AdminUserInfoModel loadUserInfo(String userId);
}
