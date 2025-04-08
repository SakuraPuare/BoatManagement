package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.common.context.UserContext;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseBoatTypesDTO;
import com.sakurapuare.boatmanagement.pojo.entity.BoatTypes;
import com.sakurapuare.boatmanagement.pojo.entity.Units;
import com.sakurapuare.boatmanagement.pojo.entity.Vendors;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseBoatTypesVO;
import com.sakurapuare.boatmanagement.service.base.BaseBoatTypesService;
import com.sakurapuare.boatmanagement.utils.POJOUtils;
import com.sakurapuare.boatmanagement.utils.ParamsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.BOAT_TYPES;
import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.VENDORS;

@Service
@RequiredArgsConstructor
public class BoatTypesService extends BaseBoatTypesService {

    private final VendorsService vendorsService;
    private final UnitsService unitsService;

    /**
     * 搜索字段
     */
    private static final QueryColumn[] SEARCH_FIELDS = {
            BOAT_TYPES.ID,
            BOAT_TYPES.TYPE_NAME,
            BOAT_TYPES.DESCRIPTION,
            BOAT_TYPES.PRICE,
            BOAT_TYPES.WIDTH,
            BOAT_TYPES.LENGTH,
            BOAT_TYPES.MAX_LOAD,
            BOAT_TYPES.MAX_SPEED
    };

    /*
     * 管理员函数
     */

