package com.sakurapuare.boatmanagement.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sakurapuare.boatmanagement.mapper.UsersMapper;
import com.sakurapuare.boatmanagement.pojo.entity.User;
import com.sakurapuare.boatmanagement.service.UsersService;
import org.springframework.stereotype.Service;

/**
 * 服务层实现。
 *
 * @author sakurapuare
 * @since 2024-11-15
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, User> implements UsersService {

}
