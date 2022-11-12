package com.sychina.admin.auth.jwt;

import com.alibaba.fastjson.JSON;
import com.sychina.admin.common.RedisKeys;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 认证,WEB过滤器配置
 *
 * @author Administrator
 */
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private JwtAuthenticationConfig config;

    private final RedisTemplate redisTemplate;

    public JwtTokenAuthenticationFilter(JwtAuthenticationConfig config,RedisTemplate redisTemplate) {
        this.config = config;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = req.getHeader(config.getHeader());
        if (token != null && token.startsWith(config.getPrefix())) {
            token = token.substring(config.getPrefix().length());
            try {
                Claims claims = Jwts.parser().setSigningKey(config.getSecret().getBytes())
                        .parseClaimsJws(token).getBody();
                String userId = claims.getSubject();

                String cacheToke = (String) redisTemplate.opsForValue().get(RedisKeys.adminUserToken + userId);
                if (userId != null && StringUtils.equals(cacheToke,token)) {
                    @SuppressWarnings("unchecked")
                    List<String> authorities = claims.get("authorities", List.class);
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userId,
                            null,
                            authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }else {
                    res.setCharacterEncoding("utf-8");
                    res.getWriter().write(JSON.toJSONString(ResultModel.unauthorized("token失效,请重新登录")));
                    res.getWriter().flush();
                }
            } catch (Exception ignore) {
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(req, res);
    }
}
