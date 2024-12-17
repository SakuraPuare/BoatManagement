package com.sakurapuare.boatmanagement.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sakurapuare.boatmanagement.mapper.OrdersMapper;
import com.sakurapuare.boatmanagement.pojo.entity.Orders;
import com.sakurapuare.boatmanagement.service.OrdersService;
import org.springframework.stereotype.Service;

/**
 * 订单表 服务层实现。
 *
 * @author sakurapuare
 * @since 2024-12-17
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

}
