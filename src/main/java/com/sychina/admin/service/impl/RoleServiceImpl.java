package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.common.ResponseConstants;
import com.sychina.admin.infra.domain.Menu;
import com.sychina.admin.infra.domain.Role;
import com.sychina.admin.infra.mapper.MenuMapper;
import com.sychina.admin.infra.mapper.RoleMapper;
import com.sychina.admin.web.model.RoleModel;
import com.sychina.admin.web.model.RoleTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    public String addRole(RoleModel roleModel) {
        Role role = baseMapper.selectOne(new QueryWrapper<Role>().eq("name", roleModel.getName()));
        if (role != null) {
            return ResponseConstants.EXISTS;
        }

        role = new Role();
        role.setName(roleModel.getName());
        role.setMenus(roleModel.getMenus());

        baseMapper.insert(role);

        return ResponseConstants.SUCCESS;
    }

    public List<RoleTableModel> loadRoleTable() {
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

        return roleTableModels;
    }

    public String editRole(RoleModel roleModel) {

        Role role = baseMapper.selectById(roleModel.getId());
        if (role == null) {
            return ResponseConstants.FAILURE;
        }

        role.setName(roleModel.getName());
        role.setMenus(roleModel.getMenus());

        baseMapper.updateById(role);

        return ResponseConstants.SUCCESS;
    }

    /**
     * 删除角色
     *
     * @param id roleid
     * @return 是否成
     */
    public String deleteRole(Integer id) {

        int marker = baseMapper.deleteById(id);
        if (marker == 0) {
            return ResponseConstants.IN_USE;
        }

        return ResponseConstants.SUCCESS;
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
