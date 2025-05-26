package com.sakurapuare.boatmanagement.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class DuplicateSubmissionService {

    private final ConcurrentHashMap<String, Long> processingRequests = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    
    // 请求超时时间（毫秒）
    private static final long REQUEST_TIMEOUT = 10000; // 10秒

    public DuplicateSubmissionService() {
        // 定期清理过期的请求记录
        scheduler.scheduleAtFixedRate(this::cleanupExpiredRequests, 30, 30, TimeUnit.SECONDS);
    }

    /**
     * 检查并标记请求为正在处理
     * @param key 请求的唯一标识（如用户名）
     * @return true 如果可以处理，false 如果正在处理中
     */
    public boolean tryLock(String key) {
        long currentTime = System.currentTimeMillis();
        Long existingTime = processingRequests.putIfAbsent(key, currentTime);
        
        if (existingTime == null) {
            log.info("DuplicateSubmissionService.tryLock - 新请求已锁定: {}", key);
            return true; // 成功获取锁
        }
        
        // 检查是否超时
        if (currentTime - existingTime > REQUEST_TIMEOUT) {
            log.warn("DuplicateSubmissionService.tryLock - 请求超时，重新获取锁: {}", key);
            processingRequests.put(key, currentTime);
            return true;
        }
        
        log.warn("DuplicateSubmissionService.tryLock - 请求正在处理中，拒绝重复提交: {}", key);
        return false; // 正在处理中
    }

    /**
     * 释放请求锁
     * @param key 请求的唯一标识
     */
    public void unlock(String key) {
        processingRequests.remove(key);
        log.info("DuplicateSubmissionService.unlock - 请求锁已释放: {}", key);
    }

    /**
     * 清理过期的请求记录
     */
    private void cleanupExpiredRequests() {
        long currentTime = System.currentTimeMillis();
        AtomicInteger removedCount = new AtomicInteger();
        
        processingRequests.entrySet().removeIf(entry -> {
            if (currentTime - entry.getValue() > REQUEST_TIMEOUT) {
                removedCount.getAndIncrement();
                return true;
            }
            return false;
        });
        
        if (removedCount.get() > 0) {
            log.info("DuplicateSubmissionService.cleanupExpiredRequests - 清理了 {} 个过期请求", removedCount);
        }
    }

    /**
     * 获取当前正在处理的请求数量
     */
    public int getProcessingRequestCount() {
        return processingRequests.size();
    }
} 