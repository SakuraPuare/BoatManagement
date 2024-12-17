package com.sakurapuare.boatmanagement.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sakurapuare.boatmanagement.mapper.TicketsMapper;
import com.sakurapuare.boatmanagement.pojo.entity.Tickets;
import com.sakurapuare.boatmanagement.service.TicketsService;
import org.springframework.stereotype.Service;

/**
 * 船票表 服务层实现。
 *
 * @author sakurapuare
 * @since 2024-12-17
 */
@Service
public class TicketsServiceImpl extends ServiceImpl<TicketsMapper, Tickets> implements TicketsService {

}
