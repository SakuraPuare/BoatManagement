package com.sakurapuare.boatmanagement.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sakurapuare.boatmanagement.mapper.DockMapper;
import com.sakurapuare.boatmanagement.pojo.entity.Dock;
import com.sakurapuare.boatmanagement.service.DockService;
import org.springframework.stereotype.Service;

/**
 * 服务层实现。
 *
 * @author sakurapuare
 * @since 2024-11-15
 */
@Service
public class DockServiceImpl extends ServiceImpl<DockMapper, Dock> implements DockService {

}
