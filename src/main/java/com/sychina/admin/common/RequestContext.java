package com.sychina.admin.common;

import com.sychina.admin.exception.AuthenticationException;
import com.sychina.admin.infra.domain.User;
import com.sychina.admin.infra.mapper.UserMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Service
public class RequestContext {

    private UserMapper userMapper;

    @Value("${jwt.header:Authorization}")
    private String header;

    private String prefix;

    private String secret;

    public User getRequestUser() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        String token = request.getHeader(header);

        if (StringUtils.isEmpty(token) || !token.startsWith(prefix)) {
            return null;
        }

        token = token.substring(prefix.length());

        Claims claims = Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        String userId = claims.getSubject();
        if (userId == null) {
            throw new AuthenticationException("user id is empty");
        }
        User user = userMapper.selectById(userId);
        return user;
    }

    @Value("${jwt.prefix}")
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
