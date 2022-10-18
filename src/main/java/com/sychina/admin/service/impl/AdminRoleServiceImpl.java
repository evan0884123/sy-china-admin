package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.infra.domain.AdminMenu;
import com.sychina.admin.infra.domain.AdminRole;
import com.sychina.admin.infra.mapper.AdminMenuMapper;
import com.sychina.admin.infra.mapper.AdminRoleMapper;
import com.sychina.admin.web.pojo.SelectOption;
import com.sychina.admin.web.pojo.models.AdminRoleTable;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.AdminRoleParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Administrator
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRole> implements IService<AdminRole> {

    private AdminMenuMapper adminMenuMapper;

    /**
     * @param adminRoleParam
     * @return
     */
    public ResultModel addRole(AdminRoleParam adminRoleParam) {

        AdminRole adminRole = baseMapper.selectOne(new QueryWrapper<AdminRole>().eq("name", adminRoleParam.getName()));
        Assert.isNull(adminRole, "该角色已存在");

        adminRole = new AdminRole();
        adminRole.setName(adminRoleParam.getName());
        adminRole.setMenus(adminRoleParam.getMenuIds());

        baseMapper.insert(adminRole);

        return ResultModel.succeed();
    }

    /**
     * @return
     */
    public ResultModel<List<AdminRoleTable>> loadRoleTable() {

        List<AdminRole> adminRoles = baseMapper.selectList(new QueryWrapper<>());

        List<AdminRoleTable> adminRoleTables = new ArrayList<>();
        adminRoles.forEach(adminRole -> {
            AdminRoleTable adminRoleTable = new AdminRoleTable();
            adminRoleTable.setId(adminRole.getId());
            adminRoleTable.setName(adminRole.getName());

            String[] menuId = adminRole.getMenus().split(",");
            List<AdminMenu> adminMenuList = adminMenuMapper.selectBatchIds(Arrays.asList(menuId));
            adminRoleTable.setAdminMenus(adminMenuList);

            adminRoleTables.add(adminRoleTable);
        });

        return ResultModel.succeed(adminRoleTables);
    }

    /**
     * @param adminRoleParam
     * @return
     */
    public ResultModel editRole(AdminRoleParam adminRoleParam) {

        AdminRole adminRole = baseMapper.selectById(adminRoleParam.getId());
        Assert.notNull(adminRole, "未找到该角色");

        adminRole.setName(adminRoleParam.getName());
        adminRole.setMenus(adminRoleParam.getMenuIds());

        baseMapper.updateById(adminRole);

        return ResultModel.succeed();
    }

    /**
     * 删除角色
     *
     * @param id roleid
     * @return 是否成
     */
    public ResultModel deleteRole(Integer id) {

        baseMapper.deleteById(id);

        return ResultModel.succeed();
    }


    public ResultModel<List<SelectOption>> fetchRoleOption() {

        List<AdminRole> adminRoles = baseMapper.selectList(new QueryWrapper<>());
        List<SelectOption> roleSelect = new ArrayList<>();

        adminRoles.forEach(adminRole -> {
            SelectOption selectOption = new SelectOption();
            selectOption.setLabel(adminRole.getName());
            selectOption.setValue(adminRole.getId() + "");
            roleSelect.add(selectOption);
        });

        return ResultModel.succeed(roleSelect);
    }

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

    @Autowired
    public void setMenuMapper(AdminMenuMapper adminMenuMapper) {
        this.adminMenuMapper = adminMenuMapper;
    }
}
