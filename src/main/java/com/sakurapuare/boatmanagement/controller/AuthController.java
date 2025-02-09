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

@RestController
@RequestMapping("/auth")
@Tag(name = "认证", description = "统一认证模块")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    public Response<TokenVO> auth(Accounts account) {
        if (account.getIsBlocked()) {
            return Response.error(CODE_FORBIDDEN, "账号已锁定"); // 不会走到这里的
        }

        String token = authService.generateToken(account);
        TokenVO tokenVO = new TokenVO();
        tokenVO.setToken(token);
        return Response.success("登录成功", tokenVO);
    }


    @PostMapping("availability")
    @Operation(summary = "检查用户名是否可用")
    public Response<Boolean> checkAvailability(@RequestBody NameRequestDTO nameRequestDTO) {
        boolean isAvailable = authService.checkAvailability(nameRequestDTO);
        if (!isAvailable) {
            return Response.error(CODE_FORBIDDEN, "用户名已存在");
        }
        return Response.success("用户名可用", isAvailable);
    }

    @PostMapping("login")
    @Operation(summary = "密码登录")
    public Response<TokenVO> loginWithPassword(@RequestBody AuthRequestDTO authRequestDTO) {
        Accounts account = authService.loginWithPassword(authRequestDTO);
        return this.auth(account);
    }


    @PostMapping("login/code")
    @Operation(summary = "验证码登录")
    public Response<TokenVO> loginByCode(@RequestBody AuthRequestDTO authRequestDTO) {
        Accounts account = authService.authWithCode(authRequestDTO);
        return this.auth(account);
    }

    @PostMapping("login/wechat")
    @Operation(summary = "微信登录")
    public Response<TokenVO> loginByWechat(@RequestBody WxLoginDTO wxLoginDTO) {
        Accounts account = authService.loginWithWechat(wxLoginDTO);
        return this.auth(account);
    }


    @PostMapping("register")
    @Operation(summary = "密码注册")
    public Response<TokenVO> registerWithPassword(@RequestBody AuthRequestDTO authRequestDTO) {
        Accounts account = authService.registerWithPassword(authRequestDTO);
        return this.auth(account);
    }

    @PostMapping("code")
    @Operation(summary = "发送验证码")
    public Response<String> sendCode(@RequestBody NameRequestDTO nameRequestDTO) {
        authService.sendCode(nameRequestDTO);
        return Response.success("验证码发送成功");
    }

    @PostMapping("wx/login")
    @Operation(summary = "微信登录")
    public Response<TokenVO> wxLogin(@RequestBody WxLoginDTO wxLoginDTO) {
        Accounts account = authService.loginWithWechat(wxLoginDTO);
        return this.auth(account);
    }
}