package com.sychina.admin.web.router.system;

import com.sychina.admin.aop.Access;
import com.sychina.admin.service.impl.AdminUserServiceImpl;
import com.sychina.admin.web.pojo.SelectOption;
import com.sychina.admin.web.pojo.models.AdminUserTable;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.AdminUserParam;
import com.sychina.admin.web.pojo.params.AdminUserQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/adminUser")
@Api(tags = {"后台用户管理"})
public class AdminUserController {

    private AdminUserServiceImpl userServiceImpl;

    @PostMapping("/info")
    @ApiOperation("获取个人信息")
    public ResultModel<AdminUserTable> info() {

        return userServiceImpl.buildUserInfo();
    }

    @PostMapping("/updateProfile")
    @ApiOperation("更新个人信息")
    @Access(recordLog = true)
    public ResultModel updateProfile(@Validated AdminUserParam adminUserParam) {
        return userServiceImpl.updateProfile(adminUserParam);
    }

    @PostMapping("/updatePassword")
    @ApiOperation("更新个人密码")
    @Access(recordLog = true)
    public ResultModel updatePassword(@RequestParam String id, String password, String oldPassword) {
        return userServiceImpl.updatePassword(id, password, oldPassword);
    }


    @PostMapping("/addUser")
    @ApiOperation("新增用户")
    @Access(recordLog = true)
    public ResultModel addUser(@Validated AdminUserParam adminUserParam) {
        return userServiceImpl.addUser(adminUserParam);
    }

    @PostMapping("/query")
    @ApiOperation("查询所有用户")
    @Access
    public ResultModel query(@Validated AdminUserQuery userQuery) {
        return userServiceImpl.query(userQuery);
    }

    @PostMapping("/editUser")
    @ApiOperation("编辑用户")
    @Access(recordLog = true)
    public ResultModel editUser(@Validated AdminUserParam adminUserParam) {
        return userServiceImpl.editUser(adminUserParam);
    }

    @PostMapping("/deleteUser")
    @ApiOperation("删除用户")
    @Access(recordLog = true)
    public ResultModel deleteUser(@RequestParam String id) {
        return userServiceImpl.deleteUser(id);
    }

    @PostMapping("/resetPassword")
    @ApiOperation("重置用户密码")
    @Access(recordLog = true)
    public ResultModel<String> resetPassword(@RequestParam String id) {
        return userServiceImpl.resetPassword(id);
    }

    @PostMapping("/fetchUserOptions")
    @ApiOperation("获取用户SelectOption")
    @Access
    public ResultModel<List<SelectOption>> fetchUserOptions() {
        return userServiceImpl.fetchUserOptions();
    }

    /**
     * 生成二维码，APP直接扫描绑定，两种方式任选一种
     */
    @PostMapping("getQrcode")
    @ApiOperation("获取谷歌二维码")
    public ResultModel getQrcode(@RequestParam String loginName) {

        // 生成二维码内容
        return userServiceImpl.getQrcode(loginName);
    }

    /**
     * 确定绑定二维码
     */
    @PostMapping("confirmBind")
    @ApiOperation("确定绑定二维码")
    public ResultModel confirmBind(@RequestParam String loginName) {

        // 确认绑定
        return userServiceImpl.confirmBind(loginName);
    }

    @Autowired
    public void setUserService(AdminUserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }
}