package com.sychina.admin.auth.jwt;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sychina.admin.infra.domain.AdminUser;
import com.sychina.admin.service.impl.AdminUserServiceImpl;
import com.sychina.admin.utils.GoogleAuthenticator;
import com.sychina.admin.web.pojo.models.response.ResponseStatus;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Description: 默认往 /login post json '{ username, password }'
 *
 * @author Administrator
 */
public class JwtUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final JwtAuthenticationConfig config;

    private final AdminUserServiceImpl adminUserService;

    public JwtUsernamePasswordAuthenticationFilter(JwtAuthenticationConfig config,
                                                   AuthenticationManager authManager,
                                                   AdminUserServiceImpl adminUserService) {
        super(new AntPathRequestMatcher(config.getUrl(), "POST"));
        setAuthenticationManager(authManager);
        this.config = config;
        this.adminUserService = adminUserService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException,
            IOException {
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(req.getParameter("username"), req
                        .getParameter("password"), Collections.emptyList()));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        response.getWriter().println(JSON.toJSONString(ResultModel.failed(failed.getMessage())));
        response.getWriter().flush();
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res,
                                            FilterChain chain, Authentication auth) throws IOException {

        ResultModel resultModel;
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
        AdminUser adminUser = adminUserService.getOne(queryWrapper.eq("id", auth.getName()));
        if (StringUtils.isBlank(adminUser.getGoogleSecret())) {
            resultModel = ResultModel.unbindGoogle("请先绑定谷歌验证器");
        } else {
            String code = req.getParameter("code");
            if (code == null) {
                resultModel = ResultModel.bindGoogle("请先绑定谷歌验证器");
            } else {
                boolean b = GoogleAuthenticator.checkCode(adminUser.getGoogleSecret(), Long.parseLong(code), System.currentTimeMillis());
                if (b) {
                    Instant now = Instant.now();
                    String token = Jwts
                            .builder()
                            .setSubject(auth.getName())
                            .claim(
                                    "authorities",
                                    auth.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                                            .collect(Collectors.toList())).setIssuedAt(Date.from(now))
                            .setExpiration(Date.from(now.plusSeconds(config.getExpiration())))
                            .signWith(SignatureAlgorithm.HS256, config.getSecret().getBytes()).compact();
                    res.addHeader(config.getHeader(), config.getPrefix() + token);

                    resultModel = ResultModel.succeed(config.getPrefix() + token);
                } else {
                    resultModel = new ResultModel(ResponseStatus.GOOGLE_CODE_ERROR, "google验证码错误");
                }
            }

        }

        res.getWriter().write(JSON.toJSONString(resultModel));
        res.getWriter().flush();
    }
}
