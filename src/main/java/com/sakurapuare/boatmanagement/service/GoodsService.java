package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.common.context.UserContext;
import com.sakurapuare.boatmanagement.constant.OrderStatus;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseGoodsDTO;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseGoodsOrdersDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Goods;
import com.sakurapuare.boatmanagement.pojo.entity.GoodsOrders;
import com.sakurapuare.boatmanagement.pojo.entity.Merchants;
import com.sakurapuare.boatmanagement.pojo.entity.Units;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseGoodsVO;
import com.sakurapuare.boatmanagement.service.base.BaseGoodsService;
import com.sakurapuare.boatmanagement.utils.POJOUtils;
import com.sakurapuare.boatmanagement.utils.ParamsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.GOODS;
import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.MERCHANTS;

@Service
@RequiredArgsConstructor
public class GoodsService extends BaseGoodsService {

    private final MerchantsService merchantsService;
    private final UnitsService unitsService;

    /**
     * 管理员搜索字段
     */
    private static final QueryColumn[] SEARCH_FIELDS = {
            GOODS.ID,
            GOODS.NAME,
            GOODS.MERCHANT_ID,
            GOODS.UNIT_ID
    };

    /**
     * 解析管理员商品查询参数
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
     * 获取管理员商品列表
     *
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 商品视图对象列表
     */
    public List<BaseGoodsVO> adminGetGoodsList(
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseGoodsDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, Goods.class);
        adminParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.listAs(queryWrapper, BaseGoodsVO.class);
    }

    /**
     * 分页获取管理员商品列表
     *
     * @param pageNum       页码
     * @param pageSize      每页大小
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 分页商品视图对象
     */
    public Page<BaseGoodsVO> adminGetGoodsPage(
            Integer pageNum,
            Integer pageSize,
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseGoodsDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, Goods.class);
        adminParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseGoodsVO.class);
    }

    /**
     * 根据 ID 获取管理员商品
     *
     * @param ids 商品 ID 字符串，多个 ID 用逗号分隔
     * @return 商品视图对象列表
     * @throws RuntimeException 当商品不存在时抛出
     */
    public List<BaseGoodsVO> adminGetGoodsByIds(String ids) {
        List<Long> idList = ParamsUtils.getListFromIds(ids);

        if (idList.isEmpty()) {
            throw new RuntimeException("商品不存在");
        }

        List<Goods> goods = POJOUtils.getListFromIds(idList, super::getById);

        return POJOUtils.asOtherList(goods, BaseGoodsVO.class);
    }

    /**
     * 创建商品
     *
     * @param dto 商品数据传输对象
     * @param merchantId 商家ID
     * @param unitId 单位ID
     * @return 创建的商品视图对象
     */
    public BaseGoodsVO adminCreateGoods(BaseGoodsDTO dto, Long merchantId, Long unitId) {
        // 验证商家是否存在
        Merchants merchant = merchantsService.getById(merchantId);
        if (merchant == null) {
            throw new RuntimeException("商家不存在");
        }
        
        // 验证单位是否存在
        Units unit = unitsService.getById(unitId);
        if (unit == null) {
            throw new RuntimeException("单位不存在");
        }
        
        // 验证商家是否属于指定单位
        if (!merchant.getUnitId().equals(unitId)) {
            throw new RuntimeException("商家不属于指定单位");
        }
        
        Goods goods = POJOUtils.asOther(dto, Goods.class);
        goods.setMerchantId(merchantId);
        goods.setUnitId(unitId);
        super.save(goods);
        return POJOUtils.asOther(goods, BaseGoodsVO.class);
    }

    /**
     * 更新商品信息
     *
     * @param id  商品 ID
     * @param dto 商品数据传输对象
     * @return 更新后的商品视图对象
     * @throws RuntimeException 当商品不存在时抛出
     */
    public BaseGoodsVO adminUpdateGoods(Long id, BaseGoodsDTO dto) {
        Goods goods = super.getById(id);
        if (goods == null) {
            throw new RuntimeException("商品不存在");
        }
        
        Goods updatedGoods = POJOUtils.asOther(dto, Goods.class);
        updatedGoods.setId(id);
        super.updateById(updatedGoods, false);
        return POJOUtils.asOther(updatedGoods, BaseGoodsVO.class);
    }

    /**
     * 删除商品
     *
     * @param id 商品 ID
     * @throws RuntimeException 当商品不存在时抛出
     */
    public void adminDeleteGoods(Long id) {
        Goods goods = super.getById(id);
        if (goods == null) {
            throw new RuntimeException("商品不存在");
        }
        super.removeById(id);
    }

    /**
     * 恢复商品库存（订单取消时调用）
     *
     * @param orderInfoMap 订单信息映射
     */
    public void restoreGoodsStock(Map<Long, Double> orderInfoMap) {
        for (Map.Entry<Long, Double> entry : orderInfoMap.entrySet()) {
            Goods goods = super.getById(entry.getKey());
            if (goods != null) {
                int orderQuantity = entry.getValue().intValue();
                goods.setStock(goods.getStock() + orderQuantity);
                goods.setSales((goods.getSales() != null ? goods.getSales() : 0) - orderQuantity);
                // 确保销量不为负数
                if (goods.getSales() < 0) {
                    goods.setSales(0L);
                }
                super.updateById(goods, false);
            }
        }
    }

    /*
     * 商户函数
     */

    private Merchants getMerchant() {
        return merchantsService.getOne(
                QueryWrapper.create().where(MERCHANTS.USER_ID.eq(UserContext.getAccount().getId())));
    }

    private Units getUnit() {
        Merchants merchant = getMerchant();
        if (merchant == null) {
            throw new RuntimeException("商家不存在");
        }
        return unitsService.getById(merchant.getUnitId());
    }

    private Units getUnit(Merchants merchant) {
        Units unit = unitsService.getById(merchant.getUnitId());
        if (unit == null) {
            throw new RuntimeException("单位不存在");
        }
        return unit;
    }

    private QueryWrapper getMerchantQueryWrapper(BaseGoodsDTO queryDTO) {
        Merchants merchant = getMerchant();
        if (merchant == null) {
            throw new RuntimeException("商家不存在");
        }

        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(queryDTO, Goods.class);
        queryWrapper.and(GOODS.MERCHANT_ID.eq(merchant.getId()));
        return queryWrapper;
    }

    public Goods merchantGetGoods(Long id) {
        return super.getById(id);
    }

    public List<BaseGoodsVO> merchantGetGoodsListQuery(BaseGoodsDTO queryDTO) {
        return super.listAs(getMerchantQueryWrapper(queryDTO), BaseGoodsVO.class);
    }

    public Page<BaseGoodsVO> merchantGetGoodsPageQuery(Integer pageNum, Integer pageSize, BaseGoodsDTO queryDTO) {
        return super.pageAs(Page.of(pageNum, pageSize), getMerchantQueryWrapper(queryDTO), BaseGoodsVO.class);
    }

    public BaseGoodsVO merchantGetGoodsById(Long id) {
        Goods goods = merchantGetGoods(id);
        if (goods == null) {
            throw new RuntimeException("商品不存在");
        }
        // 验证归属
        verifyId(id);
        return POJOUtils.asOther(goods, BaseGoodsVO.class);
    }

    public void merchantCreateGoods(BaseGoodsDTO goods) {
        Merchants merchant = getMerchant();
        if (merchant == null) {
            throw new RuntimeException("商家不存在");
        }
        Units unit = getUnit(merchant);
        if (unit == null) {
            throw new RuntimeException("单位不存在");
        }
        Goods newGoods = POJOUtils.asOther(goods, Goods.class);
        newGoods.setMerchantId(merchant.getId());
        newGoods.setUnitId(unit.getId());
        super.save(newGoods);
    }

    private void verifyId(Long id) {
        Merchants merchant = getMerchant();
        if (merchant == null) {
            throw new RuntimeException("商家不存在");
        }
        Goods goods = super.getById(id);
        if (goods == null) {
            throw new RuntimeException("商品不存在");
        }
        if (!goods.getMerchantId().equals(merchant.getId())) {
            throw new RuntimeException("商品不属于当前商家");
        }
    }

    public void merchantUpdateGoods(Long id, BaseGoodsDTO goods) {
        verifyId(id);
        Goods updatedGoods = POJOUtils.asOther(goods, Goods.class);
        updatedGoods.setId(id);
        super.updateById(updatedGoods, false);
    }

    public void merchantDeleteGoods(Long id) {
        verifyId(id);
        // 注意：这里暂时移除了订单检查以避免循环依赖
        // 在实际应用中，应该在控制器层或者通过事件机制来处理这种检查
        super.removeById(id);
    }

    /*
     * 用户函数
     */

    private QueryWrapper getUserGoodsQueryWrapper(BaseGoodsDTO queryDTO) {
        Goods goods = new Goods();
        BeanUtils.copyProperties(queryDTO, goods);
        return QueryWrapper.create(goods);
    }

    public List<BaseGoodsVO> getUserMerchantGoodsList(Long merchantId, BaseGoodsDTO queryDTO) {
        QueryWrapper queryWrapper = getUserGoodsQueryWrapper(queryDTO);
        queryWrapper.where(GOODS.MERCHANT_ID.eq(merchantId));
        return super.listAs(queryWrapper, BaseGoodsVO.class);
    }

    public Page<BaseGoodsVO> getUserMerchantGoodsPage(Long merchantId, Integer pageNum, Integer pageSize,
                                                      BaseGoodsDTO queryDTO) {
        QueryWrapper queryWrapper = getUserGoodsQueryWrapper(queryDTO);
        queryWrapper.where(GOODS.MERCHANT_ID.eq(merchantId));
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseGoodsVO.class);
    }

    /**
     * 减少商品库存（下单时调用）
     *
     * @param orderInfoMap 订单信息映射
     * @return 订单总价
     */
    public BigDecimal reduceGoodsStock(Long merchantId, Map<Long, Double> orderInfoMap) {
        BigDecimal price = BigDecimal.ZERO;
        
        // 第一步：检查所有商品的库存和有效性
        for (Map.Entry<Long, Double> entry : orderInfoMap.entrySet()) {
            Goods goods = super.getById(entry.getKey());
            if (goods == null) {
                throw new RuntimeException("商品不存在");
            }
            if (!goods.getMerchantId().equals(merchantId)) {
                throw new RuntimeException("商品不属于指定商家");
            }
            
            int orderQuantity = entry.getValue().intValue();
            if (goods.getStock() < orderQuantity) {
                throw new RuntimeException("商品 \"" + goods.getName() + "\" 库存不足，当前库存：" + goods.getStock() + "，需要：" + orderQuantity);
            }
            
            price = price.add(goods.getPrice().multiply(BigDecimal.valueOf(entry.getValue())));
        }

        // 第二步：减少库存
        for (Map.Entry<Long, Double> entry : orderInfoMap.entrySet()) {
            Goods goods = super.getById(entry.getKey());
            int orderQuantity = entry.getValue().intValue();
            goods.setStock(goods.getStock() - orderQuantity);
            goods.setSales((goods.getSales() != null ? goods.getSales() : 0) + orderQuantity);
            super.updateById(goods, false);
        }
        
        return price;
    }



    /*
     * 公共函数（不需要登录）
     */

    /**
     * 解析公共查询参数
     *
     * @param queryWrapper  查询包装器
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     */
    private void publicParseParams(QueryWrapper queryWrapper, String search, String sort, String startDateTime,
                                  String endDateTime) {
        queryWrapper.where(GOODS.IS_ENABLED.eq(true));
        POJOUtils.search(queryWrapper, SEARCH_FIELDS, search);
        POJOUtils.dateRange(queryWrapper, startDateTime, endDateTime);
        POJOUtils.sort(queryWrapper, ParamsUtils.getSortFromParams(sort));
    }

    /**
     * 获取公共商品列表
     *
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 商品视图对象列表
     */
    public List<BaseGoodsVO> publicGetGoodsList(
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseGoodsDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, Goods.class);
        publicParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.listAs(queryWrapper, BaseGoodsVO.class);
    }

    /**
     * 分页获取公共商品列表
     *
     * @param pageNum          页码
     * @param pageSize         每页大小
     * @param search           搜索关键词
     * @param sort             排序方式
     * @param startDateTime    开始时间
     * @param endDateTime      结束时间
     * @param filter           过滤条件
     * @return 分页商品视图对象
     */
    public Page<BaseGoodsVO> publicGetGoodsPage(
            Integer pageNum,
            Integer pageSize,
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseGoodsDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, Goods.class);
        publicParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseGoodsVO.class);
    }

    /**
     * 根据 ID 获取公共商品
     *
     * @param ids 商品 ID 字符串，多个 ID 用逗号分隔
     * @return 商品视图对象列表
     * @throws RuntimeException 当商品不存在时抛出
     */
    public List<BaseGoodsVO> publicGetGoodsByIds(String ids) {
        List<Long> idList = ParamsUtils.getListFromIds(ids);

        if (idList.isEmpty()) {
            throw new RuntimeException("商品不存在");
        }

        List<Goods> goods = POJOUtils.getListFromIds(idList, id -> {
            Goods good = super.getById(id);
            if (good != null && !good.getIsEnabled()) {
                throw new RuntimeException("商品未启用");
            }
            return good;
        });

        return POJOUtils.asOtherList(goods, BaseGoodsVO.class);
    }
}
