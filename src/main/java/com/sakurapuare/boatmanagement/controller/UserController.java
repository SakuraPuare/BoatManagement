package com.sakurapuare.boatmanagement.controller;

import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.vo.UserVO;
import com.sakurapuare.boatmanagement.pojo.vo.UserSelfVO;
import com.sakurapuare.boatmanagement.service.UserService;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sakurapuare.boatmanagement.pojo.entity.User;
@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "User")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public Response<UserVO> getUser(@PathVariable Long id) {
        User user = userService.getById(id);
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user, userVO);
        return Response.success(userVO);
    }

    @GetMapping("/me")
    public Response<UserSelfVO> getMe() {
        User user = userService.getCurrentUser();

        UserSelfVO userSelfVO = new UserSelfVO();
        BeanUtil.copyProperties(user, userSelfVO);

        return Response.success(userSelfVO);
    }
}
