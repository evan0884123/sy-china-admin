package com.sychina.admin.web.system;

import com.sychina.admin.service.impl.AdminUserServiceImpl;
import com.sychina.admin.web.pojo.SelectOption;
import com.sychina.admin.web.pojo.models.AdminUserInfoModel;
import com.sychina.admin.web.pojo.models.AdminUserTable;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.AdminUserParam;
import com.sychina.admin.web.pojo.params.AdminUserProfileQuery;
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
    public ResultModel<AdminUserInfoModel> info() {

        return userServiceImpl.buildUserInfo();
    }

    @PostMapping("/getProfile")
    @ApiOperation("获取用户信息")
    public ResultModel<AdminUserTable> getProfile(@Validated AdminUserProfileQuery profileQuery) {
        return userServiceImpl.getProfile(profileQuery);
    }

    @PostMapping("/updateProfile")
    @ApiOperation("更新个人信息")
    public ResultModel updateProfile(@Validated AdminUserParam adminUserParam) {
        return userServiceImpl.updateProfile(adminUserParam);
    }

    @PostMapping("/updatePassword")
    @ApiOperation("更新个人密码")
    public ResultModel updatePassword(@RequestParam String id, String password, String oldPassword) {
        return userServiceImpl.updatePassword(id, password, oldPassword);
    }



    @PostMapping("/addUser")
    @ApiOperation("新增用户")
    public ResultModel addUser(@Validated AdminUserParam adminUserParam) {
        return userServiceImpl.addUser(adminUserParam);
    }

    @PostMapping("/query")
    @ApiOperation("查询所有用户")
    public ResultModel query(@Validated AdminUserQuery userQuery) {
        return userServiceImpl.query(userQuery);
    }

    @PostMapping("/editUser")
    @ApiOperation("编辑用户")
    public ResultModel editUser(@Validated AdminUserParam adminUserParam) {
        return userServiceImpl.editUser(adminUserParam);
    }

    @PostMapping("/deleteUser")
    @ApiOperation("删除用户")
    public ResultModel deleteUser(@RequestParam String id) {
        return userServiceImpl.deleteUser(id);
    }

    @PostMapping("/resetPassword")
    @ApiOperation("重置用户密码")
    public ResultModel<String> resetPassword(@RequestParam String id) {
        return userServiceImpl.resetPassword(id);
    }

    @PostMapping("/fetchUserOptions")
    @ApiOperation("获取用户SelectOption")
    public ResultModel<List<SelectOption>> fetchUserOptions() {
        return userServiceImpl.fetchUserOptions();
    }


    @Autowired
    public void setUserService(AdminUserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }
}