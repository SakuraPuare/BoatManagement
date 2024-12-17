package com.sakurapuare.boatmanagement.controller;

import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.AuthRequestDTO;
import com.sakurapuare.boatmanagement.pojo.dto.NameRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Users;
import com.sakurapuare.boatmanagement.pojo.vo.TokenVO;
import com.sakurapuare.boatmanagement.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    public Response<TokenVO> auth(Users user) {
        if (user == null) {
            return Response.error(CODE_UNAUTHORIZED, "Auth failed");
        }

        if (user.getIsBlocked()) {
            return Response.error(CODE_FORBIDDEN, "Users is blocked");
        }

        // if (!user.getIsActive()) {
        //     return Response.error(CODE_FORBIDDEN, "Users is not active");
        // }

        String token = user.getToken();
        TokenVO tokenVO = new TokenVO();
        tokenVO.setToken(token);
        return Response.success("Auth success", tokenVO);
    }


    @PostMapping("availability")
    @Operation(summary = "Check Username availability")
    public Response<Boolean> checkAvailability(@RequestBody NameRequestDTO nameRequestDTO) {
        boolean ret = authService.checkAvailability(nameRequestDTO);
        return Response.success("Check availability " + ret, ret);
    }

    @PostMapping("login")
    @Operation(summary = "Login")
    public Response<TokenVO> loginWithPassword(@RequestBody AuthRequestDTO authRequestDTO) {
        Users user = authService.loginWithPassword(authRequestDTO);
        return this.auth(user);
    }


    @PostMapping("login/code")
    @Operation(summary = "Login by code")
    public Response<TokenVO> loginByCode(@RequestBody AuthRequestDTO authRequestDTO) {
        Users user = authService.loginWithCode(authRequestDTO);

        if (user == null) {
            user = authService.registerWithCode(authRequestDTO);
        }

        return this.auth(user);
    }

    @PostMapping("register")
    @Operation(summary = "Register")
    public Response<TokenVO> registerWithPassword(@RequestBody AuthRequestDTO authRequestDTO) {
        Users user = authService.registerWithPassword(authRequestDTO);
        return this.auth(user);
    }

    @PostMapping("code")
    @Operation(summary = "Send code")
    public Response<String> sendCode(@RequestBody NameRequestDTO nameRequestDTO) {
        boolean ret = authService.sendCode(nameRequestDTO);
        if (!ret) {
            return Response.error("Send code failed");
        }
        return Response.success("Send code success");
    }
}