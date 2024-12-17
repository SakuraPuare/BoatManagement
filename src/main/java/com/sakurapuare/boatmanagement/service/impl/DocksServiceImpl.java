package com.sakurapuare.boatmanagement.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sakurapuare.boatmanagement.mapper.DocksMapper;
import com.sakurapuare.boatmanagement.pojo.entity.Docks;
import com.sakurapuare.boatmanagement.service.DocksService;
import org.springframework.stereotype.Service;

/**
 * 码头表 服务层实现。
 *
 * @author sakurapuare
 * @since 2024-12-17
 */
@Service
public class DocksServiceImpl extends ServiceImpl<DocksMapper, Docks> implements DocksService {

}
