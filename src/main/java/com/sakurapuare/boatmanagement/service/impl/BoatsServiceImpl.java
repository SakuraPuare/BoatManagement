package com.sakurapuare.boatmanagement.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sakurapuare.boatmanagement.mapper.BoatsMapper;
import com.sakurapuare.boatmanagement.pojo.entity.Boats;
import com.sakurapuare.boatmanagement.service.BoatsService;
import org.springframework.stereotype.Service;

/**
 * 服务层实现。
 *
 * @author sakurapuare
 * @since 2024-11-15
 */
@Service
public class BoatsServiceImpl extends ServiceImpl<BoatsMapper, Boats> implements BoatsService {

}
