package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.query.QueryWrapperAdapter;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseUnitsDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Units;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseUnitsVO;
import com.sakurapuare.boatmanagement.service.base.BaseUnitsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnitsService extends BaseUnitsService {

    /*
     * 管理员函数
     */

    private QueryWrapper getAdminUnitListQueryWrapper(BaseUnitsDTO queryDTO) {
        Units units = new Units();
        BeanUtils.copyProperties(queryDTO, units);
        return QueryWrapperAdapter.create(units);
    }

    public List<BaseUnitsVO> getAdminUnitListQuery(BaseUnitsDTO queryDTO) {
        return super.listAs(getAdminUnitListQueryWrapper(queryDTO), BaseUnitsVO.class);
    }

    public Page<BaseUnitsVO> getAdminUnitPageQuery(Integer pageNum, Integer pageSize, BaseUnitsDTO queryDTO) {
        return super.pageAs(Page.of(pageNum, pageSize), getAdminUnitListQueryWrapper(queryDTO), BaseUnitsVO.class);
    }

    public BaseUnitsVO getAdminUnit(Long id) {
        Units units = super.getById(id);
        if (units == null) {
            throw new IllegalArgumentException("单位不存在");
        }
        BaseUnitsVO baseUnitsVO = new BaseUnitsVO();
        BeanUtils.copyProperties(units, baseUnitsVO);
        return baseUnitsVO;
    }
}
