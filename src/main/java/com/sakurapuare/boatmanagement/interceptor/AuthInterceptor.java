package com.sakurapuare.boatmanagement.interceptor;

import com.sakurapuare.boatmanagement.common.UserContext;
import com.sakurapuare.boatmanagement.service.AccountsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.PrintWriter;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final AccountsService accountService;

    public AuthInterceptor(AccountsService accountService) {
        this.accountService = accountService;
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {
        // 获取token
        String token = request.getHeader("Authorization");
        if (!StringUtils.hasText(token)) {
            // 设置response的内容类型为application/json
            response.setContentType("application/json;charset=UTF-8");
            // 将json数据写入response
            try ( // 获取response的输出流
                  PrintWriter writer = response.getWriter()) {
                // 将json数据写入response
                writer.write("{\"code\":401,\"message\":\"未登录\",\"data\":null}");
                writer.flush();
            }
            return false;
        }

        // 验证token并获取用户信息
        try {
            token = token.replace("Bearer ", "");
            var account = accountService.getAccountByToken(token);
            UserContext.setAccount(account);
            return true;
        } catch (Exception e) {
            // 设置response的内容类型为application/json
            response.setContentType("application/json;charset=UTF-8");
            // 将json数据写入response
            try ( // 获取response的输出流
                  PrintWriter writer = response.getWriter()) {
                // 将json数据写入response
                writer.write("{\"code\":401,\"message\":\"token无效\",\"data\":null}");
                writer.flush();
            }
            return false;
        }
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                @NonNull Object handler, @SuppressWarnings("null") Exception ex) {
        // 清理ThreadLocal
        UserContext.clear();
    }
}