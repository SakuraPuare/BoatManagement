package com.sakurapuare.boatmanagement.interceptor;

import com.sakurapuare.boatmanagement.common.context.UserContext;
import com.sakurapuare.boatmanagement.pojo.entity.Accounts;
import com.sakurapuare.boatmanagement.pojo.entity.Permission;
import com.sakurapuare.boatmanagement.pojo.entity.Role;
import com.sakurapuare.boatmanagement.service.AccountsService;
import com.sakurapuare.boatmanagement.service.RoleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final AccountsService accountService;
    private final RoleService roleService;

    private void writeResponse(HttpServletResponse response, int code, String message) {
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.write("{\"code\":" + code + ",\"message\":\"" + message + "\",\"data\":null}");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                             @NonNull Object handler) {
        // 获取 token
        String token = request.getHeader("Authorization");
        if (!StringUtils.hasText(token)) {
            writeResponse(response, 401, "未登录");
            return false;
        }

        // 验证 token 并获取用户信息
        Accounts account;
        try {
            token = token.replace("Bearer ", "");
            account = accountService.getAccountByToken(token);
            UserContext.setAccount(account);
        } catch (Exception e) {
            writeResponse(response, 401, "token 无效");
            return false;
        }

        if (account == null) {
            writeResponse(response, 401, "未登录");
            return false;
        }

        // 初步校验权限
        if (account.getIsBlocked()) {
            writeResponse(response, 401, "账号已锁定");
            return false;
        }

        // 获取用户角色和权限
        List<Role> roles = roleService.getUserRoles(account.getId());
        List<Permission> permissions = roleService.getUserPermissions(account.getId());
        
        // 检查 URI 访问权限
        String uri = request.getRequestURI();
        
        // 检查是否是管理员，管理员可以访问所有路径
        if (roleService.hasAnyRole(account.getId(), Arrays.asList("ADMIN"))) {
            return true;
        }
        
        // 特殊路径处理 - 允许所有已登录用户访问基本信息
        if (uri.equals("/user/info/me")) {
            return true; // 所有已登录用户都可以获取自己的信息
        }
        
        // 基于 URI 路径检查角色
        Map<String, List<String>> pathRoleMap = new HashMap<>();
        pathRoleMap.put("/user", Arrays.asList("USER", "MERCHANT", "VENDOR", "ADMIN"));
        pathRoleMap.put("/merchant", Arrays.asList("MERCHANT", "ADMIN"));
        pathRoleMap.put("/vendor", Arrays.asList("VENDOR", "ADMIN"));
        pathRoleMap.put("/admin", Arrays.asList("ADMIN"));
        
        for (Map.Entry<String, List<String>> entry : pathRoleMap.entrySet()) {
            if (uri.startsWith(entry.getKey())) {
                if (!roleService.hasAnyRole(account.getId(), entry.getValue())) {
                    writeResponse(response, 401, "权限不足");
                    return false;
                }
                break;
            }
        }

        return true;
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                @NonNull Object handler, @SuppressWarnings("null") Exception ex) {
        // 清理 ThreadLocal
        UserContext.clear();
    }
}