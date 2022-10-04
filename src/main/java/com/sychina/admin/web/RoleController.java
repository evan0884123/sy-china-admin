package com.sychina.admin.web;

import com.sychina.admin.service.impl.RoleServiceImpl;
import com.sychina.admin.web.pojo.models.RoleTableModel;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.RoleParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("/role")
@Api(tags = {"角色管理"})
public class RoleController {

    private RoleServiceImpl roleServiceImpl;

    @PostMapping("/addRole")
    @ApiOperation("新增角色")
    public ResultModel addRole(@Validated RoleParam roleParam) {
        return roleServiceImpl.addRole(roleParam);
    }

    @PostMapping("/editRole")
    @ApiOperation("编辑角色")
    public ResultModel editRole(@Validated RoleParam roleParam) {
        return roleServiceImpl.editRole(roleParam);
    }

    @GetMapping("/loadRoleTable")
    @ApiOperation("获取所有角色")
    public ResultModel<List<RoleTableModel>> loadRoleTable() {
        return roleServiceImpl.loadRoleTable();
    }

    @DeleteMapping("/deleteRole/{id}")
    @ApiOperation("删除角色")
    public ResultModel deleteRole(@PathVariable Integer id) {
        return roleServiceImpl.deleteRole(id);
    }

    @Autowired
    public void setRoleService(RoleServiceImpl roleServiceImpl) {
        this.roleServiceImpl = roleServiceImpl;
    }
}
