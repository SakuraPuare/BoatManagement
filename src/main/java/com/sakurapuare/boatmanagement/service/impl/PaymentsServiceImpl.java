package com.sakurapuare.boatmanagement.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sakurapuare.boatmanagement.mapper.PaymentsMapper;
import com.sakurapuare.boatmanagement.pojo.entity.Payments;
import com.sakurapuare.boatmanagement.service.PaymentsService;
import org.springframework.stereotype.Service;

/**
 * 支付记录表 服务层实现。
 *
 * @author sakurapuare
 * @since 2024-12-17
 */
@Service
public class PaymentsServiceImpl extends ServiceImpl<PaymentsMapper, Payments> implements PaymentsService {

}
