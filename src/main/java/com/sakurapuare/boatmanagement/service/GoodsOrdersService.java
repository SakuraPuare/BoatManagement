package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.common.context.UserContext;
import com.sakurapuare.boatmanagement.constant.OrderStatus;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseGoodsOrdersDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Goods;
import com.sakurapuare.boatmanagement.pojo.entity.GoodsOrders;
import com.sakurapuare.boatmanagement.pojo.entity.Merchants;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseGoodsOrdersVO;
import com.sakurapuare.boatmanagement.service.base.BaseGoodsOrdersService;
import com.sakurapuare.boatmanagement.utils.POJOUtils;
import com.sakurapuare.boatmanagement.utils.ParamsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.GOODS_ORDERS;
import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.MERCHANTS;

@Service
@RequiredArgsConstructor
public class GoodsOrdersService extends BaseGoodsOrdersService {

    private final GoodsService goodsService;
    private final MerchantsService merchantsService;

    /**
     * 恢复商品库存（订单取消时调用）
     *
     * @param orderId 订单 ID
     */
    private void restoreGoodsStock(Long orderId) {
        GoodsOrders order = super.getById(orderId);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }

        Map<Long, Double> orderInfoMap = order.getOrderInfo();
        goodsService.restoreGoodsStock(orderInfoMap);
    }

    /**
     * 管理员搜索字段
     */
    private static final QueryColumn[] SEARCH_FIELDS = {
            GOODS_ORDERS.ID,
            GOODS_ORDERS.USER_ID,
            GOODS_ORDERS.MERCHANT_ID,
            GOODS_ORDERS.STATUS
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
     * 获取管理员订单列表
     *
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 订单视图对象列表
     */
    public List<BaseGoodsOrdersVO> adminGetGoodsOrdersList(
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseGoodsOrdersDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, GoodsOrders.class);
        adminParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.listAs(queryWrapper, BaseGoodsOrdersVO.class);
    }

    /**
     * 分页获取管理员订单列表
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
    public Page<BaseGoodsOrdersVO> adminGetGoodsOrdersPage(
            Integer pageNum,
            Integer pageSize,
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseGoodsOrdersDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, GoodsOrders.class);
        adminParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseGoodsOrdersVO.class);
    }

    /**
     * 根据 ID 获取管理员订单
     *
     * @param ids 订单 ID 字符串，多个 ID 用逗号分隔
     * @return 订单视图对象列表
     * @throws RuntimeException 当订单不存在时抛出
     */
    public List<BaseGoodsOrdersVO> adminGetGoodsOrdersByIds(String ids) {
        List<Long> idList = ParamsUtils.getListFromIds(ids);

        if (idList.isEmpty()) {
            throw new RuntimeException("订单不存在");
        }

        List<GoodsOrders> orders = POJOUtils.getListFromIds(idList, super::getById);

        return POJOUtils.asOtherList(orders, BaseGoodsOrdersVO.class);
    }

    /**
     * 管理员完成订单
     *
     * @param id 订单 ID
     */
    public void adminCompleteOrder(Long id) {
        GoodsOrders goodsOrder = super.getById(id);
        if (goodsOrder == null) {
            throw new RuntimeException("订单不存在");
        }
        
        // UNPAID / PAID -> COMPLETED
        if (!goodsOrder.getStatus().equals(OrderStatus.UNPAID) && !goodsOrder.getStatus().equals(OrderStatus.PAID)) {
            throw new RuntimeException("订单状态不正确");
        }

        goodsOrder.setStatus(OrderStatus.COMPLETED);
        super.updateById(goodsOrder, false);
    }

    /**
     * 管理员取消订单
     *
     * @param id 订单 ID
     */
    public void adminCancelOrder(Long id) {
        GoodsOrders goodsOrder = super.getById(id);
        if (goodsOrder == null) {
            throw new RuntimeException("订单不存在");
        }
        
        // COMPLETED !-> CANCELLED, PAID -> REFUNDED -> CANCELLED
        if (goodsOrder.getStatus().equals(OrderStatus.COMPLETED)) {
            throw new RuntimeException("订单状态不正确");
        }

        if (goodsOrder.getStatus().equals(OrderStatus.PAID)) {
            // TODO: 退款
            goodsOrder.setStatus(OrderStatus.REFUNDING);
        } else {
            // 恢复库存
            restoreGoodsStock(id);
            goodsOrder.setStatus(OrderStatus.CANCELLED);
        }

        super.updateById(goodsOrder, false);
    }

    /*
     * 商家函数
     */

    private QueryWrapper getMerchantQueryWrapper(BaseGoodsOrdersDTO goodsOrderDTO) {
        // 获取当前用户对应的商家
        Merchants merchant = merchantsService.getOne(
                QueryWrapper.create().where(MERCHANTS.USER_ID.eq(UserContext.getAccount().getId())));
        if (merchant == null) {
            throw new RuntimeException("当前用户不是商家");
        }
        
        GoodsOrders goodsOrder = new GoodsOrders();
        BeanUtils.copyProperties(goodsOrderDTO, goodsOrder);
        QueryWrapper queryWrapper = QueryWrapper.create(goodsOrder);
        queryWrapper.where(GOODS_ORDERS.MERCHANT_ID.eq(merchant.getId()));
        return queryWrapper;
    }

    public List<BaseGoodsOrdersVO> merchantGetGoodsOrdersList(BaseGoodsOrdersDTO goodsOrderDTO) {
        QueryWrapper queryWrapper = getMerchantQueryWrapper(goodsOrderDTO);
        return super.listAs(queryWrapper, BaseGoodsOrdersVO.class);
    }

    public Page<BaseGoodsOrdersVO> merchantGetGoodsOrdersPage(Integer pageNum, Integer pageSize, BaseGoodsOrdersDTO goodsOrderDTO) {
        QueryWrapper queryWrapper = getMerchantQueryWrapper(goodsOrderDTO);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseGoodsOrdersVO.class);
    }

    private void verifyMerchantId(Long id) {
        // 获取当前用户对应的商家
        Merchants merchant = merchantsService.getOne(
                QueryWrapper.create().where(MERCHANTS.USER_ID.eq(UserContext.getAccount().getId())));
        if (merchant == null) {
            throw new IllegalArgumentException("当前用户不是商家");
        }
        
        GoodsOrders goodsOrder = super.getById(id);
        if (goodsOrder == null) {
            throw new IllegalArgumentException("订单不存在");
        }

        if (!goodsOrder.getMerchantId().equals(merchant.getId())) {
            throw new IllegalArgumentException("订单不存在");
        }
    }

    public void merchantCompleteOrder(Long id) {
        verifyMerchantId(id);
        GoodsOrders goodsOrder = super.getById(id);
        // UNPAID / PAID -> COMPLETED
        if (!goodsOrder.getStatus().equals(OrderStatus.UNPAID) && !goodsOrder.getStatus().equals(OrderStatus.PAID)) {
            throw new IllegalArgumentException("订单状态不正确");
        }

        goodsOrder.setStatus(OrderStatus.COMPLETED);
        super.updateById(goodsOrder, false);
    }

    public void merchantCancelOrder(Long id) {
        verifyMerchantId(id);

        GoodsOrders goodsOrder = super.getById(id);
        // COMPLETED !-> CANCELLED, PAID -> REFUNDED -> CANCELLED
        if (goodsOrder.getStatus().equals(OrderStatus.COMPLETED)) {
            throw new IllegalArgumentException("订单状态不正确");
        }

        if (goodsOrder.getStatus().equals(OrderStatus.PAID)) {
            // TODO: 退款
            goodsOrder.setStatus(OrderStatus.REFUNDING);
        } else {
            // 恢复库存
            restoreGoodsStock(id);
            goodsOrder.setStatus(OrderStatus.CANCELLED);
        }

        super.updateById(goodsOrder, false);
    }

    /*
     * 用户函数
     */

    private QueryWrapper getUserQueryWrapper(BaseGoodsOrdersDTO goodsOrderDTO) {
        GoodsOrders query = new GoodsOrders();
        BeanUtils.copyProperties(goodsOrderDTO, query);
        QueryWrapper queryWrapper = QueryWrapper.create(query);
        queryWrapper.where(GOODS_ORDERS.USER_ID.eq(UserContext.getAccount().getId()));
        return queryWrapper;
    }

    public List<BaseGoodsOrdersVO> userGetGoodsOrdersList(BaseGoodsOrdersDTO goodsOrderDTO) {
        QueryWrapper queryWrapper = getUserQueryWrapper(goodsOrderDTO);
        return super.listAs(queryWrapper, BaseGoodsOrdersVO.class);
    }

    public Page<BaseGoodsOrdersVO> userGetGoodsOrdersPage(Integer pageNum, Integer pageSize, BaseGoodsOrdersDTO goodsOrderDTO) {
        QueryWrapper queryWrapper = getUserQueryWrapper(goodsOrderDTO);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseGoodsOrdersVO.class);
    }

    private void verifyUserId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id 不能为空");
        }

        GoodsOrders goodsOrder = super.getById(id);
        if (goodsOrder == null) {
            throw new IllegalArgumentException("订单不存在");
        }

        if (!goodsOrder.getUserId().equals(UserContext.getAccount().getId())) {
            throw new IllegalArgumentException("订单不存在");
        }
    }

    public void userCancelGoodsOrder(Long id) {
        verifyUserId(id);

        GoodsOrders goodsOrder = super.getById(id);
        // COMPLETED !-> CANCELLED, PAID -> REFUNDED -> CANCELLED
        if (goodsOrder.getStatus().equals(OrderStatus.COMPLETED)) {
            throw new IllegalArgumentException("订单状态不正确");
        }

        if (goodsOrder.getStatus().equals(OrderStatus.PAID)) {
            // TODO: 退款
            goodsOrder.setStatus(OrderStatus.REFUNDING);
        } else {
            // 恢复库存
            restoreGoodsStock(id);
            goodsOrder.setStatus(OrderStatus.CANCELLED);
        }

        super.updateById(goodsOrder, false);
    }

    public void userPayGoodsOrder(Long id) {
        verifyUserId(id);

        GoodsOrders goodsOrder = super.getById(id);
        if (!goodsOrder.getStatus().equals(OrderStatus.UNPAID)) {
            throw new IllegalArgumentException("订单状态不正确");
        }

        goodsOrder.setStatus(OrderStatus.PAID);
        super.updateById(goodsOrder, false);
    }

    /**
     * 用户创建商家商品订单
     *
     * @param merchantId 商家 ID
     * @param orderDTO   订单数据传输对象
     */
    public void createUserMerchantGoodsOrder(Long merchantId, BaseGoodsOrdersDTO orderDTO) {
        // 检查用户是否是该商家（防止商家下单自己的商品）
        Merchants userMerchant = merchantsService.getOne(
                QueryWrapper.create().where(MERCHANTS.USER_ID.eq(UserContext.getAccount().getId())));
        if (userMerchant != null && userMerchant.getId().equals(merchantId)) {
            throw new RuntimeException("不能购买自己的商品");
        }
        
        Map<Long, Double> orderInfoMap = orderDTO.getOrderInfo();
        
        // 减少库存并获取总价
        BigDecimal price = goodsService.reduceGoodsStock(merchantId, orderInfoMap);

        // 创建订单
        GoodsOrders goodsOrders = new GoodsOrders();
        goodsOrders.setMerchantId(merchantId);
        goodsOrders.setOrderInfo(orderInfoMap);
        goodsOrders.setUserId(UserContext.getAccount().getId());
        goodsOrders.setStatus(OrderStatus.UNPAID);
        goodsOrders.setPrice(price);
        goodsOrders.setDiscount(BigDecimal.ZERO);
        super.save(goodsOrders);
    }
}
