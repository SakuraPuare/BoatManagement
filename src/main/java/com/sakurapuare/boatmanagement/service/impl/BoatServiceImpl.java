package com.sakurapuare.boatmanagement.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sakurapuare.boatmanagement.mapper.BoatMapper;
import com.sakurapuare.boatmanagement.pojo.entity.Boat;
import com.sakurapuare.boatmanagement.service.BoatService;
import org.springframework.stereotype.Service;

/**
 * 服务层实现。
 *
 * @author sakurapuare
 * @since 2024-11-15
 */
@Service
public class BoatServiceImpl extends ServiceImpl<BoatMapper, Boat> implements BoatService {

}
