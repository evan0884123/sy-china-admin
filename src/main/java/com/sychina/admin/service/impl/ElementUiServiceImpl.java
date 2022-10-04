package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sychina.admin.infra.domain.Menu;
import com.sychina.admin.infra.domain.Role;
import com.sychina.admin.infra.domain.User;
import com.sychina.admin.infra.mapper.MenuMapper;
import com.sychina.admin.infra.mapper.RoleMapper;
import com.sychina.admin.infra.mapper.UserMapper;
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

    private MenuMapper menuMapper;

    private RoleMapper roleMapper;

    private UserMapper userMapper;

    private RoleServiceImpl roleServiceImpl;

    /**
     * @return
     */
    public ResultModel<List<SelectOption>> loadMenuList() {

        List<Menu> menus = menuMapper.selectList(new QueryWrapper<>());
        List<SelectOption> menuSelect = new ArrayList<>();

        menus.forEach(menu -> {
            SelectOption selectOption = new SelectOption();
            selectOption.setLabel(menu.getName());
            selectOption.setValue(menu.getId() + "");
            menuSelect.add(selectOption);
        });

        return ResultModel.succeed(menuSelect);
    }

    /**
     * @return
     */
    public ResultModel<List<SelectOption>> fetchRoleOption() {

        List<Role> roles = roleMapper.selectList(new QueryWrapper<>());
        List<SelectOption> roleSelect = new ArrayList<>();

        roles.forEach(role -> {
            SelectOption selectOption = new SelectOption();
            selectOption.setLabel(role.getName());
            selectOption.setValue(role.getId() + "");
            roleSelect.add(selectOption);
        });

        return ResultModel.succeed(roleSelect);
    }

    /**
     * @return
     */
    public ResultModel<List<SelectOption>> fetchUserOptions() {

        List<User> users = userMapper.selectList(new QueryWrapper<>());
        List<SelectOption> userSelect = new ArrayList<>();

        users.forEach(user -> {
            SelectOption selectOption = new SelectOption();
            selectOption.setLabel(user.getFullName());
            selectOption.setValue(user.getId());
            userSelect.add(selectOption);
        });

        return ResultModel.succeed(userSelect);
    }

    @Autowired
    public void setMenuMapper(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    @Autowired
    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setRoleService(RoleServiceImpl roleServiceImpl) {
        this.roleServiceImpl = roleServiceImpl;
    }
}
