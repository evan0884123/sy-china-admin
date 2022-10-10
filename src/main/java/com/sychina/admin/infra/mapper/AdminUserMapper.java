package com.sychina.admin.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sychina.admin.infra.domain.AdminUser;
import com.sychina.admin.web.pojo.models.AdminUserInfoModel;
import com.sychina.admin.web.pojo.models.AdminUserTableModel;
import com.sychina.admin.web.pojo.params.AdminUserProfileQuery;
import com.sychina.admin.web.pojo.params.AdminUserQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Administrator
 */
@Mapper
public interface AdminUserMapper extends BaseMapper<AdminUser> {

    Page<AdminUserTableModel> findTable(IPage page, @Param("query") AdminUserQuery query);

    AdminUserTableModel findByLoginName(@Param("query") AdminUserProfileQuery profileQuery);
}
