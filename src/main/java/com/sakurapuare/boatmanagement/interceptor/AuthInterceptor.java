package com.sakurapuare.boatmanagement.interceptor;

import com.sakurapuare.boatmanagement.common.UserContext;
import com.sakurapuare.boatmanagement.constant.UserRole;
import com.sakurapuare.boatmanagement.pojo.entity.Accounts;
import com.sakurapuare.boatmanagement.service.AccountsService;
import com.sakurapuare.boatmanagement.utils.RoleUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final AccountsService accountService;

    public AuthInterceptor(AccountsService accountService) {
        this.accountService = accountService;
    }

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
        // 获取token
        String token = request.getHeader("Authorization");
        if (!StringUtils.hasText(token)) {
            writeResponse(response, 401, "未登录");
            return false;
        }

        // 验证token并获取用户信息
        Accounts account = null;
        try {
            token = token.replace("Bearer ", "");
            account = accountService.getAccountByToken(token);
            UserContext.setAccount(account);
        } catch (Exception e) {
            writeResponse(response, 401, "token无效");
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

        // 校验权限
        String uri = request.getRequestURI();
        Map<String, Integer> roleMap = new HashMap<>();
        roleMap.put("/user/", UserRole.USER);
        roleMap.put("/merchant/", UserRole.MERCHANT);
        roleMap.put("/vendor/", UserRole.VENDOR);
        roleMap.put("/admin/", UserRole.ADMIN);

        for (Map.Entry<String, Integer> entry : roleMap.entrySet()) {
            if (uri.startsWith(entry.getKey())) {
                if (!RoleUtils.hasRole(account.getRole(), entry.getValue())) {
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
        // 清理ThreadLocal
        UserContext.clear();

    }
}