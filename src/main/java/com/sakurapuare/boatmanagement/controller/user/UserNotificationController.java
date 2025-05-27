package com.sakurapuare.boatmanagement.controller.user;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseNotificationsDTO;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseNotificationsVO;
import com.sakurapuare.boatmanagement.service.NotificationsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户通知控制器
 */
@Api(tags = "用户通知管理")
@RestController
@RequestMapping("/user/notifications")
public class UserNotificationController {

    @Autowired
    private NotificationsService notificationsService;

    /**
     * 获取用户通知列表
     */
    @ApiOperation("获取用户通知列表")
    @PostMapping("/list")
    public Response<List<BaseNotificationsVO>> getNotificationsList(
            @RequestParam Long userId,
            @RequestParam(required = false) Boolean isRead,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseNotificationsDTO filter) {
        List<BaseNotificationsVO> result = notificationsService.userGetNotificationsList(
                userId, isRead, type, search, sort, startDateTime, endDateTime, filter);
        return Response.success(result);
    }

    /**
     * 分页获取用户通知列表
     */
    @ApiOperation("分页获取用户通知列表")
    @PostMapping("/page")
    public Response<Page<BaseNotificationsVO>> getNotificationsPage(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Boolean isRead,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseNotificationsDTO filter) {
        Page<BaseNotificationsVO> result = notificationsService.userGetNotificationsPage(
                userId, pageNum, pageSize, isRead, type, search, sort, startDateTime, endDateTime, filter);
        return Response.success(result);
    }

    /**
     * 标记通知为已读
     */
    @ApiOperation("标记通知为已读")
    @PutMapping("/{notificationId}/read")
    public Response<Void> markAsRead(
            @RequestParam Long userId,
            @PathVariable Long notificationId) {
        notificationsService.userMarkAsRead(userId, notificationId);
        return Response.success();
    }

    /**
     * 批量标记通知为已读
     */
    @ApiOperation("批量标记通知为已读")
    @PutMapping("/batch-read")
    public Response<Void> markMultipleAsRead(
            @RequestParam Long userId,
            @RequestBody List<Long> notificationIds) {
        notificationsService.userMarkMultipleAsRead(userId, notificationIds);
        return Response.success();
    }

    /**
     * 获取用户未读通知数量
     */
    @ApiOperation("获取用户未读通知数量")
    @GetMapping("/unread-count")
    public Response<Long> getUnreadCount(@RequestParam Long userId) {
        long count = notificationsService.getUserUnreadCount(userId);
        return Response.success(count);
    }
} 