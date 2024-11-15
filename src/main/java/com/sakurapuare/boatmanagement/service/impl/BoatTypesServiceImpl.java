package com.sakurapuare.boatmanagement.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sakurapuare.boatmanagement.mapper.BoatTypesMapper;
import com.sakurapuare.boatmanagement.pojo.entity.BoatType;
import com.sakurapuare.boatmanagement.service.BoatTypesService;
import org.springframework.stereotype.Service;

/**
 * 服务层实现。
 *
 * @author sakurapuare
 * @since 2024-11-15
 */
@Service
public class BoatTypesServiceImpl extends ServiceImpl<BoatTypesMapper, BoatType> implements BoatTypesService {

}
