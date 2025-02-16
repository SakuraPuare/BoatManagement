package com.sakurapuare.boatmanagement.interceptor;

import com.sakurapuare.boatmanagement.common.RequestInfo;
import com.sakurapuare.boatmanagement.common.context.RequestContext;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class RequestInfoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                             @NonNull Object handler) {
        // 获取请求信息
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setIp(request.getRemoteAddr());
        requestInfo.setUserAgent(request.getHeader("User-Agent"));
        requestInfo.setReferer(request.getHeader("Referer"));
        requestInfo.setUrl(request.getRequestURI());
        requestInfo.setMethod(request.getMethod());

        RequestContext.setContext(requestInfo);

        return true;
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                @NonNull Object handler, @SuppressWarnings("null") Exception ex) {
        RequestContext.clear();
    }
}
