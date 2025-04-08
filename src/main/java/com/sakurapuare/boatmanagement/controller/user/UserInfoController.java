package com.sakurapuare.boatmanagement.controller.user;

import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.common.context.UserContext;
import com.sakurapuare.boatmanagement.pojo.entity.Accounts;
import com.sakurapuare.boatmanagement.pojo.vo.UserInfoVO;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseAccountsVO;
import com.sakurapuare.boatmanagement.service.AccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/info")
@Tag(name = "UserInfo", description = "用户信息模块")
@RequiredArgsConstructor
public class UserInfoController {

    private final AccountsService accountsService;

    @GetMapping("/{id}")
    @Operation(summary = "获取用户信息")
    public Response<UserInfoVO> userGetUser(@PathVariable Long id) {
        List<BaseAccountsVO> users = accountsService.adminGetUserByIds(id.toString());
        if (users.isEmpty()) {
            return Response.error("用户不存在");
        }
        
        // 转换为用户视图对象
        UserInfoVO userInfoVO = new UserInfoVO();
        BaseAccountsVO user = users.get(0);
        userInfoVO.setId(user.getId());
        userInfoVO.setUsername(user.getUsername());
        // 设置其他需要的属性...
        
        return Response.success(userInfoVO);
    }

    @GetMapping("/me")
    @Operation(summary = "获取当前用户信息")
    public Response<BaseAccountsVO> userGetCurrentUser() {
        Accounts account = UserContext.getAccount();
        List<BaseAccountsVO> users = accountsService.adminGetUserByIds(account.getId().toString());
        if (users.isEmpty()) {
            return Response.error("用户不存在");
        }
        return Response.success(users.get(0));
    }
}
