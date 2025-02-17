package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseMerchantsDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Merchants;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseMerchantsVO;
import com.sakurapuare.boatmanagement.service.base.impl.BaseMerchantsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantsService extends BaseMerchantsServiceImpl {

    /*
     * 管理员函数
     */

    private QueryWrapper getAdminQueryWrapper(BaseMerchantsDTO queryDTO) {
        Merchants merchants = new Merchants();
        BeanUtils.copyProperties(queryDTO, merchants);
        return QueryWrapper.create(merchants);
    }


    public List<BaseMerchantsVO> getListQuery(BaseMerchantsDTO queryDTO) {
        QueryWrapper queryWrapper = getAdminQueryWrapper(queryDTO);
        return super.listAs(queryWrapper, BaseMerchantsVO.class);
    }

    public Page<BaseMerchantsVO> getPageQuery(Integer pageNum, Integer pageSize, BaseMerchantsDTO queryDTO) {
        QueryWrapper queryWrapper = getAdminQueryWrapper(queryDTO);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseMerchantsVO.class);
    }

    public void addMerchant(BaseMerchantsDTO baseMerchantsDTO) {
        Merchants merchants = new Merchants();
        BeanUtils.copyProperties(baseMerchantsDTO, merchants);
        super.save(merchants);
    }

    private void verifyId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("商户ID不能为空");
        }

        Merchants merchants = super.getById(id);
        if (merchants == null) {
            throw new IllegalArgumentException("商户不存在");
        }
    }

    public void updateMerchant(Long id, BaseMerchantsDTO baseMerchantsDTO) {
        verifyId(id);
        Merchants merchants = new Merchants();
        BeanUtils.copyProperties(baseMerchantsDTO, merchants);
        merchants.setId(id);
        super.updateById(merchants);
    }

    public void deleteMerchant(Long id) {
        verifyId(id);
        super.removeById(id);
    }

    /*
     * 用户函数
     */

    private QueryWrapper getUserQueryWrapper(BaseMerchantsDTO queryDTO) {
        Merchants merchants = new Merchants();
        BeanUtils.copyProperties(queryDTO, merchants);
        return QueryWrapper.create(merchants);
    }

    public List<BaseMerchantsVO> getUserMerchantListQuery(BaseMerchantsDTO queryDTO) {
        QueryWrapper queryWrapper = getUserQueryWrapper(queryDTO);
        return super.listAs(queryWrapper, BaseMerchantsVO.class);
    }

    public Page<BaseMerchantsVO> getUserMerchantPageQuery(Integer pageNum, Integer pageSize, BaseMerchantsDTO queryDTO) {
        QueryWrapper queryWrapper = getUserQueryWrapper(queryDTO);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseMerchantsVO.class);
    }

    private void verifyMerchantId(Long id) {
        Merchants merchants = super.getById(id);
        if (merchants == null) {
            throw new IllegalArgumentException("商户不存在");
        }
    }

    public BaseMerchantsVO getUserMerchantById(Long id) {
        verifyMerchantId(id);
        Merchants merchants = super.getById(id);
        BaseMerchantsVO baseMerchantsVO = new BaseMerchantsVO();
        BeanUtils.copyProperties(merchants, baseMerchantsVO);
        return baseMerchantsVO;
    }
}
