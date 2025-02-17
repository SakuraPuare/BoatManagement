package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseVendorsDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Vendors;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseVendorsVO;
import com.sakurapuare.boatmanagement.service.base.impl.BaseVendorsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorsService extends BaseVendorsServiceImpl {

    /*
     * 管理员函数
     */

    private QueryWrapper getAdminQueryWrapper(BaseVendorsDTO queryDTO) {
        Vendors vendors = new Vendors();
        BeanUtils.copyProperties(queryDTO, vendors);
        return QueryWrapper.create(vendors);
    }

    public List<BaseVendorsVO> getListQuery(BaseVendorsDTO queryDTO) {
        QueryWrapper queryWrapper = getAdminQueryWrapper(queryDTO);
        return super.listAs(queryWrapper, BaseVendorsVO.class);
    }

    public Page<BaseVendorsVO> getPageQuery(Integer pageNum, Integer pageSize, BaseVendorsDTO queryDTO) {
        QueryWrapper queryWrapper = getAdminQueryWrapper(queryDTO);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseVendorsVO.class);
    }

    public void addVendor(BaseVendorsDTO baseVendorsDTO) {
        Vendors vendors = new Vendors();
        BeanUtils.copyProperties(baseVendorsDTO, vendors);
        super.save(vendors);
    }

    private void verifyId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("供应商ID不能为空");
        }

        Vendors vendors = super.getById(id);
        if (vendors == null) {
            throw new IllegalArgumentException("供应商不存在");
        }
    }

    public void updateVendor(Long id, BaseVendorsDTO baseVendorsDTO) {
        verifyId(id);
        Vendors vendors = new Vendors();
        BeanUtils.copyProperties(baseVendorsDTO, vendors);
        vendors.setId(id);
        super.updateById(vendors);
    }

    public void deleteVendor(Long id) {
        verifyId(id);
        super.removeById(id);
    }

}
