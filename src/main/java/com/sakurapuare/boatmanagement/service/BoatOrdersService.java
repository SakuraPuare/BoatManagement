package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.common.context.UserContext;
import com.sakurapuare.boatmanagement.constant.OrderStatus;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseBoatOrdersDTO;
import com.sakurapuare.boatmanagement.pojo.entity.BoatOrders;
import com.sakurapuare.boatmanagement.pojo.entity.BoatRequests;
import com.sakurapuare.boatmanagement.pojo.entity.BoatTypes;
import com.sakurapuare.boatmanagement.pojo.entity.Boats;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseBoatOrdersVO;
import com.sakurapuare.boatmanagement.service.base.BaseBoatOrdersService;
import com.sakurapuare.boatmanagement.utils.POJOUtils;
import com.sakurapuare.boatmanagement.utils.ParamsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.BOAT_ORDERS;

@Service
@RequiredArgsConstructor
public class BoatOrdersService extends BaseBoatOrdersService {

    private final BoatRequestsService boatRequestsService;
    private final BoatsService boatService;
    private final BoatTypesService boatTypeService;

    /**
     * 管理员搜索字段
     */
    private static final QueryColumn[] SEARCH_FIELDS = {
            BOAT_ORDERS.ID,
            BOAT_ORDERS.USER_ID,
            BOAT_ORDERS.BOAT_ID,
            BOAT_ORDERS.REQUEST_ID
    };

    /**
     * 解析管理员订单查询参数
     *
     * @param queryWrapper  查询包装器
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     */
    private void adminParseParams(QueryWrapper queryWrapper, String search, String sort, String startDateTime,
                                 String endDateTime) {
        POJOUtils.search(queryWrapper, SEARCH_FIELDS, search);
        POJOUtils.dateRange(queryWrapper, startDateTime, endDateTime);
        POJOUtils.sort(queryWrapper, ParamsUtils.getSortFromParams(sort));
    }

