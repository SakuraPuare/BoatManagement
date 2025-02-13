package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.common.UserContext;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseBoatTypesDTO;
import com.sakurapuare.boatmanagement.pojo.entity.BoatTypes;
import com.sakurapuare.boatmanagement.pojo.entity.Units;
import com.sakurapuare.boatmanagement.pojo.entity.Vendors;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseBoatTypesVO;
import com.sakurapuare.boatmanagement.service.base.impl.BaseBoatTypesServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sakurapuare.boatmanagement.pojo.entity.table.BoatTypesTableDef.BOAT_TYPES;
import static com.sakurapuare.boatmanagement.pojo.entity.table.VendorsTableDef.VENDORS;

@Service
@RequiredArgsConstructor
public class BoatTypesService extends BaseBoatTypesServiceImpl {

    private final VendorsService vendorsService;

    private final UnitsService unitsService;

    /*
     * 管理员函数
     */

    public List<BaseBoatTypesVO> getListQuery(BaseBoatTypesDTO queryDTO) {
        BoatTypes query = new BoatTypes();
        BeanUtils.copyProperties(queryDTO, query);
        QueryWrapper queryWrapper = QueryWrapper.create(query);
        return super.listAs(queryWrapper, BaseBoatTypesVO.class);
    }

    public Page<BaseBoatTypesVO> getPageQuery(Integer pageNum, Integer pageSize, BaseBoatTypesDTO queryDTO) {
        BoatTypes query = new BoatTypes();
        BeanUtils.copyProperties(queryDTO, query);
        QueryWrapper queryWrapper = QueryWrapper.create(query);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseBoatTypesVO.class);
    }

    public void addBoatType(BaseBoatTypesDTO baseBoatTypeDTO) {
        BoatTypes boatTypes = new BoatTypes();
        BeanUtils.copyProperties(baseBoatTypeDTO, boatTypes);
        super.save(boatTypes);
    }

    private void verifyId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("船舶类型ID不能为空");
        }

        BoatTypes boatTypes = super.getById(id);
        if (boatTypes == null) {
            throw new IllegalArgumentException("船舶类型不存在");
        }
    }


    public void updateBoatType(Long id, BaseBoatTypesDTO baseBoatTypeDTO) {
        verifyId(id);
        BoatTypes boatTypes = new BoatTypes();
        BeanUtils.copyProperties(baseBoatTypeDTO, boatTypes);
        boatTypes.setId(id);
        super.updateById(boatTypes);
    }

    public void deleteBoatType(Long id) {
        verifyId(id);
        super.removeById(id);
    }

    /*
     * 供应商函数
     */

    private Vendors getVendor() {
        Vendors vendor = vendorsService
                .getOne(new QueryWrapper().eq(VENDORS.USER_ID.getName(), UserContext.getAccount().getId()));
        if (vendor == null) {
            throw new IllegalArgumentException("供应商不存在");
        }
        return vendor;
    }

    private Units getUnit(Vendors vendor) {
        return unitsService.getById(vendor.getUnitId());
    }

    private QueryWrapper getVendorQueryWrapper(BaseBoatTypesDTO queryDTO) {
        QueryWrapper queryWrapper = QueryWrapper.create(queryDTO);
        Vendors vendor = getVendor();
        Units unit = getUnit(vendor);
        if (unit == null) {
            queryWrapper.eq(BOAT_TYPES.CREATED_VENDOR_ID.getName(), vendor.getId());
        } else {
            queryWrapper.eq(BOAT_TYPES.CREATED_UNIT_ID.getName(), unit.getId());
        }
        return queryWrapper;
    }

    public List<BaseBoatTypesVO> getVendorBoatTypesList(BaseBoatTypesDTO queryDTO) {
        QueryWrapper queryWrapper = getVendorQueryWrapper(queryDTO);

        List<BaseBoatTypesVO> boatTypes = super.listAs(queryWrapper, BaseBoatTypesVO.class);
        return boatTypes;
    }

    public Page<BaseBoatTypesVO> getVendorBoatTypesPage(Integer pageNum, Integer pageSize, BaseBoatTypesDTO queryDTO) {
        QueryWrapper queryWrapper = getVendorQueryWrapper(queryDTO);

        Page<BaseBoatTypesVO> boatTypes = super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseBoatTypesVO.class);
        return boatTypes;
    }

    public void addVendorBoatType(BaseBoatTypesDTO boatTypesDTO) {
        Vendors vendor = getVendor();
        Units unit = getUnit(vendor);
        BoatTypes boatTypes = new BoatTypes();
        BeanUtils.copyProperties(boatTypesDTO, boatTypes);
        boatTypes.setCreatedVendorId(vendor.getId());

        if (unit != null) {
            boatTypes.setCreatedUnitId(unit.getId());
        }
        super.save(boatTypes);
    }

    private void vendorVerifyId(Long id) {
        verifyId(id);

        // 必须属于当前供应商
        if (!getById(id).getCreatedVendorId().equals(getVendor().getId())) {
            throw new IllegalArgumentException("船舶类型不属于当前供应商");
        }
    }

    public void updateVendorBoatType(Long id, BaseBoatTypesDTO boatTypesDTO) {
        vendorVerifyId(id);
        BoatTypes boatTypes = new BoatTypes();
        BeanUtils.copyProperties(boatTypesDTO, boatTypes);
        boatTypes.setId(id);
        super.updateById(boatTypes);
    }

    public void deleteVendorBoatType(Long id) {
        vendorVerifyId(id);
        super.removeById(id);
    }
}
