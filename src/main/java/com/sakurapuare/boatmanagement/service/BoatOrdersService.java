package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.paginate.Page;
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

    /*
     * 管理员函数
     */

    private QueryWrapper getAdminGoodsOrdersQueryWrapper(BaseBoatOrdersDTO boatOrdersDTO) {
        BoatOrders boatOrders = new BoatOrders();
        BeanUtils.copyProperties(boatOrdersDTO, boatOrders);
        return QueryWrapper.create(boatOrders);
    }

    private QueryWrapper getAdminBoatOrdersQueryWrapper(BaseBoatOrdersDTO boatOrdersDTO) {
        BoatOrders boatOrders = new BoatOrders();
        BeanUtils.copyProperties(boatOrdersDTO, boatOrders);
        return QueryWrapper.create(boatOrders);
    }

    public List<BaseBoatOrdersVO> getAdminGoodsOrdersListQuery(BaseBoatOrdersDTO boatOrdersDTO) {
        QueryWrapper orderQueryWrapper = getAdminGoodsOrdersQueryWrapper(boatOrdersDTO);
        return super.listAs(orderQueryWrapper, BaseBoatOrdersVO.class);
    }

    public Page<BaseBoatOrdersVO> getAdminGoodsOrdersPageQuery(Integer pageNum, Integer pageSize,
                                                               BaseBoatOrdersDTO boatOrdersDTO) {
        QueryWrapper orderQueryWrapper = getAdminGoodsOrdersQueryWrapper(boatOrdersDTO);
        return super.pageAs(Page.of(pageNum, pageSize), orderQueryWrapper, BaseBoatOrdersVO.class);
    }

    public List<BaseBoatOrdersVO> getAdminBoatOrdersListQuery(BaseBoatOrdersDTO boatOrdersDTO) {
        QueryWrapper orderQueryWrapper = getAdminBoatOrdersQueryWrapper(boatOrdersDTO);
        return super.listAs(orderQueryWrapper, BaseBoatOrdersVO.class);
    }

    public Page<BaseBoatOrdersVO> getAdminBoatOrdersPageQuery(Integer pageNum, Integer pageSize,
                                                              BaseBoatOrdersDTO boatOrdersDTO) {
        QueryWrapper orderQueryWrapper = getAdminBoatOrdersQueryWrapper(boatOrdersDTO);
        return super.pageAs(Page.of(pageNum, pageSize), orderQueryWrapper, BaseBoatOrdersVO.class);
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
