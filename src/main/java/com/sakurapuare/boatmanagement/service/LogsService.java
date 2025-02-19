package com.sakurapuare.boatmanagement.service;

import com.sakurapuare.boatmanagement.common.context.UserContext;
import com.sakurapuare.boatmanagement.constant.LogLevel;
import com.sakurapuare.boatmanagement.pojo.entity.Logs;
import com.sakurapuare.boatmanagement.service.base.BaseLogsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogsService extends BaseLogsService {

    private void log(String level, String types, String content) {
        Logs logs = new Logs();
        logs.setLevel(level);
        logs.setType(types);
        logs.setContent(content);
        logs.setOperatorId(UserContext.getAccount().getId());
        save(logs);
    }

    public void info(String types, String content) {
        log(LogLevel.INFO, types, content);
    }

    public void warning(String types, String content) {
        log(LogLevel.WARNING, types, content);
    }

    public void error(String types, String content) {
        log(LogLevel.ERROR, types, content);
    }
}