    /**
     * 解析管理员查询参数
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
     * 获取船舶类型列表
     *
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 船舶类型视图对象列表
     */
    public List<BaseBoatTypesVO> adminGetBoatTypeList(
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseBoatTypesDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, BoatTypes.class);
        adminParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.listAs(queryWrapper, BaseBoatTypesVO.class);
    }

    /**
     * 分页获取船舶类型列表
     *
     * @param pageNum          页码
     * @param pageSize         每页大小
     * @param search           搜索关键词
     * @param sort             排序方式
     * @param startDateTime    开始时间
     * @param endDateTime      结束时间
     * @param filter           过滤条件
     * @return 分页船舶类型视图对象
     */
    public Page<BaseBoatTypesVO> adminGetBoatTypePage(
            Integer pageNum,
            Integer pageSize,
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseBoatTypesDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, BoatTypes.class);
        adminParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseBoatTypesVO.class);
    }

    /**
     * 根据 ID 获取船舶类型
     *
     * @param ids 船舶类型 ID 字符串，多个 ID 用逗号分隔
     * @return 船舶类型视图对象列表
     * @throws RuntimeException 当船舶类型不存在时抛出
     */
    public List<BaseBoatTypesVO> adminGetBoatTypeByIds(String ids) {
        List<Long> idList = ParamsUtils.getListFromIds(ids);

        if (idList.isEmpty()) {
            throw new RuntimeException("船舶类型不存在");
        }

        List<BoatTypes> boatTypes = POJOUtils.getListFromIds(idList, super::getById);

        return POJOUtils.asOtherList(boatTypes, BaseBoatTypesVO.class);
    }

    /**
     * 创建船舶类型
     *
     * @param dto 船舶类型数据传输对象
     * @return 创建的船舶类型视图对象
     */
    public BaseBoatTypesVO adminCreateBoatType(BaseBoatTypesDTO dto) {
        BoatTypes boatType = POJOUtils.asOther(dto, BoatTypes.class);
        super.save(boatType);
        return POJOUtils.asOther(boatType, BaseBoatTypesVO.class);
    }

    /**
     * 检查船舶类型是否存在
     *
     * @param id 船舶类型 ID
     * @throws RuntimeException 当船舶类型不存在时抛出
     */
    private void checkBoatTypeExist(Long id) {
        if (super.getById(id) == null) {
            throw new RuntimeException("船舶类型不存在");
        }
    }

    /**
     * 更新船舶类型信息
     *
     * @param id  船舶类型 ID
     * @param dto 船舶类型数据传输对象
     * @return 更新后的船舶类型视图对象
     */
    public BaseBoatTypesVO adminUpdateBoatType(Long id, BaseBoatTypesDTO dto) {
        checkBoatTypeExist(id);
        BoatTypes boatType = POJOUtils.asOther(dto, BoatTypes.class);
        boatType.setId(id);
        super.updateById(boatType);
        return POJOUtils.asOther(super.getById(id), BaseBoatTypesVO.class);
    }

    /**
     * 删除船舶类型
     *
     * @param id 船舶类型 ID
     */
    public void adminDeleteBoatType(Long id) {
        checkBoatTypeExist(id);
        super.removeById(id);
    }

    /*
     * 供应商函数
     */

    /**
     * 获取当前供应商
     *
     * @return 供应商实体
     * @throws RuntimeException 当供应商不存在时抛出
     */
    private Vendors getVendor() {
        Vendors vendor = vendorsService
                .getOne(new QueryWrapper().where(VENDORS.USER_ID.eq(UserContext.getAccount().getId())));
        if (vendor == null) {
            throw new RuntimeException("供应商不存在");
        }
        return vendor;
    }

    /**
     * 获取供应商所属单位
     *
     * @param vendor 供应商实体
     * @return 单位实体
     */
    private Units getUnit(Vendors vendor) {
        return unitsService.getById(vendor.getUnitId());
    }

    /**
     * 解析供应商查询参数
     *
     * @param queryWrapper  查询包装器
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     */
    private void vendorParseParams(QueryWrapper queryWrapper, String search, String sort, String startDateTime,
                                   String endDateTime) {
        Vendors vendor = getVendor();
        Units unit = getUnit(vendor);

        if (unit == null) {
            queryWrapper.where(BOAT_TYPES.VENDOR_ID.eq(vendor.getId()));
        } else {
            queryWrapper.where(BOAT_TYPES.UNIT_ID.eq(unit.getId()));
        }

        POJOUtils.search(queryWrapper, SEARCH_FIELDS, search);
        POJOUtils.dateRange(queryWrapper, startDateTime, endDateTime);
        POJOUtils.sort(queryWrapper, ParamsUtils.getSortFromParams(sort));
    }

    /**
     * 获取供应商船舶类型列表
     *
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 船舶类型视图对象列表
     */
    public List<BaseBoatTypesVO> vendorGetBoatTypeList(
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseBoatTypesDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, BoatTypes.class);
        vendorParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.listAs(queryWrapper, BaseBoatTypesVO.class);
    }

    /**
     * 分页获取供应商船舶类型列表
     *
     * @param pageNum          页码
     * @param pageSize         每页大小
     * @param search           搜索关键词
     * @param sort             排序方式
     * @param startDateTime    开始时间
     * @param endDateTime      结束时间
     * @param filter           过滤条件
     * @return 分页船舶类型视图对象
     */
    public Page<BaseBoatTypesVO> vendorGetBoatTypePage(
            Integer pageNum,
            Integer pageSize,
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseBoatTypesDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, BoatTypes.class);
        vendorParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseBoatTypesVO.class);
    }

    /**
     * 根据 ID 获取供应商船舶类型
     *
     * @param ids 船舶类型 ID 字符串，多个 ID 用逗号分隔
     * @return 船舶类型视图对象列表
     * @throws RuntimeException 当船舶类型不存在或无权限时抛出
     */
    public List<BaseBoatTypesVO> vendorGetBoatTypeByIds(String ids) {
        List<Long> idList = ParamsUtils.getListFromIds(ids);

        if (idList.isEmpty()) {
            throw new RuntimeException("船舶类型不存在");
        }

        Vendors vendor = getVendor();
        List<BoatTypes> boatTypes = POJOUtils.getListFromIds(idList, id -> {
            BoatTypes boatType = super.getById(id);
            if (boatType != null && !boatType.getVendorId().equals(vendor.getId())) {
                throw new RuntimeException("无权限操作");
            }
            return boatType;
        });

        return POJOUtils.asOtherList(boatTypes, BaseBoatTypesVO.class);
    }

    /**
     * 创建供应商船舶类型
     *
     * @param dto 船舶类型数据传输对象
     * @return 创建的船舶类型视图对象
     */
    public BaseBoatTypesVO vendorCreateBoatType(BaseBoatTypesDTO dto) {
        Vendors vendor = getVendor();
        Units unit = getUnit(vendor);

        BoatTypes boatType = POJOUtils.asOther(dto, BoatTypes.class);
        boatType.setVendorId(vendor.getId());
        if (unit != null) {
            boatType.setUnitId(unit.getId());
        }
        super.save(boatType);
        return POJOUtils.asOther(boatType, BaseBoatTypesVO.class);
    }

    /**
     * 检查供应商是否有权限操作船舶类型
     *
     * @param id 船舶类型 ID
     * @throws RuntimeException 当船舶类型不存在或无权限时抛出
     */
    private void vendorCheckBoatTypeExist(Long id) {
        BoatTypes boatType = super.getById(id);
        if (boatType == null) {
            throw new RuntimeException("船舶类型不存在");
        }

        // 必须属于当前供应商
        if (!boatType.getVendorId().equals(getVendor().getId())) {
            throw new RuntimeException("无权限操作");
        }
    }

    /**
     * 更新供应商船舶类型
     *
     * @param id  船舶类型 ID
     * @param dto 船舶类型数据传输对象
     * @return 更新后的船舶类型视图对象
     */
    public BaseBoatTypesVO vendorUpdateBoatType(Long id, BaseBoatTypesDTO dto) {
        vendorCheckBoatTypeExist(id);
        BoatTypes boatType = POJOUtils.asOther(dto, BoatTypes.class);
        boatType.setId(id);
        super.updateById(boatType);
        return POJOUtils.asOther(super.getById(id), BaseBoatTypesVO.class);
    }

    /**
     * 删除供应商船舶类型
     *
     * @param id 船舶类型 ID
     */
    public void vendorDeleteBoatType(Long id) {
        vendorCheckBoatTypeExist(id);
        super.removeById(id);
    }
}
