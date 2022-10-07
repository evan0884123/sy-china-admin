package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.common.StringGenerator;
import com.sychina.admin.infra.domain.AdminUser;
import com.sychina.admin.infra.mapper.AdminUserMapper;
import com.sychina.admin.service.IAdminUserService;
import com.sychina.admin.web.pojo.models.AdminUserInfoModel;
import com.sychina.admin.web.pojo.models.AdminUserTableModel;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.AdminUserParam;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Administrator
 */
@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements IAdminUserService {

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);

    /**
     * 增加用户
     *
     * @param adminUserParam 前端用户表单
     * @return 是否成功
     */
    public ResultModel<String> addUser(AdminUserParam adminUserParam) {

        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
        AdminUser adminUser = baseMapper.selectOne(queryWrapper.eq("login_name", adminUserParam.getLoginName()));
        Assert.isNull(adminUser, "用户已存在");

        adminUser = new AdminUser();
        adminUser.setLoginName(adminUserParam.getLoginName());
        adminUser.setFullName(adminUserParam.getFullName());
        adminUser.setRoleId(adminUserParam.getRoleId());
        adminUser.setMobile(adminUserParam.getMobile());
        adminUser.setEmail(adminUserParam.getEmail());
        adminUser.setType(adminUserParam.getType());
        String password = StringGenerator.genRandom(8);
        adminUser.setPassword(bCryptPasswordEncoder.encode(password));

        baseMapper.insert(adminUser);

        return ResultModel.succeed(password);
    }

    /**
     * 增加用户
     *
     * @param adminUserParam 前端用户表单
     * @return 是否成功
     */
    public ResultModel editUser(AdminUserParam adminUserParam) {

        AdminUser adminUser = baseMapper.selectById(adminUserParam.getId());
        Assert.notNull(adminUser, "未找到该用户信息");

        adminUser.setSubDepartments(adminUserParam.getSubDepartments());
        adminUser.setFullName(adminUserParam.getFullName());
        adminUser.setRoleId(adminUserParam.getRoleId());
        adminUser.setMobile(adminUserParam.getMobile());
        adminUser.setEmail(adminUserParam.getEmail());
        adminUser.setType(adminUserParam.getType());

        baseMapper.updateById(adminUser);

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
    public ResultModel<List<AdminUserTableModel>> query(String name, Integer roleId, Integer page,
                                                        Integer pageSize) {
        Integer offset = (page - 1) * pageSize;
        List<AdminUserTableModel> table = baseMapper.findTable(name, roleId, pageSize, offset);

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

        AdminUser adminUser = baseMapper.selectById(id);
        Assert.notNull(adminUser, "未找到该用户");

        String password = StringGenerator.genRandom(8);
        adminUser.setPassword(bCryptPasswordEncoder.encode(password));

        baseMapper.updateById(adminUser);

        return ResultModel.succeed(password);
    }

    /**
     * 从token获取用户名，并组合成页面需要的用户信息
     *
     * @param userId userId
     */
    public ResultModel<AdminUserInfoModel> buildUserInfo(String userId) {

        AdminUser adminUser = baseMapper.selectById(userId);
        Assert.notNull(adminUser, "未找到该用户");

        AdminUserInfoModel adminUserInfoModel = baseMapper.loadUserInfo(userId);

        return ResultModel.succeed(adminUserInfoModel);
    }

    public ResultModel<AdminUserTableModel> getProfile(String loginName) {

        AdminUserTableModel userTable = baseMapper.findByLoginName(loginName);

        return ResultModel.succeed(userTable);
    }

    /**
     * 修改个人信息
     *
     * @param adminUserParam 用户信息表单
     */
    public ResultModel updateProfile(AdminUserParam adminUserParam) {

        AdminUser adminUser = baseMapper.selectById(adminUserParam.getId());
        Assert.notNull(adminUser, "未找到该用户");

        adminUser.setFullName(adminUserParam.getFullName());
        adminUser.setSubDepartments(adminUserParam.getSubDepartments());
        adminUser.setEmail(adminUserParam.getEmail());
        adminUser.setMobile(adminUserParam.getMobile());

        baseMapper.updateById(adminUser);

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

        AdminUser adminUser = baseMapper.selectById(id);
        Assert.notNull(adminUser, "未找到该用户");
        Assert.isTrue(!bCryptPasswordEncoder.matches(oldPassword, adminUser.getPassword()), "旧密码不正确");

        adminUser.setPassword(bCryptPasswordEncoder.encode(password));
        baseMapper.updateById(adminUser);

        return ResultModel.succeed();
    }

    public ResultModel<AdminUserTableModel> loadUserById(String id) {

        AdminUser adminUser = baseMapper.selectById(id);
        Assert.notNull(adminUser, "未找到该用户");

        AdminUserTableModel result = new AdminUserTableModel();
        result.setId(adminUser.getId());
        result.setLoginName(adminUser.getLoginName());
        result.setFullName(adminUser.getFullName());
        result.setType(adminUser.getType());
        result.setRoleId(adminUser.getRoleId());
        result.setMobile(adminUser.getMobile());
        result.setEmail(adminUser.getEmail());

        return ResultModel.succeed(result);
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
        System.out.println(bCryptPasswordEncoder.encode("123456"));
    }
}
