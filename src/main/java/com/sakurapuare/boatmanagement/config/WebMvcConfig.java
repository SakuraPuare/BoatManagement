package com.sakurapuare.boatmanagement.config;

import com.sakurapuare.boatmanagement.interceptor.AuthInterceptor;
import com.sakurapuare.boatmanagement.interceptor.RequestInfoInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    private final RequestInfoInterceptor requestInfoInterceptor;

    public WebMvcConfig(AuthInterceptor authInterceptor, RequestInfoInterceptor requestInfoInterceptor) {
        this.authInterceptor = authInterceptor;
        this.requestInfoInterceptor = requestInfoInterceptor;
    }

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/auth/**",
                        "/error",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/doc.html",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/favicon.ico",
                        // Knife4j
                        "/api/doc.html",
                        "/api/swagger-resources/**",
                        "/api/v3/api-docs/**",
                        "/api/swagger-ui/**"
                )
                .excludePathPatterns("/login", "/register", "/register");

        registry.addInterceptor(requestInfoInterceptor)
                .addPathPatterns("/**");
    }

    // 设置静态资源映射
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
} 