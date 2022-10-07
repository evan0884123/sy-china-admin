package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sychina.admin.infra.domain.AdminMenu;
import com.sychina.admin.infra.domain.AdminRole;
import com.sychina.admin.infra.domain.AdminUser;
import com.sychina.admin.infra.mapper.AdminMenuMapper;
import com.sychina.admin.infra.mapper.AdminRoleMapper;
import com.sychina.admin.infra.mapper.AdminUserMapper;
import com.sychina.admin.web.pojo.SelectOption;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
public class ElementUiServiceImpl {

    private AdminMenuMapper adminMenuMapper;

    private AdminRoleMapper adminRoleMapper;

    private AdminUserMapper adminUserMapper;

    private AdminRoleServiceImpl adminRoleServiceImpl;

    /**
     * @return
     */
    public ResultModel<List<SelectOption>> loadMenuList() {

        List<AdminMenu> adminMenus = adminMenuMapper.selectList(new QueryWrapper<>());
        List<SelectOption> menuSelect = new ArrayList<>();

        adminMenus.forEach(adminMenu -> {
            SelectOption selectOption = new SelectOption();
            selectOption.setLabel(adminMenu.getName());
            selectOption.setValue(adminMenu.getId() + "");
            menuSelect.add(selectOption);
        });

        return ResultModel.succeed(menuSelect);
    }

    /**
     * @return
     */
    public ResultModel<List<SelectOption>> fetchRoleOption() {

        List<AdminRole> adminRoles = adminRoleMapper.selectList(new QueryWrapper<>());
        List<SelectOption> roleSelect = new ArrayList<>();

        adminRoles.forEach(adminRole -> {
            SelectOption selectOption = new SelectOption();
            selectOption.setLabel(adminRole.getName());
            selectOption.setValue(adminRole.getId() + "");
            roleSelect.add(selectOption);
        });

        return ResultModel.succeed(roleSelect);
    }

    /**
     * @return
     */
    public ResultModel<List<SelectOption>> fetchUserOptions() {

        List<AdminUser> adminUsers = adminUserMapper.selectList(new QueryWrapper<>());
        List<SelectOption> userSelect = new ArrayList<>();

        adminUsers.forEach(user -> {
            SelectOption selectOption = new SelectOption();
            selectOption.setLabel(user.getFullName());
            selectOption.setValue(user.getId());
            userSelect.add(selectOption);
        });

        return ResultModel.succeed(userSelect);
    }

    @Autowired
    public void setMenuMapper(AdminMenuMapper adminMenuMapper) {
        this.adminMenuMapper = adminMenuMapper;
    }

    @Autowired
    public void setRoleMapper(AdminRoleMapper adminRoleMapper) {
        this.adminRoleMapper = adminRoleMapper;
    }

    @Autowired
    public void setUserMapper(AdminUserMapper adminUserMapper) {
        this.adminUserMapper = adminUserMapper;
    }

    @Autowired
    public void setRoleService(AdminRoleServiceImpl adminRoleServiceImpl) {
        this.adminRoleServiceImpl = adminRoleServiceImpl;
    }
}
