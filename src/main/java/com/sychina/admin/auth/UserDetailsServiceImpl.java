package com.sychina.admin.auth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sychina.admin.infra.domain.User;
import com.sychina.admin.infra.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(s)) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        User user = userMapper.selectOne(queryWrapper.eq("loginName", s));

        if (user == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        SecurityUser securityUser = new SecurityUser(user);

        securityUser.setEnabled(true);
        securityUser.setAccountNonLocked(true);
        securityUser.setAccountNonExpired(true);
        securityUser.setCredentialsNonExpired(true);

        return securityUser;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
