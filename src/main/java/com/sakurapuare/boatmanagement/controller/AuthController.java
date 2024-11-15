package com.sakurapuare.boatmanagement.controller;

import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.LoginRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.User;
import com.sakurapuare.boatmanagement.pojo.vo.TokenVO;
import com.sakurapuare.boatmanagement.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sakurapuare.boatmanagement.constant.ResponseCode.CODE_FORBIDDEN;
import static com.sakurapuare.boatmanagement.constant.ResponseCode.CODE_UNAUTHORIZED;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Authentication")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    public Response<?> login(User user) {
        if (user == null) {
            return Response.error(CODE_UNAUTHORIZED, "Login failed");
        }

        if (user.getIsBlocked()) {
            return Response.error(CODE_FORBIDDEN, "User is blocked");
        }

        if (!user.getIsActive()) {
            return Response.error(CODE_FORBIDDEN, "User is not active");
        }

        String token = user.getToken();
        TokenVO tokenVO = new TokenVO();
        tokenVO.setToken(token);
        return Response.success("Login success", tokenVO);
    }


    @PostMapping("login")
    @Operation(summary = "Login")
    @Parameters({@Parameter(name = "loginRequestDTO", description = "Login request", required = true)})
    public Response<?> loginWithPassword(@RequestBody LoginRequestDTO loginRequestDTO) {
        User user = authService.loginWithPassword(loginRequestDTO);
        return this.login(user);
    }


    @PostMapping("login/code")
    @Operation(summary = "Login by code")
    @Parameters({@Parameter(name = "loginRequestDTO", description = "Login request", required = true)})
    public Response<?> loginByCode(@RequestBody LoginRequestDTO loginRequestDTO) {
        User user = authService.loginWithCode(loginRequestDTO);
        return this.login(user);
    }
}