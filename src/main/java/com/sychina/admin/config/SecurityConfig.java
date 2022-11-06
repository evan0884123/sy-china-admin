package com.sychina.admin.config;

import com.alibaba.fastjson.JSON;
import com.sychina.admin.auth.UserDetailsServiceImpl;
import com.sychina.admin.auth.WhaleAuthenticationProvider;
import com.sychina.admin.auth.jwt.JwtAuthenticationConfig;
import com.sychina.admin.auth.jwt.JwtTokenAuthenticationFilter;
import com.sychina.admin.auth.jwt.JwtUsernamePasswordAuthenticationFilter;
import com.sychina.admin.service.impl.AdminUserServiceImpl;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * 网关安全配置，用于过滤访问请求
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtAuthenticationConfig config;

    private AdminUserServiceImpl adminUserService;

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public WhaleAuthenticationProvider authenticationProvider() {
        WhaleAuthenticationProvider result = new WhaleAuthenticationProvider();
        result.setUserDetailsService(userDetailsService);
        result.setPasswordEncoder(new BCryptPasswordEncoder(4));

        return result;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and().csrf().disable().logout().disable().formLogin().disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling()
                .authenticationEntryPoint((req, rsp, e) -> {
                    rsp.getWriter().println(JSON.toJSONString(ResultModel.unauthorized("token invalidation")));
                    rsp.getWriter().flush();
                })
                .and()
                .addFilterAfter(new JwtTokenAuthenticationFilter(config),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new JwtUsernamePasswordAuthenticationFilter(config, authenticationManager(), adminUserService),
                        UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests().antMatchers(config.getUrl()).permitAll()
                // 放行swagger
                .antMatchers("/adminUser/confirmBind", "/adminUser/getQrcode", "/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/v3/**", "/api/**", "/doc.html").permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().authenticated();
    }

    @Autowired
    public void setConfig(JwtAuthenticationConfig config) {
        this.config = config;
    }

    @Autowired
    public void setAdminUserService(AdminUserServiceImpl adminUserService) {
        this.adminUserService = adminUserService;
    }
}
