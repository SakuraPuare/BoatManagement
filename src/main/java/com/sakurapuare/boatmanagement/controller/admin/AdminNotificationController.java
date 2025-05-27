package com.sakurapuare.boatmanagement.controller.admin;

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
 * 管理员通知管理控制器
 */
@Api(tags = "管理员通知管理")
@RestController
@RequestMapping("/admin/notifications")
public class AdminNotificationController {

    @Autowired
    private NotificationsService notificationsService;

    /**
     * 获取通知列表
     */
    @ApiOperation("获取通知列表")
    @PostMapping("/list")
    public Response<List<BaseNotificationsVO>> getNotificationsList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseNotificationsDTO filter) {
        List<BaseNotificationsVO> result = notificationsService.adminGetNotificationsList(
                search, sort, startDateTime, endDateTime, filter);
        return Response.success(result);
    }

    /**
     * 分页获取通知列表
     */
    @ApiOperation("分页获取通知列表")
    @PostMapping("/page")
    public Response<Page<BaseNotificationsVO>> getNotificationsPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseNotificationsDTO filter) {
        Page<BaseNotificationsVO> result = notificationsService.adminGetNotificationsPage(
                pageNum, pageSize, search, sort, startDateTime, endDateTime, filter);
        return Response.success(result);
    }

    /**
     * 根据ID获取通知
     */
    @ApiOperation("根据ID获取通知")
    @GetMapping("/ids")
    public Response<List<BaseNotificationsVO>> getNotificationsByIds(@RequestParam String ids) {
        List<BaseNotificationsVO> result = notificationsService.adminGetNotificationsByIds(ids);
        return Response.success(result);
    }

    /**
     * 创建通知
     */
    @ApiOperation("创建通知")
    @PostMapping
    public Response<BaseNotificationsVO> createNotification(@RequestBody BaseNotificationsDTO notificationDTO) {
        BaseNotificationsVO result = notificationsService.adminCreateNotification(notificationDTO);
        return Response.success(result);
    }

    /**
     * 更新通知
     */
    @ApiOperation("更新通知")
    @PutMapping("/{id}")
    public Response<BaseNotificationsVO> updateNotification(
            @PathVariable Long id,
            @RequestBody BaseNotificationsDTO notificationDTO) {
        BaseNotificationsVO result = notificationsService.adminUpdateNotification(id, notificationDTO);
        return Response.success(result);
    }

    /**
     * 删除通知
     */
    @ApiOperation("删除通知")
    @DeleteMapping("/{id}")
    public Response<Void> deleteNotification(@PathVariable Long id) {
        notificationsService.adminDeleteNotification(id);
        return Response.success();
    }

    /**
     * 发送通知
     */
    @ApiOperation("发送通知")
    @PostMapping("/send")
    public Response<BaseNotificationsVO> sendNotification(
            @RequestParam Long userId,
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam String type,
            @RequestParam(required = false) String businessType,
            @RequestParam(required = false) Long businessId) {
        BaseNotificationsVO result = notificationsService.sendNotification(
                userId, title, content, type, businessType, businessId);
        return Response.success(result);
    }
} 