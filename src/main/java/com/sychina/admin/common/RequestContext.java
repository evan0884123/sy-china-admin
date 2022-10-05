package com.sychina.admin.common;

import com.sychina.admin.auth.jwt.JwtAuthenticationConfig;
import com.sychina.admin.exception.AuthenticationException;
import com.sychina.admin.infra.domain.User;
import com.sychina.admin.infra.mapper.UserMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator
 */
@Component
public class RequestContext {

    private UserMapper userMapper;

    private JwtAuthenticationConfig config;

    public User getRequestUser() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        String token = request.getHeader(config.getHeader());

        if (StringUtils.isEmpty(token) || !token.startsWith(config.getPrefix())) {
            return null;
        }

        token = token.substring(config.getPrefix().length());

        Claims claims = Jwts.parser().setSigningKey(config.getSecret().getBytes()).parseClaimsJws(token).getBody();
        String userId = claims.getSubject();
        if (userId == null) {
            throw new AuthenticationException("user id is empty");
        }
        User user = userMapper.selectById(userId);
        return user;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setConfig(JwtAuthenticationConfig config) {
        this.config = config;
    }
}
