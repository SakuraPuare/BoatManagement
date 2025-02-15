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

import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.GOODS;
import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.MERCHANTS;

@Service
@RequiredArgsConstructor
public class GoodsService extends BaseGoodsServiceImpl {

    private final MerchantsService merchantsService;

    private final UnitsService unitsService;

    /*
     * 管理员函数
     */

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

    public Goods getMerchantsGoods(Long id) {
        Units unit = getUnit();
        return super.getOne(
                QueryWrapper.create().eq(GOODS.CREATED_UNIT_ID.getName(), unit.getId()).eq(GOODS.ID.getName(), id));
    }

    public List<BaseGoodsVO> getMerchantsGoodsList(BaseGoodsDTO queryDTO) {
        Units unit = getUnit();
        Goods query = new Goods();
        BeanUtils.copyProperties(queryDTO, query);
        QueryWrapper queryWrapper = QueryWrapper.create(query);
        return super.listAs(
                queryWrapper.eq(GOODS.CREATED_UNIT_ID.getName(), unit.getId()),
                BaseGoodsVO.class);
    }

    public Page<BaseGoodsVO> getMerchantsGoodsPage(Integer pageNum, Integer pageSize, BaseGoodsDTO queryDTO) {
        Units unit = getUnit();
        Goods query = new Goods();
        BeanUtils.copyProperties(queryDTO, query);
        QueryWrapper queryWrapper = QueryWrapper.create(query);

        return super.pageAs(
                Page.of(pageNum, pageSize),
                queryWrapper.eq(GOODS.CREATED_UNIT_ID.getName(), unit.getId()),
                BaseGoodsVO.class);
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
        newGoods.setCreatedUnitId(unit.getId());
        newGoods.setCreatedMerchantId(merchant.getId());
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

}
