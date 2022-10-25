package com.sychina.admin.aop;

import com.alibaba.fastjson.JSON;
import com.sychina.admin.common.RequestContext;
import com.sychina.admin.infra.domain.ActionLog;
import com.sychina.admin.infra.domain.AdminMenu;
import com.sychina.admin.infra.domain.AdminRole;
import com.sychina.admin.infra.domain.AdminUser;
import com.sychina.admin.infra.mapper.AdminMenuMapper;
import com.sychina.admin.service.impl.ActionLogServiceImpl;
import com.sychina.admin.service.impl.AdminRoleServiceImpl;
import com.sychina.admin.utils.LocalDateTimeHelper;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

/**
 * Description:
 */

@Component
@Aspect
@Slf4j
public class AccessAspect {

    private ActionLogServiceImpl actionLogService;

    private AdminRoleServiceImpl adminRoleService;

    private AdminMenuMapper adminMenuMapper;

    private RequestContext requestContext;

    /**
     * 切点
     */
    @Pointcut("@annotation(com.sychina.admin.aop.Access)")
    public void Accessaspect() {
    }

    /**
     * 记录日志
     *
     * @param joinPoint
     */

    @Around("Accessaspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        AdminUser adminUser = requestContext.getRequestUser();
        Assert.notNull(adminUser, "未找到该用户");
        AdminRole adminRole = adminRoleService.getById(adminUser.getRoleId());
        Assert.notNull(adminRole, "未找到该用户的关联角色");

        String[] menuId = adminRole.getMenus().split(",");
        List<AdminMenu> adminMenuList = adminMenuMapper.selectBatchIds(Arrays.asList(menuId));
        Class<?> claaz = joinPoint.getTarget().getClass();
        String[] value = claaz.getDeclaredAnnotation(RequestMapping.class).value();
        boolean accessMark = false;
        for (AdminMenu menu : adminMenuList) {
            for (String path : value) {
                if (menu.getCode().endsWith(path)) {
                    accessMark = true;
                }
            }
        }
        Assert.isTrue(accessMark, "该用户没有此权限");
        Method method = getMethod(joinPoint);

        if (method.getDeclaredAnnotation(Access.class).recordLog()) {
            return recordLog(joinPoint, method, adminUser);
        } else {
            return joinPoint.proceed();
        }
    }

    private Object recordLog(ProceedingJoinPoint joinPoint, Method method, AdminUser adminUser) throws Throwable {

        ActionLog actionLog = new ActionLog();

        actionLog.setUserId(adminUser.getId());
        actionLog.setAdminUserName(adminUser.getLoginName());

        actionLog.setMethod(method.getDeclaringClass().getName() + "." + method.getName());
        actionLog.setMethodDes(method.getDeclaredAnnotation(ApiOperation.class).value());

        String parameters = getMethodParam(joinPoint);
        actionLog.setParameters(parameters);

        ResultModel resultModel = (ResultModel) joinPoint.proceed();

        actionLog.setResult(resultModel.getCode());
        actionLog.setReturnValue(JSON.toJSONString(resultModel));

        actionLog.setActionTime(LocalDateTimeHelper.toStr(LocalDateTime.now()));
        actionLog.setCreate(LocalDateTimeHelper.toLong(LocalDateTime.now()));

        actionLogService.saveOrUpdate(actionLog);

        return resultModel;
    }

    private String getMethodParam(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        if (args.length == 0) {
            return "";
        }

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] names = methodSignature.getParameterNames();

        StringJoiner joiner = new StringJoiner(",", "{", "}");

        for (int i = 0; i < names.length; i++) {
            joiner.add(names[i] + ":" + args[i] != null ? args[i].toString() : "");
        }

        return joiner.toString();
    }

    private Method getMethod(JoinPoint joinPoint) throws Exception {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        return joinPoint.getTarget().getClass().getMethod(methodSignature.getName(),
                methodSignature.getParameterTypes());
    }

    @Autowired
    public void setActionLogService(ActionLogServiceImpl actionLogService) {
        this.actionLogService = actionLogService;
    }

    @Autowired
    public void setRequestContext(RequestContext requestContext) {
        this.requestContext = requestContext;
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
