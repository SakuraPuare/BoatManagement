package com.sakurapuare.boatmanagement.controller.user;

import cn.hutool.core.bean.BeanUtil;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.common.UserContext;
import com.sakurapuare.boatmanagement.pojo.entity.Accounts;
import com.sakurapuare.boatmanagement.pojo.vo.UserInfoVO;
import com.sakurapuare.boatmanagement.pojo.vo.UserPersonalInfoVO;
import com.sakurapuare.boatmanagement.service.AccountsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/info")
@Tag(name = "UserInfo", description = "用户模块")
@RequiredArgsConstructor
public class UserInfoController {

    private final AccountsService accountsService;

    @GetMapping("/{id}")
    public Response<UserInfoVO> getUser(@PathVariable Long id) {
        Accounts account = accountsService.getById(id);
        UserInfoVO userVO = new UserInfoVO();
        BeanUtil.copyProperties(account, userVO);
        return Response.success(userVO);
    }

    @GetMapping("/me")
    public Response<UserPersonalInfoVO> getMe() {
        Accounts account = UserContext.getAccount();

        UserPersonalInfoVO userSelfVO = new UserPersonalInfoVO();
        BeanUtil.copyProperties(account, userSelfVO);

        return Response.success(userSelfVO);
    }
}
