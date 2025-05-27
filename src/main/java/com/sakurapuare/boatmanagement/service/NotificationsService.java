package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseNotificationsDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Notifications;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseNotificationsVO;
import com.sakurapuare.boatmanagement.service.base.BaseNotificationsService;
import com.sakurapuare.boatmanagement.utils.POJOUtils;
import com.sakurapuare.boatmanagement.utils.ParamsUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.NOTIFICATIONS;

import com.mybatisflex.core.query.QueryColumn;

/**
 * 通知服务
 */
@Service
public class NotificationsService extends BaseNotificationsService {

    /**
     * 搜索字段
     */
    private static final QueryColumn[] SEARCH_FIELDS = {
            NOTIFICATIONS.TITLE,
            NOTIFICATIONS.CONTENT,
            NOTIFICATIONS.TYPE,
            NOTIFICATIONS.BUSINESS_TYPE
    };

    /**
     * 管理员获取通知列表
     *
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 通知视图对象列表
     */
    public List<BaseNotificationsVO> adminGetNotificationsList(
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseNotificationsDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, Notifications.class);
        adminParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.listAs(queryWrapper, BaseNotificationsVO.class);
    }

    /**
     * 管理员分页获取通知列表
     *
     * @param pageNum       页码
     * @param pageSize      每页大小
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 分页通知视图对象
     */
    public Page<BaseNotificationsVO> adminGetNotificationsPage(
            Integer pageNum,
            Integer pageSize,
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseNotificationsDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, Notifications.class);
        adminParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.pageAs(
                Page.of(pageNum, pageSize), queryWrapper, BaseNotificationsVO.class);
    }

    /**
     * 管理员根据 ID 获取通知
     *
     * @param ids 通知 ID 字符串，多个 ID 用逗号分隔
     * @return 通知视图对象列表
     * @throws RuntimeException 当通知不存在时抛出
     */
    public List<BaseNotificationsVO> adminGetNotificationsByIds(String ids) {
        List<Long> idList = ParamsUtils.getListFromIds(ids);

        if (idList.isEmpty()) {
            throw new RuntimeException("通知不存在");
        }

        return POJOUtils.getListFromIds(idList, this::getNotificationInfo);
    }

    /**
     * 管理员创建通知
     *
     * @param notificationDTO 通知数据传输对象
     * @return 通知视图对象
     * @throws RuntimeException 当创建失败时抛出
     */
    public BaseNotificationsVO adminCreateNotification(BaseNotificationsDTO notificationDTO) {
        Notifications notification = POJOUtils.convertToEntity(notificationDTO, Notifications.class);

        if (!super.save(notification)) {
            throw new RuntimeException("通知创建失败");
        }

        return getNotificationInfo(notification.getId());
    }

    /**
     * 管理员更新通知
     *
     * @param id              通知 ID
     * @param notificationDTO 通知数据传输对象
     * @return 通知视图对象
     * @throws RuntimeException 当通知不存在或更新失败时抛出
     */
    public BaseNotificationsVO adminUpdateNotification(Long id, BaseNotificationsDTO notificationDTO) {
        Notifications existingNotification = super.getById(id);
        if (existingNotification == null) {
            throw new RuntimeException("通知不存在");
        }

        Notifications notification = POJOUtils.convertToEntity(notificationDTO, Notifications.class);
        notification.setId(id);

        if (!super.updateById(notification)) {
            throw new RuntimeException("通知更新失败");
        }

        return getNotificationInfo(id);
    }

    /**
     * 管理员删除通知
     *
     * @param id 通知 ID
     * @throws RuntimeException 当通知不存在或删除失败时抛出
     */
    public void adminDeleteNotification(Long id) {
        Notifications existingNotification = super.getById(id);
        if (existingNotification == null) {
            throw new RuntimeException("通知不存在");
        }

        if (!super.removeById(id)) {
            throw new RuntimeException("通知删除失败");
        }
    }

    /**
     * 用户获取通知列表
     *
     * @param userId        用户ID
     * @param isRead        是否已读
     * @param type          通知类型
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 通知视图对象列表
     */
    public List<BaseNotificationsVO> userGetNotificationsList(
            Long userId,
            Boolean isRead,
            String type,
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseNotificationsDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, Notifications.class);
        queryWrapper.and(NOTIFICATIONS.USER_ID.eq(userId));
        
        if (isRead != null) {
            queryWrapper.and(NOTIFICATIONS.IS_READ.eq(isRead));
        }
        
        if (type != null && !type.isEmpty()) {
            queryWrapper.and(NOTIFICATIONS.TYPE.eq(type));
        }
        
        userParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.listAs(queryWrapper, BaseNotificationsVO.class);
    }

    /**
     * 用户分页获取通知列表
     *
     * @param userId        用户ID
     * @param pageNum       页码
     * @param pageSize      每页大小
     * @param isRead        是否已读
     * @param type          通知类型
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 分页通知视图对象
     */
    public Page<BaseNotificationsVO> userGetNotificationsPage(
            Long userId,
            Integer pageNum,
            Integer pageSize,
            Boolean isRead,
            String type,
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseNotificationsDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, Notifications.class);
        queryWrapper.and(NOTIFICATIONS.USER_ID.eq(userId));
        
        if (isRead != null) {
            queryWrapper.and(NOTIFICATIONS.IS_READ.eq(isRead));
        }
        
        if (type != null && !type.isEmpty()) {
            queryWrapper.and(NOTIFICATIONS.TYPE.eq(type));
        }
        
        userParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.pageAs(
                Page.of(pageNum, pageSize), queryWrapper, BaseNotificationsVO.class);
    }

    /**
     * 发送通知
     *
     * @param userId       接收用户ID
     * @param title        通知标题
     * @param content      通知内容
     * @param type         通知类型
     * @param businessType 业务类型
     * @param businessId   业务ID
     * @return 通知视图对象
     */
    public BaseNotificationsVO sendNotification(
            Long userId,
            String title,
            String content,
            String type,
            String businessType,
            Long businessId) {
        Notifications notification = Notifications.builder()
                .userId(userId)
                .title(title)
                .content(content)
                .type(type)
                .businessType(businessType)
                .businessId(businessId)
                .isRead(false)
                .build();

        if (!super.save(notification)) {
            throw new RuntimeException("通知发送失败");
        }

        return getNotificationInfo(notification.getId());
    }

    /**
     * 用户标记通知为已读
     *
     * @param userId         用户ID
     * @param notificationId 通知ID
     * @throws RuntimeException 当通知不存在或不属于该用户时抛出
     */
    public void userMarkAsRead(Long userId, Long notificationId) {
        Notifications notification = super.getById(notificationId);
        if (notification == null) {
            throw new RuntimeException("通知不存在");
        }

        if (!notification.getUserId().equals(userId)) {
            throw new RuntimeException("无权限操作此通知");
        }

        notification.setIsRead(true);
        if (!super.updateById(notification)) {
            throw new RuntimeException("标记已读失败");
        }
    }

    /**
     * 用户批量标记通知为已读
     *
     * @param userId          用户ID
     * @param notificationIds 通知ID列表
     */
    public void userMarkMultipleAsRead(Long userId, List<Long> notificationIds) {
        if (notificationIds == null || notificationIds.isEmpty()) {
            return;
        }

        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(NOTIFICATIONS.ID.in(notificationIds))
                .and(NOTIFICATIONS.USER_ID.eq(userId))
                .and(NOTIFICATIONS.IS_READ.eq(false));

        Notifications updateNotification = new Notifications();
        updateNotification.setIsRead(true);

        super.update(updateNotification, queryWrapper);
    }

    /**
     * 获取用户未读通知数量
     *
     * @param userId 用户ID
     * @return 未读通知数量
     */
    public long getUserUnreadCount(Long userId) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(NOTIFICATIONS.USER_ID.eq(userId))
                .and(NOTIFICATIONS.IS_READ.eq(false))
                .and(NOTIFICATIONS.IS_DELETED.eq(false));

        return super.count(queryWrapper);
    }

    /**
     * 根据业务类型和业务ID获取通知列表
     *
     * @param businessType 业务类型
     * @param businessId   业务ID
     * @return 通知视图对象列表
     */
    public List<BaseNotificationsVO> getNotificationsByBusiness(String businessType, Long businessId) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(NOTIFICATIONS.BUSINESS_TYPE.eq(businessType))
                .and(NOTIFICATIONS.BUSINESS_ID.eq(businessId))
                .and(NOTIFICATIONS.IS_DELETED.eq(false));
        return super.listAs(queryWrapper, BaseNotificationsVO.class);
    }

    /**
     * 获取通知信息
     *
     * @param id 通知 ID
     * @return 通知视图对象
     * @throws RuntimeException 当通知不存在时抛出
     */
    public BaseNotificationsVO getNotificationInfo(Long id) {
        Notifications notification = super.getById(id);
        if (notification == null) {
            throw new RuntimeException("通知不存在");
        }
        return POJOUtils.convertToVO(notification, BaseNotificationsVO.class);
    }

    /**
     * 管理员解析参数
     *
     * @param queryWrapper  查询包装器
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     */
    private void adminParseParams(QueryWrapper queryWrapper, String search, String sort, String startDateTime, String endDateTime) {
        POJOUtils.search(queryWrapper, SEARCH_FIELDS, search);
        POJOUtils.dateRange(queryWrapper, startDateTime, endDateTime);
        POJOUtils.sort(queryWrapper, ParamsUtils.getSortFromParams(sort));
    }

    /**
     * 用户解析参数
     *
     * @param queryWrapper  查询包装器
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     */
    private void userParseParams(QueryWrapper queryWrapper, String search, String sort, String startDateTime, String endDateTime) {
        QueryColumn[] userSearchFields = {NOTIFICATIONS.TITLE, NOTIFICATIONS.CONTENT};
        POJOUtils.search(queryWrapper, userSearchFields, search);
        POJOUtils.dateRange(queryWrapper, startDateTime, endDateTime);
        POJOUtils.sort(queryWrapper, ParamsUtils.getSortFromParams(sort));
    }
} 