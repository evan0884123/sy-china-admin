package com.sychina.admin.cache;

import com.sychina.admin.infra.domain.AdminMenu;
import com.sychina.admin.infra.domain.AdminRole;
import com.sychina.admin.infra.domain.AdminUser;
import com.sychina.admin.infra.mapper.AdminMenuMapper;
import com.sychina.admin.infra.mapper.AdminUserMapper;
import com.sychina.admin.service.impl.AdminRoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

/**
 * @author Administrator
 */
@Component
@CacheConfig(cacheNames = "admin")
public class AdminUserCache {

    private AdminUserMapper adminUserMapper;

    private AdminRoleServiceImpl adminRoleService;

    private AdminMenuMapper adminMenuMapper;

    @Cacheable(key = "'adminUser' + #userId")
    public AdminUser cacheAdminUser(String userId) {
        AdminUser adminUser = adminUserMapper.selectById(userId);

        return adminUser;
    }

    @Cacheable(key = "'adminUser_menu' + #adminUser.id")
    public List<AdminMenu> cachePrivilege(AdminUser adminUser) {

        AdminRole adminRole = adminRoleService.getById(adminUser.getRoleId());
        Assert.notNull(adminRole, "未找到该用户的关联角色");

        String[] menuId = adminRole.getMenus().split(",");
        List<AdminMenu> adminMenuList = adminMenuMapper.selectBatchIds(Arrays.asList(menuId));

        return adminMenuList;
    }

    @Autowired
    public void setAdminRoleService(AdminRoleServiceImpl adminRoleService) {
        this.adminRoleService = adminRoleService;
    }

    @Autowired
    public void setAdminMenuMapper(AdminMenuMapper adminMenuMapper) {
        this.adminMenuMapper = adminMenuMapper;
    }

    @Autowired
    public void setAdminUserMapper(AdminUserMapper adminUserMapper) {
        this.adminUserMapper = adminUserMapper;
    }
}
