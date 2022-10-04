package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.common.ResponseConstants;
import com.sychina.admin.common.StringGenerator;
import com.sychina.admin.infra.domain.User;
import com.sychina.admin.infra.mapper.UserMapper;
import com.sychina.admin.service.IUserService;
import com.sychina.admin.web.model.UserInfoModel;
import com.sychina.admin.web.model.UserModel;
import com.sychina.admin.web.model.UserTableModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);

    /**
     * 增加用户
     *
     * @param userModel 前端用户表单
     * @return 是否成功
     */
    public String addUser(UserModel userModel) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        User user = baseMapper.selectOne(queryWrapper.eq("loginName", userModel.getLoginName()));
        if (user != null) {
            return ResponseConstants.EXISTS;
        }

        user = new User();
        user.setLoginName(userModel.getLoginName());
        user.setFullName(userModel.getFullName());
        user.setRoleId(userModel.getRoleId());
        user.setMobile(userModel.getMobile());
        user.setEmail(userModel.getEmail());
        user.setType(userModel.getType());
        String password = StringGenerator.genRandom(8);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        baseMapper.insert(user);

        return password;
    }

    /**
     * 增加用户
     *
     * @param userModel 前端用户表单
     * @return 是否成功
     */
    public String editUser(UserModel userModel) {
        User user = baseMapper.selectById(userModel.getId());
        if (user == null) {
            return ResponseConstants.FAILURE;
        }

        user.setSubDepartments(userModel.getSubDepartments());
        user.setFullName(userModel.getFullName());
        user.setRoleId(userModel.getRoleId());
        user.setMobile(userModel.getMobile());
        user.setEmail(userModel.getEmail());
        user.setType(userModel.getType());

        baseMapper.updateById(user);

        return ResponseConstants.SUCCESS;
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
    public List<UserTableModel> query(String name, Integer roleId, Integer page,
                                      Integer pageSize) {
        Integer offset = (page - 1) * pageSize;
        return baseMapper.findTable(name, roleId, pageSize, offset);
    }

    /**
     * 查询总元素数量
     *
     * @param name   用户名或姓名
     * @param unitId 单位
     * @param roleId 角色
     * @return 数量
     */
    public Integer count(String name, String unitId, Integer roleId) {
        return baseMapper.count(name, unitId, roleId);
    }

    public void deleteUser(String id) {
        baseMapper.deleteById(id);
    }

    public String resetPassword(String id) {
        User user = baseMapper.selectById(id);
        if (user == null) {
            return ResponseConstants.FAILURE;
        }

        String password = StringGenerator.genRandom(8);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        baseMapper.updateById(user);

        return password;
    }

    /**
     * 从token获取用户名，并组合成页面需要的用户信息
     *
     * @param userId userId
     */
    public UserInfoModel buildUserInfo(String userId) {
        User user = baseMapper.selectById(userId);
        if (user == null) {
            return null;
        }

        return baseMapper.loadUserInfo(userId);
    }

    public UserTableModel getProfile(String loginName) {
        return baseMapper.findByLoginName(loginName);
    }

    /**
     * 修改个人信息
     *
     * @param userModel 用户信息表单
     */
    public void updateProfile(UserModel userModel) {
        User user = baseMapper.selectById(userModel.getId());
        if (user == null) {
            return;
        }

        user.setFullName(userModel.getFullName());
        user.setSubDepartments(userModel.getSubDepartments());
        user.setEmail(userModel.getEmail());
        user.setMobile(userModel.getMobile());

        baseMapper.updateById(user);
    }

    /**
     * 修改用户密码
     *
     * @param id          uuid
     * @param password    新密码
     * @param oldPassword 原密码
     */
    public String updatePassword(String id, String password, String oldPassword) {
        User user = baseMapper.selectById(id);
        if (user == null) {
            return ResponseConstants.FAILURE;
        }

        if (!bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
            return ResponseConstants.INCORRECT;
        }

        user.setPassword(bCryptPasswordEncoder.encode(password));

        baseMapper.updateById(user);

        return ResponseConstants.SUCCESS;
    }

    public UserModel loadUserById(String id) {
        User user = baseMapper.selectById(id);
        if (user == null) {
            return null;
        }

        UserModel result = new UserModel();
        result.setId(user.getId());
        result.setLoginName(user.getLoginName());
        result.setFullName(user.getFullName());
        result.setType(user.getType());
        result.setRoleId(user.getRoleId());
        result.setMobile(user.getMobile());
        result.setEmail(user.getEmail());

        return result;
    }
}
