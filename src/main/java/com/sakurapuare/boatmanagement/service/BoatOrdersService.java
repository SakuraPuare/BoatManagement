package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.common.context.UserContext;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseBoatOrdersDTO;
import com.sakurapuare.boatmanagement.pojo.entity.BoatOrders;
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

    private QueryWrapper getUserBoatOrdersQueryWrapper(BaseBoatOrdersDTO boatOrderDTO) {
        QueryWrapper queryWrapper = QueryWrapper.create(boatOrderDTO);
        queryWrapper.eq(BOAT_ORDERS.USER_ID.getName(), UserContext.getAccount().getId());
        return queryWrapper;
    }

    public List<BaseBoatOrdersVO> getUserBoatOrders(BaseBoatOrdersDTO boatOrderDTO) {
        QueryWrapper queryWrapper = getUserBoatOrdersQueryWrapper(boatOrderDTO);
        return super.listAs(queryWrapper, BaseBoatOrdersVO.class);
    }

    public Page<BaseBoatOrdersVO> getUserBoatOrdersPage(Integer pageNum, Integer pageSize, BaseBoatOrdersDTO boatOrderDTO) {
        QueryWrapper queryWrapper = getUserBoatOrdersQueryWrapper(boatOrderDTO);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseBoatOrdersVO.class);
    }

    public void createUserBoatOrder(BaseBoatOrdersDTO boatOrderDTO) {
        BoatOrders boatOrder = new BoatOrders();
        BeanUtils.copyProperties(boatOrderDTO, boatOrder);
        boatOrder.setUserId(UserContext.getAccount().getId());
        super.save(boatOrder);
    }

    private void verifyUserId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id不能为空");
        }

        BoatOrders boatOrder = super.getById(id);
        if (boatOrder == null) {
            throw new IllegalArgumentException("订单不存在");
        }

        if (boatOrder.getUserId() != UserContext.getAccount().getId()) {
            throw new IllegalArgumentException("订单不存在");
        }
    }

    public void cancelUserBoatOrder(Long id) {
        verifyUserId(id);

        BoatOrders boatOrder = super.getById(id);
        if (boatOrder.getStatus() == "CANCELLED") {
            throw new IllegalArgumentException("订单已取消");
        }

        boatOrder.setStatus("CANCELLED");
        super.updateById(boatOrder);
    }

    public void payUserBoatOrder(Long id) {
        verifyUserId(id);

        BoatOrders boatOrder = super.getById(id);
        if (boatOrder.getStatus() == "PAID") {
            throw new IllegalArgumentException("订单已支付");
        }

        boatOrder.setStatus("PAID");
        super.updateById(boatOrder);
    }
}
