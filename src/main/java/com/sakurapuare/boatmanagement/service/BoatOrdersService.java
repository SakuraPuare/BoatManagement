package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.common.context.UserContext;
import com.sakurapuare.boatmanagement.constant.OrderStatus;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseBoatOrdersDTO;
import com.sakurapuare.boatmanagement.pojo.entity.*;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseBoatOrdersVO;
import com.sakurapuare.boatmanagement.service.base.impl.BaseBoatOrdersServiceImpl;
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
public class BoatOrdersService extends BaseBoatOrdersServiceImpl {

    private final BoatRequestsService boatRequestsService;

    private final BoatsService boatService;

    private final BoatTypesService boatTypeService;

    private final OrdersService ordersService;

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

        // 2. 创建订单
        Orders orders = new Orders();
        BeanUtils.copyProperties(boatOrdersDTO, orders);
        orders.setUserId(boatRequests.getUserId());
        orders.setStatus(OrderStatus.ACCEPTED);
        orders.setPrice(price);
        orders.setDiscount(BigDecimal.ZERO);
        ordersService.save(orders);

        // 3. 创建船舶订单
        BoatOrders boatOrders = new BoatOrders();
        boatOrders.setUserId(boatRequests.getUserId());
        boatOrders.setOrderId(orders.getId());
        boatOrders.setRequestId(requestId); // requestId
        boatOrders.setBoatId(boatOrdersDTO.getBoatId());
        super.save(boatOrders);

        // 4. 更新请求状态
        boatRequests.setStatus(OrderStatus.ACCEPTED);
        boatRequests.setOrderId(orders.getId());
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
        boatOrders.setStatus(OrderStatus.COMPLETED);
        super.updateById(boatOrders);

        BoatRequests boatRequests = new BoatRequests();
        boatRequests.setId(boatOrders.getRequestId());
        boatRequests.setStatus(OrderStatus.COMPLETED);
        boatRequestsService.updateById(boatRequests);

        Orders orders = ordersService.getById(boatOrders.getOrderId());
        orders.setStatus(OrderStatus.UNPAID);
        ordersService.updateById(orders);
    }

    public void cancelVendorOrder(Long id) {
        verifyVendorOrderId(id);

        BoatOrders boatOrders = super.getById(id);
        boatOrders.setStatus(OrderStatus.CANCELLED);
        super.updateById(boatOrders);

        Orders orders = ordersService.getById(boatOrders.getOrderId());
        orders.setStatus(OrderStatus.CANCELLED);
        ordersService.updateById(orders);
    }
}
