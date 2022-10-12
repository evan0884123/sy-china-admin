package com.sychina.admin.web.system;

import com.sychina.admin.service.impl.AdminRoleServiceImpl;
import com.sychina.admin.web.pojo.models.AdminRoleTable;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.AdminRoleParam;
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
@RequestMapping("/adminRole")
@Api(tags = {"角色管理"})
public class AdminRoleController {

    private AdminRoleServiceImpl adminRoleServiceImpl;

    @PostMapping("/addRole")
    @ApiOperation("新增角色")
    public ResultModel addRole(@Validated AdminRoleParam adminRoleParam) {
        return adminRoleServiceImpl.addRole(adminRoleParam);
    }

    @GetMapping("/loadRoleTable")
    @ApiOperation("获取所有角色")
    public ResultModel<List<AdminRoleTable>> loadRoleTable() {
        return adminRoleServiceImpl.loadRoleTable();
    }

    @PostMapping("/editRole")
    @ApiOperation("编辑角色")
    public ResultModel editRole(@Validated AdminRoleParam adminRoleParam) {
        return adminRoleServiceImpl.editRole(adminRoleParam);
    }

    @DeleteMapping("/deleteRole/{id}")
    @ApiOperation("删除角色")
    public ResultModel deleteRole(@PathVariable Integer id) {
        return adminRoleServiceImpl.deleteRole(id);
    }

    @Autowired
    public void setRoleService(AdminRoleServiceImpl adminRoleServiceImpl) {
        this.adminRoleServiceImpl = adminRoleServiceImpl;
    }
}
