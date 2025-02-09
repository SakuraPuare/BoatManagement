package com.sakurapuare.boatmanagement.controller;

import cn.hutool.core.bean.BeanUtil;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.common.UserContext;
import com.sakurapuare.boatmanagement.pojo.entity.Accounts;
import com.sakurapuare.boatmanagement.pojo.vo.UserSelfVO;
import com.sakurapuare.boatmanagement.pojo.vo.UserVO;
import com.sakurapuare.boatmanagement.service.AccountsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Tag(name = "Users", description = "Users")
public class UserController {

    private final AccountsService accountsService;

    public UserController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @GetMapping("/{id}")
    public Response<UserVO> getUser(@PathVariable Long id) {
        Accounts account = accountsService.getById(id);
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(account, userVO);
        return Response.success(userVO);
    }

    @GetMapping("/me")
    public Response<UserSelfVO> getMe() {
        Accounts account = UserContext.getAccount();
        
        UserSelfVO userSelfVO = new UserSelfVO();
        BeanUtil.copyProperties(account, userSelfVO);
        
        return Response.success(userSelfVO);
    }
}
