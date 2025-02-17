package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.common.context.UserContext;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseGoodsDTO;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseGoodsOrdersDTO;
import com.sakurapuare.boatmanagement.pojo.entity.*;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseGoodsVO;
import com.sakurapuare.boatmanagement.service.base.impl.BaseGoodsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.GOODS;
import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.MERCHANTS;

@Service
@RequiredArgsConstructor
public class GoodsService extends BaseGoodsServiceImpl {

    private final MerchantsService merchantsService;

    private final UnitsService unitsService;

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
        Goods goods = new Goods();
        BeanUtils.copyProperties(queryDTO, goods);
        QueryWrapper queryWrapper = QueryWrapper.create(goods);
        Units unit = getUnit();
        queryWrapper.where(GOODS.UNIT_ID.eq(unit.getId()));
        return queryWrapper;
    }

    public Goods getMerchantsGoods(Long id) {
        QueryWrapper queryWrapper = getMerchantQueryWrapper(new BaseGoodsDTO());
        queryWrapper.where(GOODS.ID.eq(id));
        return super.getOne(queryWrapper);
    }

    public List<BaseGoodsVO> getMerchantsGoodsListQuery(BaseGoodsDTO queryDTO) {
        QueryWrapper queryWrapper = getMerchantQueryWrapper(queryDTO);
        return super.listAs(queryWrapper, BaseGoodsVO.class);
    }

    public Page<BaseGoodsVO> getMerchantsGoodsPageQuery(Integer pageNum, Integer pageSize, BaseGoodsDTO queryDTO) {
        QueryWrapper queryWrapper = getMerchantQueryWrapper(queryDTO);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseGoodsVO.class);
    }

    public BaseGoodsVO getMerchantsGoodsById(Long id) {
        Goods goods = getMerchantsGoods(id);
        if (goods == null) {
            throw new RuntimeException("商品不存在");
        }
        BaseGoodsVO baseGoodsVO = new BaseGoodsVO();
        BeanUtils.copyProperties(goods, baseGoodsVO);
        return baseGoodsVO;
    }

    public void addMerchantsGoods(BaseGoodsDTO goods) {
        Merchants merchant = getMerchant();
        Units unit = getUnit(merchant);
        Goods newGoods = new Goods();
        BeanUtils.copyProperties(goods, newGoods);
        newGoods.setUnitId(unit.getId());
        newGoods.setMerchantId(merchant.getId());
        super.save(newGoods);
    }

    private void verifyId(Long id) {
        if (id == null) {
            throw new RuntimeException("商品ID不能为空");
        }

        Goods goods = getMerchantsGoods(id);
        if (goods == null) {
            throw new RuntimeException("商品不存在");
        }
    }

    public void updateMerchantsGoods(Long id, BaseGoodsDTO goods) {
        verifyId(id);
        Goods oldGoods = getMerchantsGoods(id);
        BeanUtils.copyProperties(goods, oldGoods);
        super.updateById(oldGoods);
    }

    public void deleteMerchantsGoods(Long id) {
        verifyId(id);
        super.removeById(id);
    }

    /*
     * 用户函数
     */

    private QueryWrapper getUserGoodsQueryWrapper(BaseGoodsDTO queryDTO) {
        Goods goods = new Goods();
        BeanUtils.copyProperties(queryDTO, goods);
        QueryWrapper queryWrapper = QueryWrapper.create(goods);
        return queryWrapper;
    }

    public List<BaseGoodsVO> getUserMerchantGoodsList(Long merchantId, BaseGoodsDTO queryDTO) {
        QueryWrapper queryWrapper = getUserGoodsQueryWrapper(queryDTO);
        queryWrapper.where(GOODS.MERCHANT_ID.eq(merchantId));
        return super.listAs(queryWrapper, BaseGoodsVO.class);
    }

    public Page<BaseGoodsVO> getUserMerchantGoodsPage(Long merchantId, Integer pageNum, Integer pageSize, BaseGoodsDTO queryDTO) {
        QueryWrapper queryWrapper = getUserGoodsQueryWrapper(queryDTO);
        queryWrapper.where(GOODS.MERCHANT_ID.eq(merchantId));
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseGoodsVO.class);
    }


    public String createUserMerchantGoodsOrder(Long merchantId, BaseGoodsOrdersDTO orderDTO) {
        Map<Integer, Double> orderInfoMap = orderDTO.getOrderInfo();
        Orders order = new Orders();

//        order.setMerchantId(merchantId);
        GoodsOrders goodsOrders = new GoodsOrders();
        BeanUtils.copyProperties(orderDTO, goodsOrders);
        return "";
    }
}
