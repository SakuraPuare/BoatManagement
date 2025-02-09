package com.sakurapuare.boatmanagement.controller;

import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.AuthRequestDTO;
import com.sakurapuare.boatmanagement.pojo.dto.NameRequestDTO;
import com.sakurapuare.boatmanagement.pojo.dto.WxLoginDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Accounts;
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

    public Response<TokenVO> auth(Accounts account) {
        if (account == null) {
            return Response.error(CODE_UNAUTHORIZED, "Auth failed");
        }

        if (account.getIsBlocked()) {
            return Response.error(CODE_FORBIDDEN, "Account is blocked");
        }

        String token = authService.generateToken(account);
        TokenVO tokenVO = new TokenVO();
        tokenVO.setToken(token);
        return Response.success("Auth success", tokenVO);
    }


    @PostMapping("availability")
    @Operation(summary = "Check Username availability")
    public Response<Boolean> checkAvailability(@RequestBody NameRequestDTO nameRequestDTO) {
        boolean ret = authService.checkAvailability(nameRequestDTO);
        return Response.success("Username is available", ret);
    }

    @PostMapping("login")
    @Operation(summary = "Login")
    public Response<TokenVO> loginWithPassword(@RequestBody AuthRequestDTO authRequestDTO) {
        Accounts account = authService.loginWithPassword(authRequestDTO);
        return this.auth(account);
    }


    @PostMapping("login/code")
    @Operation(summary = "Login by code")
    public Response<TokenVO> loginByCode(@RequestBody AuthRequestDTO authRequestDTO) {
        Accounts account = authService.loginWithCode(authRequestDTO);

        if (account == null) {
            account = authService.registerWithCode(authRequestDTO);
        }

        return this.auth(account);
    }

    @PostMapping("login/wechat")
    @Operation(summary = "Login by wechat")
    public Response<TokenVO> loginByWechat(@RequestBody WxLoginDTO wxLoginDTO) {
        Accounts account = authService.loginWithWechat(wxLoginDTO);
        return this.auth(account);
    }


    @PostMapping("register")
    @Operation(summary = "Register")
    public Response<TokenVO> registerWithPassword(@RequestBody AuthRequestDTO authRequestDTO) {
        Accounts account = authService.registerWithPassword(authRequestDTO);
        return this.auth(account);
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

    @PostMapping("wx/login")
    @Operation(summary = "微信登录")
    public Response<TokenVO> wxLogin(@RequestBody WxLoginDTO wxLoginDTO) {
        Accounts account = authService.loginWithWechat(wxLoginDTO);
        return this.auth(account);
    }
}