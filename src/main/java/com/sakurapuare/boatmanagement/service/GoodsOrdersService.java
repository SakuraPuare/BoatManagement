package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.common.context.UserContext;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseGoodsOrdersDTO;
import com.sakurapuare.boatmanagement.pojo.entity.GoodsOrders;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseGoodsOrdersVO;
import com.sakurapuare.boatmanagement.service.base.impl.BaseGoodsOrdersServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.GOODS_ORDERS;

@Service
@RequiredArgsConstructor
public class GoodsOrdersService extends BaseGoodsOrdersServiceImpl {

    /*
     * 商家函数
     */

    private QueryWrapper getMerchantQueryWrapper(BaseGoodsOrdersDTO goodsOrderDTO) {
        GoodsOrders goodsOrder = new GoodsOrders();
        BeanUtils.copyProperties(goodsOrderDTO, goodsOrder);
        QueryWrapper queryWrapper = QueryWrapper.create(goodsOrder);
        queryWrapper.eq(GOODS_ORDERS.MERCHANT_ID.getName(), UserContext.getAccount().getId());
        return queryWrapper;
    }

    public List<BaseGoodsOrdersVO> getMerchantsGoodsOrdersListQuery(BaseGoodsOrdersDTO goodsOrderDTO) {
        QueryWrapper queryWrapper = getMerchantQueryWrapper(goodsOrderDTO);
        return super.listAs(queryWrapper, BaseGoodsOrdersVO.class);
    }

    public Page<BaseGoodsOrdersVO> getMerchantsGoodsOrdersPageQuery(Integer pageNum, Integer pageSize, BaseGoodsOrdersDTO goodsOrderDTO) {
        QueryWrapper queryWrapper = getMerchantQueryWrapper(goodsOrderDTO);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseGoodsOrdersVO.class);
    }

    private void verifyMerchantId(Long id) {
        GoodsOrders goodsOrder = super.getById(id);
        if (goodsOrder == null) {
            throw new IllegalArgumentException("订单不存在");
        }

        if (!goodsOrder.getMerchantId().equals(UserContext.getAccount().getId())) {
            throw new IllegalArgumentException("订单不存在");
        }
    }

    public void completeMerchantOrder(Long id) {
        verifyMerchantId(id);
        GoodsOrders goodsOrder = super.getById(id);
        if (goodsOrder.getStatus().equals("COMPLETED")) {
            throw new IllegalArgumentException("订单已完成");
        }

        goodsOrder.setStatus("COMPLETED");
        super.updateById(goodsOrder);

    }

    public void cancelMerchantOrder(Long id) {
        verifyMerchantId(id);

        GoodsOrders goodsOrder = super.getById(id);
        if (goodsOrder.getStatus().equals("CANCELLED")) {
            throw new IllegalArgumentException("订单已取消");
        }

        goodsOrder.setStatus("CANCELLED");
        super.updateById(goodsOrder);
    }

    /*
     * 用户函数
     */

    private QueryWrapper getUserQueryWrapper(BaseGoodsOrdersDTO goodsOrderDTO) {
        GoodsOrders query = new GoodsOrders();
        BeanUtils.copyProperties(goodsOrderDTO, query);
        QueryWrapper queryWrapper = QueryWrapper.create(query);
        queryWrapper.eq(GOODS_ORDERS.USER_ID.getName(), UserContext.getAccount().getId());
        return queryWrapper;
    }

    public List<BaseGoodsOrdersDTO> getUserGoodsOrdersListQuery(BaseGoodsOrdersDTO goodsOrderDTO) {
        QueryWrapper queryWrapper = getUserQueryWrapper(goodsOrderDTO);
        return super.listAs(queryWrapper, BaseGoodsOrdersDTO.class);
    }

    public Page<BaseGoodsOrdersDTO> getUserGoodsOrdersPageQuery(Integer pageNum, Integer pageSize, BaseGoodsOrdersDTO goodsOrderDTO) {
        QueryWrapper queryWrapper = getUserQueryWrapper(goodsOrderDTO);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseGoodsOrdersDTO.class);
    }

    public void createUserGoodsOrder(BaseGoodsOrdersDTO goodsOrderDTO) {
        GoodsOrders goodsOrder = new GoodsOrders();
        BeanUtils.copyProperties(goodsOrderDTO, goodsOrder);
        goodsOrder.setUserId(UserContext.getAccount().getId());
        super.save(goodsOrder);
    }

    private void verifyUserId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id不能为空");
        }

        GoodsOrders goodsOrder = super.getById(id);
        if (goodsOrder == null) {
            throw new IllegalArgumentException("订单不存在");
        }

        if (!goodsOrder.getUserId().equals(UserContext.getAccount().getId())) {
            throw new IllegalArgumentException("订单不存在");
        }
    }

    public void cancelUserGoodsOrder(Long id) {
        verifyUserId(id);

        GoodsOrders goodsOrder = super.getById(id);
        if (goodsOrder.getStatus().equals("CANCELLED")) {
            throw new IllegalArgumentException("订单已取消");
        }

        goodsOrder.setStatus("CANCELLED");
        super.updateById(goodsOrder);
    }

    public void payUserGoodsOrder(Long id) {
        verifyUserId(id);

        GoodsOrders goodsOrder = super.getById(id);
        if (goodsOrder.getStatus().equals("PAID")) {
            throw new IllegalArgumentException("订单已支付");
        }

        goodsOrder.setStatus("PAID");
        super.updateById(goodsOrder);
    }
}