    /**
     * 获取管理员商品订单列表
     *
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 订单视图对象列表
     */
    public List<BaseBoatOrdersVO> adminGetGoodsOrdersList(
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseBoatOrdersDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, BoatOrders.class);
        adminParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.listAs(queryWrapper, BaseBoatOrdersVO.class);
    }

    /**
     * 分页获取管理员商品订单列表
     *
     * @param pageNum       页码
     * @param pageSize      每页大小
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 分页订单视图对象
     */
    public Page<BaseBoatOrdersVO> adminGetGoodsOrdersPage(
            Integer pageNum,
            Integer pageSize,
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseBoatOrdersDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, BoatOrders.class);
        adminParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseBoatOrdersVO.class);
    }

    /**
     * 根据 ID 获取管理员商品订单
     *
     * @param ids 订单 ID 字符串，多个 ID 用逗号分隔
     * @return 订单视图对象列表
     * @throws RuntimeException 当订单不存在时抛出
     */
    public List<BaseBoatOrdersVO> adminGetGoodsOrdersByIds(String ids) {
        List<Long> idList = ParamsUtils.getListFromIds(ids);

        if (idList.isEmpty()) {
            throw new RuntimeException("订单不存在");
        }

        List<BoatOrders> orders = POJOUtils.getListFromIds(idList, super::getById);

        return POJOUtils.asOtherList(orders, BaseBoatOrdersVO.class);
    }

    /**
     * 获取管理员船舶订单列表
     *
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 订单视图对象列表
     */
    public List<BaseBoatOrdersVO> adminGetBoatOrdersList(
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseBoatOrdersDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, BoatOrders.class);
        adminParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.listAs(queryWrapper, BaseBoatOrdersVO.class);
    }

    /**
     * 分页获取管理员船舶订单列表
     *
     * @param pageNum       页码
     * @param pageSize      每页大小
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 分页订单视图对象
     */
    public Page<BaseBoatOrdersVO> adminGetBoatOrdersPage(
            Integer pageNum,
            Integer pageSize,
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseBoatOrdersDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, BoatOrders.class);
        adminParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseBoatOrdersVO.class);
    }

    /**
     * 根据 ID 获取管理员船舶订单
     *
     * @param ids 订单 ID 字符串，多个 ID 用逗号分隔
     * @return 订单视图对象列表
     * @throws RuntimeException 当订单不存在时抛出
     */
    public List<BaseBoatOrdersVO> adminGetBoatOrdersByIds(String ids) {
        List<Long> idList = ParamsUtils.getListFromIds(ids);

        if (idList.isEmpty()) {
            throw new RuntimeException("订单不存在");
        }

        List<BoatOrders> orders = POJOUtils.getListFromIds(idList, super::getById);

        return POJOUtils.asOtherList(orders, BaseBoatOrdersVO.class);
    }

    /*
     * 商家函数
     */

    private QueryWrapper getVendorOrdersQueryWrapper(BaseBoatOrdersDTO boatOrdersDTO) {
        BoatOrders boatOrders = new BoatOrders();
        BeanUtils.copyProperties(boatOrdersDTO, boatOrders);
        QueryWrapper queryWrapper = QueryWrapper.create(boatOrders);
        queryWrapper.where(BOAT_ORDERS.USER_ID.eq(UserContext.getAccount().getId()));
        return queryWrapper;
    }

    public List<BaseBoatOrdersVO> getVendorOrdersListQuery(BaseBoatOrdersDTO boatOrdersDTO) {
        QueryWrapper queryWrapper = getVendorOrdersQueryWrapper(boatOrdersDTO);
        return super.listAs(queryWrapper, BaseBoatOrdersVO.class);
    }

    public Page<BaseBoatOrdersVO> getVendorOrdersPageQuery(Integer pageNum, Integer pageSize,
                                                           BaseBoatOrdersDTO boatOrdersDTO) {
        QueryWrapper queryWrapper = getVendorOrdersQueryWrapper(boatOrdersDTO);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseBoatOrdersVO.class);
    }

    private void verifyVendorRequestId(Long requestId) {
        BoatRequests boatRequests = boatRequestsService.getById(requestId);
        if (boatRequests == null) {
            throw new RuntimeException("请求不存在");
        }

        if (!boatRequests.getStatus().equals(OrderStatus.PENDING)) {
            throw new RuntimeException("请求状态不正确");
        }
    }

    public void handleVendorOrder(Long requestId, BaseBoatOrdersDTO boatOrdersDTO) {
        verifyVendorRequestId(requestId);

        // 0. 拿到所有参与计算的数据
        BoatRequests boatRequests = boatRequestsService.getById(requestId);
        Boats boat = boatService.getById(boatOrdersDTO.getBoatId());
        BoatTypes boatType = boatTypeService.getById(boat.getTypeId());

        // 1. 计算价格
        LocalDateTime startTime = boatRequests.getStartTime();
        LocalDateTime endTime = boatRequests.getEndTime();
        long hours = Duration.between(startTime, endTime).toHours();
        long minutes = Duration.between(startTime, endTime).toMinutes();
        // 不满一小时部分按一小时计算
        if (minutes % 60 > 0) {
            hours++;
        }
        BigDecimal price = boatType.getPrice().multiply(BigDecimal.valueOf(hours));

        // 2. 创建船舶订单
        BoatOrders boatOrders = new BoatOrders();
        boatOrders.setRequestId(requestId); // requestId
        boatOrders.setBoatId(boatOrdersDTO.getBoatId());
        boatOrders.setUserId(boatRequests.getUserId());
        boatOrders.setPrice(price);
        boatOrders.setDiscount(BigDecimal.ZERO);
        super.save(boatOrders);

        // 3. 更新请求状态
        boatRequests.setStatus(OrderStatus.ACCEPTED);
        boatRequestsService.updateById(boatRequests);
    }

    private void verifyVendorOrderId(Long id) {
        BoatOrders boatOrders = super.getById(id);
        if (boatOrders == null) {
            throw new RuntimeException("订单不存在");
        }

        if (boatOrders.getStatus().equals(OrderStatus.PENDING)) {
            throw new RuntimeException("订单状态不正确");
        }
    }

    public void completeVendorOrder(Long id) {
        verifyVendorOrderId(id);

        BoatOrders boatOrders = super.getById(id);
        boatOrders.setStatus(OrderStatus.UNPAID);
        super.updateById(boatOrders);

        BoatRequests boatRequests = boatRequestsService.getById(boatOrders.getRequestId());
        boatRequests.setStatus(OrderStatus.COMPLETED);
        boatRequestsService.updateById(boatRequests);
    }

    public void cancelVendorOrder(Long id) {
        verifyVendorOrderId(id);

        BoatOrders boatOrders = super.getById(id);
        boatOrders.setStatus(OrderStatus.CANCELLED);
        super.updateById(boatOrders);

        BoatRequests boatRequests = boatRequestsService.getById(boatOrders.getRequestId());
        boatRequests.setStatus(OrderStatus.CANCELLED);
        boatRequestsService.updateById(boatRequests);
    }

    /*
     * 用户函数
     */

    private QueryWrapper getUserBoatOrdersQueryWrapper(BaseBoatOrdersDTO boatOrdersDTO) {
        BoatOrders boatOrders = new BoatOrders();
        BeanUtils.copyProperties(boatOrdersDTO, boatOrders);
        QueryWrapper queryWrapper = QueryWrapper.create(boatOrders);
        queryWrapper.where(BOAT_ORDERS.USER_ID.eq(UserContext.getAccount().getId()));
        queryWrapper.orderBy(BOAT_ORDERS.CREATED_AT, false);
        return queryWrapper;
    }

    public List<BaseBoatOrdersVO> getUserBoatOrdersListQuery(BaseBoatOrdersDTO boatOrdersDTO) {
        QueryWrapper queryWrapper = getUserBoatOrdersQueryWrapper(boatOrdersDTO);
        return super.listAs(queryWrapper, BaseBoatOrdersVO.class);
    }


    public Page<BaseBoatOrdersVO> getUserBoatOrdersPageQuery(Integer pageNum, Integer pageSize,
                                                             BaseBoatOrdersDTO boatOrdersDTO) {
        QueryWrapper queryWrapper = getUserBoatOrdersQueryWrapper(boatOrdersDTO);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseBoatOrdersVO.class);
    }

    private void verifyUserId(Long id) {
        BoatOrders boatOrders = super.getById(id);
        if (boatOrders == null) {
            throw new RuntimeException("订单不存在");
        }

        if (!boatOrders.getUserId().equals(UserContext.getAccount().getId())) {
            throw new RuntimeException("订单不存在");
        }
    }

    public void cancelUserBoatOrders(Long id) {
        verifyUserId(id);

        BoatOrders boatOrders = super.getById(id);

        if (!boatOrders.getStatus().equals(OrderStatus.PENDING)) {
            throw new RuntimeException("订单状态不正确");
        }

        boatOrders.setStatus(OrderStatus.CANCELLED);
        super.updateById(boatOrders);
    }

    public void payUserBoatOrders(Long id) {
        verifyUserId(id);

        BoatOrders boatOrders = super.getById(id);

        if (!boatOrders.getStatus().equals(OrderStatus.UNPAID)) {
            throw new RuntimeException("订单状态不正确");
        }

        boatOrders.setStatus(OrderStatus.PAID);
        super.updateById(boatOrders);
    }
}
