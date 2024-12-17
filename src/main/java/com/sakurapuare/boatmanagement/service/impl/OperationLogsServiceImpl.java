package com.sakurapuare.boatmanagement.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sakurapuare.boatmanagement.mapper.OperationLogsMapper;
import com.sakurapuare.boatmanagement.pojo.entity.OperationLogs;
import com.sakurapuare.boatmanagement.service.OperationLogsService;
import org.springframework.stereotype.Service;

/**
 * 操作日志表 服务层实现。
 *
 * @author sakurapuare
 * @since 2024-12-17
 */
@Service
public class OperationLogsServiceImpl extends ServiceImpl<OperationLogsMapper, OperationLogs> implements OperationLogsService {

}
