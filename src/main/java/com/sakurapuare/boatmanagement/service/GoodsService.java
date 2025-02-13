package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.common.UserContext;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseGoodsDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Goods;
import com.sakurapuare.boatmanagement.pojo.entity.Merchants;
import com.sakurapuare.boatmanagement.pojo.entity.Units;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseGoodsVO;
import com.sakurapuare.boatmanagement.service.base.impl.BaseGoodsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sakurapuare.boatmanagement.pojo.entity.table.GoodsTableDef.GOODS;
import static com.sakurapuare.boatmanagement.pojo.entity.table.MerchantsTableDef.MERCHANTS;

@Service
@RequiredArgsConstructor
public class GoodsService extends BaseGoodsServiceImpl {

    private final MerchantsService merchantsService;

    private final UnitsService unitsService;

    private Merchants getMerchant() {
        return merchantsService.getOne(
                QueryWrapper.create().eq(MERCHANTS.USER_ID.getName(), UserContext.getAccount().getId()));
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

    public Goods getGoods(Long id) {
        Units unit = getUnit();
        return super.getOne(
                QueryWrapper.create().eq(GOODS.CREATED_UNIT_ID.getName(), unit.getId()).eq(GOODS.ID.getName(), id));
    }

    public List<BaseGoodsVO> getMerchantsGoodsList() {
        Units unit = getUnit();

        return super.listAs(
                QueryWrapper.create().eq(GOODS.CREATED_UNIT_ID.getName(), unit.getId()),
                BaseGoodsVO.class);
    }

    public Page<BaseGoodsVO> getMerchantsGoodsPage(Integer pageNum, Integer pageSize) {
        Units unit = getUnit();

        return super.pageAs(
                Page.of(pageNum, pageSize),
                QueryWrapper.create().eq(GOODS.CREATED_UNIT_ID.getName(), unit.getId()),
                BaseGoodsVO.class);
    }

    public void addGoods(BaseGoodsDTO goods) {
        Merchants merchant = getMerchant();
        Units unit = getUnit(merchant);
        Goods newGoods = new Goods();
        BeanUtils.copyProperties(goods, newGoods);
        newGoods.setCreatedUnitId(unit.getId());
        newGoods.setCreatedMerchantId(merchant.getId());
        save(newGoods);
    }

    public void updateGoods(Long id, BaseGoodsDTO goods) {
        Goods oldGoods = getGoods(id);
        if (oldGoods == null) {
            throw new RuntimeException("商品不存在");
        }
        BeanUtils.copyProperties(goods, oldGoods);
        oldGoods.setId(id);
        updateById(oldGoods);
    }

    public void deleteGoods(Long id) {
        Goods oldGoods = getGoods(id);
        if (oldGoods == null) {
            throw new RuntimeException("商品不存在");
        }
        removeById(id);
    }
}
