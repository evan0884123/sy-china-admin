package com.sychina.admin.web;

import com.sychina.admin.service.impl.UserServiceImpl;
import com.sychina.admin.web.model.UserInfoModel;
import com.sychina.admin.web.model.UserModel;
import com.sychina.admin.web.model.UserTableModel;
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
 */
@RestController
@RequestMapping("/user")
@Api(tags = {"数据分析模块"})
public class UserController {

    private UserServiceImpl userServiceImpl;

    private String jwtSecret;

    private String jwtPrefix;

    @PostMapping("/addUser")
    @ApiOperation("新增用户")
    public String addUser(@Validated UserModel userModel) {
        return userServiceImpl.addUser(userModel);
    }

    @PostMapping("/query")
    @ApiOperation("查询所有用户")
    public List<UserTableModel> query(@RequestParam String name, Integer roleId, Integer page,
                                      Integer pageSize) {
        return userServiceImpl.query(name, roleId, page, pageSize);
    }

    @PostMapping("/count")
    @ApiOperation("获取所有用户数")
    public Integer count(@RequestParam String name, String unitId, Integer roleId) {
        return userServiceImpl.count(name, unitId, roleId);
    }

    @PostMapping("/editUser")
    @ApiOperation("编辑用户")
    public String editUser(@Validated UserModel userModel) {
        return userServiceImpl.editUser(userModel);
    }

    @DeleteMapping("/deleteUser/{id}")
    @ApiOperation("获取路子分析")
    public void deleteUser(@PathVariable String id) {
        userServiceImpl.deleteUser(id);
    }

    @GetMapping("/resetPassword/{id}")
    @ApiOperation("获取路子分析")
    public String resetPassword(@PathVariable String id) {
        return userServiceImpl.resetPassword(id);
    }

    @GetMapping("/getProfile/{loginName}")
    @ApiOperation("获取路子分析")
    public UserTableModel getProfile(@PathVariable String loginName) {
        return userServiceImpl.getProfile(loginName);
    }

    /**
     * @param token token
     * @return 用户信息
     */
    @GetMapping("/info")
    @ApiOperation("获取路子分析")
    public UserInfoModel info(@RequestParam String token) {
        token = token.substring(jwtPrefix.length());
        Claims claims = Jwts.parser().setSigningKey(jwtSecret.getBytes()).parseClaimsJws(token).getBody();
        String userId = claims.getSubject();
        return userServiceImpl.buildUserInfo(userId);
    }

    @GetMapping("/id/{id}")
    @ApiOperation("获取路子分析")
    public UserModel loadById(@PathVariable String id) {
        return userServiceImpl.loadUserById(id);
    }

    @PostMapping("/updateProfile")
    @ApiOperation("获取路子分析")
    public void updateProfile(@Validated UserModel userModel) {
        userServiceImpl.updateProfile(userModel);
    }

    @PostMapping("/updatePassword")
    @ApiOperation("获取路子分析")
    public String updatePassword(@RequestParam String id, String password, String oldPassword) {
        return userServiceImpl.updatePassword(id, password, oldPassword);
    }

    @Autowired
    public void setUserService(UserServiceImpl userServiceImpl) {
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