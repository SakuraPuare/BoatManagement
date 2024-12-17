package com.sakurapuare.boatmanagement.controller;

import cn.hutool.core.bean.BeanUtil;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.common.UserContext;
import com.sakurapuare.boatmanagement.pojo.entity.Users;
import com.sakurapuare.boatmanagement.pojo.vo.UserSelfVO;
import com.sakurapuare.boatmanagement.pojo.vo.UserVO;
import com.sakurapuare.boatmanagement.service.UsersService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Tag(name = "Users", description = "Users")
public class UserController {

    private final UsersService userService;

    public UserController(UsersService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public Response<UserVO> getUser(@PathVariable Long id) {
        Users user = userService.getById(id);
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user, userVO);
        return Response.success(userVO);
    }

    @GetMapping("/me")
    public Response<UserSelfVO> getMe() {
        Users user = UserContext.getUser();

        UserSelfVO userSelfVO = new UserSelfVO();
        BeanUtil.copyProperties(user, userSelfVO);

        return Response.success(userSelfVO);
    }
}
