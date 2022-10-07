package com.sychina.admin.web;

import com.sychina.admin.service.impl.AdminUserServiceImpl;
import com.sychina.admin.web.pojo.models.AdminUserInfoModel;
import com.sychina.admin.web.pojo.models.AdminUserTableModel;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.AdminUserParam;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/admin-user")
@Api(tags = {"后台用户管理"})
public class AdminUserController {

    private AdminUserServiceImpl userServiceImpl;

    private String jwtSecret;

    private String jwtPrefix;

    @PostMapping("/addUser")
    @ApiOperation("新增用户")
    public ResultModel addUser(@Validated AdminUserParam adminUserParam) {
        return userServiceImpl.addUser(adminUserParam);
    }

    @PostMapping("/query")
    @ApiOperation("查询所有用户")
    public ResultModel<List<AdminUserTableModel>> query(@RequestParam String name, Integer roleId, Integer page,
                                                        Integer pageSize) {
        return userServiceImpl.query(name, roleId, page, pageSize);
    }

    @PostMapping("/count")
    @ApiOperation("获取所有用户数")
    public ResultModel<Integer> count(@RequestParam String name, String unitId, Integer roleId) {
        return userServiceImpl.count(name, unitId, roleId);
    }

    @PostMapping("/editUser")
    @ApiOperation("编辑用户")
    public ResultModel editUser(@Validated AdminUserParam adminUserParam) {
        return userServiceImpl.editUser(adminUserParam);
    }

    @DeleteMapping("/deleteUser/{id}")
    @ApiOperation("删除用户")
    public ResultModel deleteUser(@PathVariable String id) {
        return userServiceImpl.deleteUser(id);
    }

    @GetMapping("/resetPassword/{id}")
    @ApiOperation("重置用户密码")
    public ResultModel<String> resetPassword(@PathVariable String id) {
        return userServiceImpl.resetPassword(id);
    }

    @GetMapping("/getProfile/{loginName}")
    @ApiOperation("获取用户信息")
    public ResultModel<AdminUserTableModel> getProfile(@PathVariable String loginName) {
        return userServiceImpl.getProfile(loginName);
    }

    /**
     * @param token token
     * @return 用户信息
     */
    @GetMapping("/info")
    @ApiOperation("获取用户信息")
    public ResultModel<AdminUserInfoModel> info(@RequestParam String token) {
        token = token.substring(jwtPrefix.length());
        Claims claims = Jwts.parser().setSigningKey(jwtSecret.getBytes()).parseClaimsJws(token).getBody();
        String userId = claims.getSubject();
        return userServiceImpl.buildUserInfo(userId);
    }

    @GetMapping("/id/{id}")
    @ApiOperation("获取用户信息")
    public ResultModel<AdminUserTableModel> loadById(@PathVariable String id) {
        return userServiceImpl.loadUserById(id);
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

    @Autowired
    public void setUserService(AdminUserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @Value("${jwt.secret}")
    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    @Value("${jwt.prefix}")
    public void setJwtPrefix(String jwtPrefix) {
        this.jwtPrefix = jwtPrefix;
    }
}