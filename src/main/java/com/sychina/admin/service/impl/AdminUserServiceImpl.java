package com.sychina.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sychina.admin.common.RequestContext;
import com.sychina.admin.infra.domain.AdminMenu;
import com.sychina.admin.infra.domain.AdminRole;
import com.sychina.admin.infra.domain.AdminUser;
import com.sychina.admin.infra.mapper.AdminMenuMapper;
import com.sychina.admin.infra.mapper.AdminUserMapper;
import com.sychina.admin.service.IAdminUserService;
import com.sychina.admin.utils.StringGenerator;
import com.sychina.admin.web.pojo.SelectOption;
import com.sychina.admin.web.pojo.models.AdminMenuModel;
import com.sychina.admin.web.pojo.models.AdminUserTable;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import com.sychina.admin.web.pojo.params.AdminUserParam;
import com.sychina.admin.web.pojo.params.AdminUserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements IAdminUserService {

    private RequestContext requestContext;

    private AdminRoleServiceImpl adminRoleService;

    private AdminMenuMapper adminMenuMapper;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);

    /**
     * 从token获取用户名，并组合成页面需要的用户信息
     */
    public ResultModel<AdminUserTable> buildUserInfo() {

        AdminUser adminUser = requestContext.getRequestUser();
        Assert.notNull(adminUser, "未找到该用户");
        AdminRole adminRole = adminRoleService.getById(adminUser.getRoleId());
        Assert.notNull(adminRole, "未找到该用户的关联角色");

        String[] menuId = adminRole.getMenus().split(",");
        List<AdminMenu> adminMenuList = adminMenuMapper.selectBatchIds(Arrays.asList(menuId));

        AdminUserTable adminUserTable = new AdminUserTable(adminUser)
                .setRoleName(adminRole.getName())
                .setAdminMenus(new AdminMenuModel().convert(adminMenuList));

        return ResultModel.succeed(adminUserTable);
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
     * 查询用户
     *
     * @return 用户列表
     */
    public ResultModel query(AdminUserQuery userQuery) {

        Page<AdminUserTable> table = baseMapper.findTable(userQuery.page(), userQuery);

        return ResultModel.succeed(table);
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

    public ResultModel<List<SelectOption>> fetchUserOptions() {

        List<AdminUser> adminUsers = baseMapper.selectList(new QueryWrapper<>());
        List<SelectOption> userSelect = new ArrayList<>();

        adminUsers.forEach(user -> {
            SelectOption selectOption = new SelectOption();
            selectOption.setLabel(user.getFullName());
            selectOption.setValue(user.getId());
            userSelect.add(selectOption);
        });

        return ResultModel.succeed(userSelect);
    }

    @Autowired
    public void setRequestContext(RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
        System.out.println(bCryptPasswordEncoder.encode("123456"));
    }

    @Autowired
    public void setAdminRoleService(AdminRoleServiceImpl adminRoleService) {
        this.adminRoleService = adminRoleService;
    }

    @Autowired
    public void setAdminMenuMapper(AdminMenuMapper adminMenuMapper) {
        this.adminMenuMapper = adminMenuMapper;
    }
}
