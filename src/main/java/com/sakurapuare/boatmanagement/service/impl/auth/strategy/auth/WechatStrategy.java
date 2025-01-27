package com.sakurapuare.boatmanagement.service.impl.auth.strategy.auth;

import com.sakurapuare.boatmanagement.mapper.UsersMapper;
import com.sakurapuare.boatmanagement.pojo.dto.AuthRequestDTO;
import com.sakurapuare.boatmanagement.pojo.dto.WxUserInfo;
import com.sakurapuare.boatmanagement.pojo.entity.Users;
import com.sakurapuare.boatmanagement.utils.WechatUtils;
import org.springframework.stereotype.Component;

@Component
public class WechatStrategy extends AuthStrategy {
    private final WechatUtils wechatUtils;
    private final UsersMapper userMapper;

    public WechatStrategy(WechatUtils wechatUtils, UsersMapper userMapper) {
        super(userMapper);  // 调用父类构造函数
        this.wechatUtils = wechatUtils;
        this.userMapper = userMapper;
    }

    @Override
    public Users auth(AuthRequestDTO authRequestDTO) {
        // 1. 通过code获取openid
        String openid = wechatUtils.getOpenId(authRequestDTO.getCode());

        // 2. 查找或创建用户
        Users user = userMapper.selectOneByOpenid(openid);
        if (user == null) {
            // 创建新用户
            user = createUser(authRequestDTO.getUserInfo(), openid);
            userMapper.insert(user);
        }

        return user;
    }

    private Users createUser(WxUserInfo userInfo, String openid) {
        return Users.builder()
                .openid(openid)
                .username(userInfo.getNickName())
                .avatar(userInfo.getAvatarUrl())
                .isActive(true)
                .isBlocked(false)
                .build();
    }
} 