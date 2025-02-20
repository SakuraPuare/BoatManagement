package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.common.context.UserContext;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseBoatsDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Boats;
import com.sakurapuare.boatmanagement.pojo.entity.Units;
import com.sakurapuare.boatmanagement.pojo.entity.Vendors;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseBoatsVO;
import com.sakurapuare.boatmanagement.service.base.BaseBoatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.BOATS;
import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.VENDORS;

@Service
@RequiredArgsConstructor
public class BoatsService extends BaseBoatsService {

    private final VendorsService vendorsService;

    private final UnitsService unitsService;

    /*
     * 管理员的函数
     */

    private QueryWrapper getAdminQueryWrapper(BaseBoatsDTO queryDTO) {
        Boats boats = new Boats();
        BeanUtils.copyProperties(queryDTO, boats);
        return QueryWrapper.create(boats);
    }

    public List<BaseBoatsVO> getAdminBoatListQuery(BaseBoatsDTO queryDTO) {
        QueryWrapper queryWrapper = getAdminQueryWrapper(queryDTO);
        return super.listAs(queryWrapper, BaseBoatsVO.class);
    }

    public Page<BaseBoatsVO> getAdminBoatPageQuery(Integer pageNum, Integer pageSize, BaseBoatsDTO queryDTO) {
        QueryWrapper queryWrapper = getAdminQueryWrapper(queryDTO);

        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseBoatsVO.class);
    }

    public void addBoat(BaseBoatsDTO boatsDTO) {
        Boats boats = new Boats();
        BeanUtils.copyProperties(boatsDTO, boats);
        super.save(boats);
    }

    private void verifyId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("船舶ID不能为空");
        }

        Boats boats = super.getById(id);
        if (boats == null) {
            throw new IllegalArgumentException("船舶不存在");
        }
    }

    public void updateBoat(Long id, BaseBoatsDTO boatsDTO) {
        verifyId(id);
        Boats boats = new Boats();
        BeanUtils.copyProperties(boatsDTO, boats);
        boats.setId(id);
        super.updateById(boats);
    }

    public void deleteBoat(Long id) {
        verifyId(id);
        super.removeById(id);
    }

    /*
     * 供应商的函数
     */

    private Vendors getVendor() {
        Vendors vendor = vendorsService
                .getOne(new QueryWrapper().where(VENDORS.USER_ID.eq(UserContext.getAccount().getId())));
        if (vendor == null) {
            throw new RuntimeException("供应商不存在");
        }
        return vendor;
    }

    private Units getUnit(Vendors vendor) {
        return unitsService.getById(vendor.getUnitId());
    }

    private QueryWrapper getVendorQueryWrapper(BaseBoatsDTO queryDTO) {
        Boats query = new Boats();
        BeanUtils.copyProperties(queryDTO, query);
        QueryWrapper queryWrapper = QueryWrapper.create(query);
        Vendors vendor = getVendor();

        Units unit = getUnit(vendor);

        if (unit == null) {
            queryWrapper.where(BOATS.VENDOR_ID.eq(vendor.getId()));
        } else {
            queryWrapper.where(BOATS.UNIT_ID.eq(unit.getId()));
        }

        return queryWrapper;
    }

    public List<BaseBoatsVO> getVendorBoatsListQuery(BaseBoatsDTO queryDTO) {
        QueryWrapper queryWrapper = getVendorQueryWrapper(queryDTO);
        return super.listAs(queryWrapper, BaseBoatsVO.class);
    }

    public Page<BaseBoatsVO> getVendorBoatsPageQuery(Integer pageNum, Integer pageSize, BaseBoatsDTO queryDTO) {
        QueryWrapper queryWrapper = getVendorQueryWrapper(queryDTO);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseBoatsVO.class);
    }

    public void addVendorBoat(BaseBoatsDTO boatsDTO) {
        Vendors vendor = getVendor();
        Units unit = getUnit(vendor);

        Boats boats = new Boats();
        BeanUtils.copyProperties(boatsDTO, boats);
        boats.setVendorId(vendor.getId());
        if (unit != null) {
            boats.setUnitId(unit.getId());
        }
        super.save(boats);
    }

    private void vendorVerifyId(Long id) {
        verifyId(id);

        // 必须属于当前供应商
        if (!super.getById(id).getVendorId().equals(getVendor().getId())) {
            throw new IllegalArgumentException("无权限操作");
        }
    }

    public void updateVendorBoat(Long id, BaseBoatsDTO boatsDTO) {
        vendorVerifyId(id);
        Boats boats = new Boats();
        BeanUtils.copyProperties(boatsDTO, boats);
        boats.setId(id);
        super.updateById(boats);
    }

    public void deleteVendorBoat(Long id) {
        vendorVerifyId(id);
        super.removeById(id);
    }

}
