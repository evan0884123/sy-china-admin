package com.sychina.admin.auth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sychina.admin.infra.domain.AdminUser;
import com.sychina.admin.infra.mapper.AdminUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author Administrator
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private AdminUserMapper adminUserMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(s)) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
        AdminUser adminUser = adminUserMapper.selectOne(queryWrapper.eq("login_name", s));

        if (adminUser == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        SecurityAdminUser securityUser = new SecurityAdminUser(adminUser);

        securityUser.setEnabled(true);
        securityUser.setAccountNonLocked(true);
        securityUser.setAccountNonExpired(true);
        securityUser.setCredentialsNonExpired(true);

        return securityUser;
    }

    @Autowired
    public void setUserMapper(AdminUserMapper adminUserMapper) {
        this.adminUserMapper = adminUserMapper;
    }
}
