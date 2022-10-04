package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.infra.domain.Menu;
import com.sychina.admin.infra.domain.Role;
import com.sychina.admin.infra.mapper.MenuMapper;
import com.sychina.admin.infra.mapper.RoleMapper;
import com.sychina.admin.web.pojo.models.RoleTableModel;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.RoleParam;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IService<Role> {

    private MenuMapper menuMapper;

    private ElementUiServiceImpl elementUiServiceImpl;

    /**
     * @param roleParam
     * @return
     */
    public ResultModel addRole(RoleParam roleParam) {

        Role role = baseMapper.selectOne(new QueryWrapper<Role>().eq("name", roleParam.getName()));
        Assert.isNull(role, "该角色已存在");

        role = new Role();
        role.setName(roleParam.getName());
        role.setMenus(roleParam.getMenuIds());

        baseMapper.insert(role);

        return ResultModel.succeed();
    }

    /**
     * @return
     */
    public ResultModel<List<RoleTableModel>> loadRoleTable() {

        List<Role> roles = baseMapper.selectList(new QueryWrapper<>());

        List<RoleTableModel> roleTableModels = new ArrayList<>();
        roles.forEach(role -> {
            RoleTableModel roleTableModel = new RoleTableModel();
            roleTableModel.setId(role.getId());
            roleTableModel.setName(role.getName());

            String[] menuId = role.getMenus().split(",");
            List<Menu> menuList = menuMapper.selectBatchIds(Arrays.asList(menuId));
            roleTableModel.setMenus(menuList);

            roleTableModels.add(roleTableModel);
        });

        return ResultModel.succeed(roleTableModels);
    }

    /**
     * @param roleParam
     * @return
     */
    public ResultModel editRole(RoleParam roleParam) {

        Role role = baseMapper.selectById(roleParam.getId());
        Assert.notNull(role, "未找到该角色");

        role.setName(roleParam.getName());
        role.setMenus(roleParam.getMenuIds());

        baseMapper.updateById(role);

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


    @Autowired
    public void setMenuMapper(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    @Autowired
    public void setElementUiService(ElementUiServiceImpl elementUiServiceImpl) {
        this.elementUiServiceImpl = elementUiServiceImpl;
    }
}
