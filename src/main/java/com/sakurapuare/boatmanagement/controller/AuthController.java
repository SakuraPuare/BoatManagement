package com.sakurapuare.boatmanagement.controller;

import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.LoginRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.User;
import com.sakurapuare.boatmanagement.pojo.vo.TokenVO;
import com.sakurapuare.boatmanagement.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sakurapuare.boatmanagement.constant.ResponseCode.FORBIDDEN;
import static com.sakurapuare.boatmanagement.constant.ResponseCode.UNAUTHORIZED;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Authentication")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("login")
    @Operation(summary = "Login")
    public Response<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        String username = loginRequestDTO.getUsername();

        User user = authService.login(loginRequestDTO);

        if (user == null) {
            return Response.error(UNAUTHORIZED, "Login failed");
        }

        if (user.getIsBlocked()) {
            return Response.error(FORBIDDEN, "User is blocked");
        }

        if (!user.getIsActive()) {
            return Response.error(FORBIDDEN, "User is not active");
        }

        String token = user.getToken();
        TokenVO tokenVO = new TokenVO();
        tokenVO.setToken(token);
        return Response.success("Login success", tokenVO);
    }

}
