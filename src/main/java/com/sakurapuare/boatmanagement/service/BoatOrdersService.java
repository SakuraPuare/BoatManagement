package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.common.context.UserContext;
import com.sakurapuare.boatmanagement.constant.BoatOrderStatus;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseBoatOrdersDTO;
import com.sakurapuare.boatmanagement.pojo.entity.BoatOrders;
import com.sakurapuare.boatmanagement.pojo.entity.BoatRequests;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseBoatOrdersVO;
import com.sakurapuare.boatmanagement.service.base.impl.BaseBoatOrdersServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.BOAT_ORDERS;

@Service
@RequiredArgsConstructor
public class BoatOrdersService extends BaseBoatOrdersServiceImpl {

    private final BoatRequestsService boatRequestsService;

    private QueryWrapper getVendorOrdersQueryWrapper(BaseBoatOrdersDTO boatOrdersDTO) {
        BoatOrders boatOrders = new BoatOrders();
        BeanUtils.copyProperties(boatOrdersDTO, boatOrders);
        QueryWrapper queryWrapper = QueryWrapper.create(boatOrders);
        queryWrapper.eq(BOAT_ORDERS.USER_ID.getName(), UserContext.getAccount().getId());
        return queryWrapper;
    }

    public List<BaseBoatOrdersVO> getVendorOrdersListQuery(BaseBoatOrdersDTO boatOrdersDTO) {
        QueryWrapper queryWrapper = getVendorOrdersQueryWrapper(boatOrdersDTO);
        return super.listAs(queryWrapper, BaseBoatOrdersVO.class);
    }

    public Page<BaseBoatOrdersVO> getVendorOrdersPageQuery(Integer pageNum, Integer pageSize, BaseBoatOrdersDTO boatOrdersDTO) {
        QueryWrapper queryWrapper = getVendorOrdersQueryWrapper(boatOrdersDTO);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseBoatOrdersVO.class);
    }

    public void completeVendorOrder(Long id) {
        BoatOrders boatOrders = super.getById(id);
        boatOrders.setStatus(BoatOrderStatus.COMPLETED);
        super.updateById(boatOrders);

        BoatRequests boatRequests = new BoatRequests();
        boatRequests.setId(boatOrders.getRequestId());
        boatRequests.setStatus(BoatOrderStatus.COMPLETED);
        boatRequestsService.updateById(boatRequests);
    }

    public void cancelVendorOrder(Long id) {
        BoatOrders boatOrders = super.getById(id);
        boatOrders.setStatus(BoatOrderStatus.CANCELLED);
        super.updateById(boatOrders);
    }
}
