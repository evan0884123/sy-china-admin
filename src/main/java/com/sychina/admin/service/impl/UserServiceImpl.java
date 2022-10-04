package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.common.StringGenerator;
import com.sychina.admin.infra.domain.User;
import com.sychina.admin.infra.mapper.UserMapper;
import com.sychina.admin.service.IUserService;
import com.sychina.admin.web.pojo.models.UserInfoModel;
import com.sychina.admin.web.pojo.models.UserTableModel;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.UserParam;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 *
 * @author Administrator
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);

    /**
     * 增加用户
     *
     * @param userParam 前端用户表单
     * @return 是否成功
     */
    public ResultModel<String> addUser(UserParam userParam) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        User user = baseMapper.selectOne(queryWrapper.eq("loginName", userParam.getLoginName()));
        Assert.isNull(user, "用户已存在");

        user = new User();
        user.setLoginName(userParam.getLoginName());
        user.setFullName(userParam.getFullName());
        user.setRoleId(userParam.getRoleId());
        user.setMobile(userParam.getMobile());
        user.setEmail(userParam.getEmail());
        user.setType(userParam.getType());
        String password = StringGenerator.genRandom(8);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        baseMapper.insert(user);

        return ResultModel.succeed(password);
    }

    /**
     * 增加用户
     *
     * @param userParam 前端用户表单
     * @return 是否成功
     */
    public ResultModel editUser(UserParam userParam) {

        User user = baseMapper.selectById(userParam.getId());
        Assert.notNull(user, "未找到该用户信息");

        user.setSubDepartments(userParam.getSubDepartments());
        user.setFullName(userParam.getFullName());
        user.setRoleId(userParam.getRoleId());
        user.setMobile(userParam.getMobile());
        user.setEmail(userParam.getEmail());
        user.setType(userParam.getType());

        baseMapper.updateById(user);

        return ResultModel.succeed();
    }

    /**
     * 查询用户
     *
     * @param name     用户名或姓名
     * @param roleId   角色
     * @param page     页数
     * @param pageSize 单页元素数量
     * @return 用户列表
     */
    public ResultModel<List<UserTableModel>> query(String name, Integer roleId, Integer page,
                                                   Integer pageSize) {
        Integer offset = (page - 1) * pageSize;
        List<UserTableModel> table = baseMapper.findTable(name, roleId, pageSize, offset);

        return ResultModel.succeed(table);
    }

    /**
     * 查询总元素数量
     *
     * @param name   用户名或姓名
     * @param unitId 单位
     * @param roleId 角色
     * @return 数量
     */
    public ResultModel<Integer> count(String name, String unitId, Integer roleId) {

        Integer count = baseMapper.count(name, unitId, roleId);
        return ResultModel.succeed(count);
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    public ResultModel deleteUser(String id) {

        baseMapper.deleteById(id);
        return ResultModel.succeed();
    }

    /**
     * 重置密码
     *
     * @param id
     * @return
     */
    public ResultModel<String> resetPassword(String id) {

        User user = baseMapper.selectById(id);
        Assert.notNull(user, "未找到该用户");

        String password = StringGenerator.genRandom(8);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        baseMapper.updateById(user);

        return ResultModel.succeed(password);
    }

    /**
     * 从token获取用户名，并组合成页面需要的用户信息
     *
     * @param userId userId
     */
    public ResultModel<UserInfoModel> buildUserInfo(String userId) {

        User user = baseMapper.selectById(userId);
        Assert.notNull(user, "未找到该用户");

        UserInfoModel userInfoModel = baseMapper.loadUserInfo(userId);

        return ResultModel.succeed(userInfoModel);
    }

    public ResultModel<UserTableModel> getProfile(String loginName) {

        UserTableModel userTable = baseMapper.findByLoginName(loginName);

        return ResultModel.succeed(userTable);
    }

    /**
     * 修改个人信息
     *
     * @param userParam 用户信息表单
     */
    public ResultModel updateProfile(UserParam userParam) {

        User user = baseMapper.selectById(userParam.getId());
        Assert.notNull(user, "未找到该用户");

        user.setFullName(userParam.getFullName());
        user.setSubDepartments(userParam.getSubDepartments());
        user.setEmail(userParam.getEmail());
        user.setMobile(userParam.getMobile());

        baseMapper.updateById(user);

        return ResultModel.succeed();
    }

    /**
     * 修改用户密码
     *
     * @param id          uuid
     * @param password    新密码
     * @param oldPassword 原密码
     */
    public ResultModel updatePassword(String id, String password, String oldPassword) {

        User user = baseMapper.selectById(id);
        Assert.notNull(user, "未找到该用户");
        Assert.isTrue(!bCryptPasswordEncoder.matches(oldPassword, user.getPassword()), "旧密码不正确");

        user.setPassword(bCryptPasswordEncoder.encode(password));
        baseMapper.updateById(user);

        return ResultModel.succeed();
    }

    public ResultModel<UserTableModel> loadUserById(String id) {

        User user = baseMapper.selectById(id);
        Assert.notNull(user, "未找到该用户");

        UserTableModel result = new UserTableModel();
        result.setId(user.getId());
        result.setLoginName(user.getLoginName());
        result.setFullName(user.getFullName());
        result.setType(user.getType());
        result.setRoleId(user.getRoleId());
        result.setMobile(user.getMobile());
        result.setEmail(user.getEmail());

        return ResultModel.succeed(result);
    }
}
