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
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sakurapuare.boatmanagement.constant.ResponseCode.CODE_FORBIDDEN;

@RestController
@RequestMapping("/auth")
@Tag(name = "AuthController", description = "统一认证模块")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 生成认证令牌
     *
     * @param account 账号信息
     * @return 令牌视图对象
     */
    private Response<TokenVO> generateAuthToken(Accounts account) {
        if (account.getIsBlocked()) {
            return Response.error(CODE_FORBIDDEN, "账号已锁定");
        }

        String token = authService.generateToken(account);
        TokenVO tokenVO = new TokenVO();
        tokenVO.setToken(token);
        return Response.success(tokenVO);
    }

    @PostMapping("/availability")
    @Operation(summary = "检查用户名是否可用")
    public Response<Boolean> checkAvailability(@RequestBody NameRequestDTO nameRequestDTO) {
        boolean isAvailable = authService.checkAvailability(nameRequestDTO);
        if (!isAvailable) {
            return Response.error(CODE_FORBIDDEN, "用户名已存在");
        }
        return Response.success(true);
    }

    @PostMapping("/login")
    @Operation(summary = "密码登录")
    public Response<TokenVO> loginWithPassword(@RequestBody AuthRequestDTO authRequestDTO) {
        Accounts account = authService.loginWithPassword(authRequestDTO);
        return this.generateAuthToken(account);
    }

    @PostMapping("/login/code")
    @Operation(summary = "验证码登录")
    public Response<TokenVO> loginByCode(@RequestBody AuthRequestDTO authRequestDTO) {
        Accounts account = authService.authWithCode(authRequestDTO);
        return this.generateAuthToken(account);
    }

    @PostMapping("/login/wechat")
    @Operation(summary = "微信登录")
    public Response<TokenVO> loginByWechat(@RequestBody WxLoginDTO wxLoginDTO) {
        Accounts account = authService.loginWithWechat(wxLoginDTO);
        return this.generateAuthToken(account);
    }

    @PostMapping("/register")
    @Operation(summary = "密码注册")
    public Response<TokenVO> registerWithPassword(@RequestBody AuthRequestDTO authRequestDTO) {
        Accounts account = authService.registerWithPassword(authRequestDTO);
        return this.generateAuthToken(account);
    }

    @PostMapping("/code")
    @Operation(summary = "发送验证码")
    public Response<Void> sendCode(@RequestBody NameRequestDTO nameRequestDTO) {
        authService.sendCode(nameRequestDTO);
        return Response.success();
    }
}