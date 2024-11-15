package com.sakurapuare.boatmanagement.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sakurapuare.boatmanagement.mapper.TicketsMapper;
import com.sakurapuare.boatmanagement.pojo.entity.Ticket;
import com.sakurapuare.boatmanagement.service.TicketsService;
import org.springframework.stereotype.Service;

/**
 * 服务层实现。
 *
 * @author sakurapuare
 * @since 2024-11-15
 */
@Service
public class TicketsServiceImpl extends ServiceImpl<TicketsMapper, Ticket> implements TicketsService {

}
