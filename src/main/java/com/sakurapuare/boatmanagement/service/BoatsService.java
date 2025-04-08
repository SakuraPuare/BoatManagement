package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.common.context.UserContext;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseBoatsDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Boats;
import com.sakurapuare.boatmanagement.pojo.entity.Units;
import com.sakurapuare.boatmanagement.pojo.entity.Vendors;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseBoatsVO;
import com.sakurapuare.boatmanagement.service.base.BaseBoatsService;
import com.sakurapuare.boatmanagement.utils.POJOUtils;
import com.sakurapuare.boatmanagement.utils.ParamsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.BOATS;
import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.VENDORS;

@Service
@RequiredArgsConstructor
public class BoatsService extends BaseBoatsService {

    private final VendorsService vendorsService;
    private final UnitsService unitsService;
    
    /**
     * 管理员搜索字段
     */
    private static final QueryColumn[] SEARCH_FIELDS = {
            BOATS.ID,
            BOATS.NAME,
            BOATS.TYPE_ID,
            BOATS.DOCK_ID
    };

    /*
     * 管理员的函数
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
     * 获取船舶列表
     *
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 船舶视图对象列表
     */
    public List<BaseBoatsVO> adminGetBoatList(
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseBoatsDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, Boats.class);
        adminParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.listAs(queryWrapper, BaseBoatsVO.class);
    }

    /**
     * 分页获取船舶列表
     *
     * @param pageNum          页码
     * @param pageSize         每页大小
     * @param search           搜索关键词
     * @param sort             排序方式
     * @param startDateTime    开始时间
     * @param endDateTime      结束时间
     * @param filter           过滤条件
     * @return 分页船舶视图对象
     */
    public Page<BaseBoatsVO> adminGetBoatPage(
            Integer pageNum,
            Integer pageSize,
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseBoatsDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, Boats.class);
        adminParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseBoatsVO.class);
    }

    /**
     * 根据ID获取船舶
     *
     * @param ids 船舶ID字符串，多个ID用逗号分隔
     * @return 船舶视图对象列表
     * @throws RuntimeException 当船舶不存在时抛出
     */
    public List<BaseBoatsVO> adminGetBoatByIds(String ids) {
        List<Long> idList = ParamsUtils.getListFromIds(ids);

        if (idList.isEmpty()) {
            throw new RuntimeException("船舶不存在");
        }

        List<Boats> boats = POJOUtils.getListFromIds(idList, super::getById);

        return POJOUtils.asOtherList(boats, BaseBoatsVO.class);
    }

    /**
     * 创建船舶
     *
     * @param dto 船舶数据传输对象
     * @return 创建的船舶视图对象
     */
    public BaseBoatsVO adminCreateBoat(BaseBoatsDTO dto) {
        Boats boat = POJOUtils.asOther(dto, Boats.class);
        super.save(boat);
        return POJOUtils.asOther(boat, BaseBoatsVO.class);
    }

    /**
     * 检查船舶是否存在
     *
     * @param id 船舶ID
     * @throws RuntimeException 当船舶不存在时抛出
     */
    private void checkBoatExist(Long id) {
        if (super.getById(id) == null) {
            throw new RuntimeException("船舶不存在");
        }
    }

    /**
     * 更新船舶信息
     *
     * @param id  船舶ID
     * @param dto 船舶数据传输对象
     * @return 更新后的船舶视图对象
     */
    public BaseBoatsVO adminUpdateBoat(Long id, BaseBoatsDTO dto) {
        checkBoatExist(id);
        Boats boat = POJOUtils.asOther(dto, Boats.class);
        boat.setId(id);
        super.updateById(boat);
        return POJOUtils.asOther(super.getById(id), BaseBoatsVO.class);
    }

    /**
     * 删除船舶
     *
     * @param id 船舶ID
     */
    public void adminDeleteBoat(Long id) {
        checkBoatExist(id);
        super.removeById(id);
    }

    /*
     * 供应商的函数
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
            queryWrapper.where(BOATS.VENDOR_ID.eq(vendor.getId()));
        } else {
            queryWrapper.where(BOATS.UNIT_ID.eq(unit.getId()));
        }

        POJOUtils.search(queryWrapper, SEARCH_FIELDS, search);
        POJOUtils.dateRange(queryWrapper, startDateTime, endDateTime);
        POJOUtils.sort(queryWrapper, ParamsUtils.getSortFromParams(sort));
    }

    /**
     * 获取供应商船舶列表
     *
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 船舶视图对象列表
     */
    public List<BaseBoatsVO> vendorGetBoatList(
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseBoatsDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, Boats.class);
        vendorParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.listAs(queryWrapper, BaseBoatsVO.class);
    }

    /**
     * 分页获取供应商船舶列表
     *
     * @param pageNum          页码
     * @param pageSize         每页大小
     * @param search           搜索关键词
     * @param sort             排序方式
     * @param startDateTime    开始时间
     * @param endDateTime      结束时间
     * @param filter           过滤条件
     * @return 分页船舶视图对象
     */
    public Page<BaseBoatsVO> vendorGetBoatPage(
            Integer pageNum,
            Integer pageSize,
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseBoatsDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, Boats.class);
        vendorParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseBoatsVO.class);
    }

    /**
     * 根据ID获取供应商船舶
     *
     * @param ids 船舶ID字符串，多个ID用逗号分隔
     * @return 船舶视图对象列表
     * @throws RuntimeException 当船舶不存在或无权限时抛出
     */
    public List<BaseBoatsVO> vendorGetBoatByIds(String ids) {
        List<Long> idList = ParamsUtils.getListFromIds(ids);

        if (idList.isEmpty()) {
            throw new RuntimeException("船舶不存在");
        }

        Vendors vendor = getVendor();
        List<Boats> boats = POJOUtils.getListFromIds(idList, id -> {
            Boats boat = super.getById(id);
            if (boat != null && !boat.getVendorId().equals(vendor.getId())) {
                throw new RuntimeException("无权限操作");
            }
            return boat;
        });

        return POJOUtils.asOtherList(boats, BaseBoatsVO.class);
    }

    /**
     * 创建供应商船舶
     *
     * @param dto 船舶数据传输对象
     * @return 创建的船舶视图对象
     */
    public BaseBoatsVO vendorCreateBoat(BaseBoatsDTO dto) {
        Vendors vendor = getVendor();
        Units unit = getUnit(vendor);

        Boats boat = POJOUtils.asOther(dto, Boats.class);
        boat.setVendorId(vendor.getId());
        if (unit != null) {
            boat.setUnitId(unit.getId());
        }
        super.save(boat);
        return POJOUtils.asOther(boat, BaseBoatsVO.class);
    }

    /**
     * 检查供应商是否有权限操作船舶
     *
     * @param id 船舶ID
     * @throws RuntimeException 当船舶不存在或无权限时抛出
     */
    private void vendorCheckBoatExist(Long id) {
        Boats boat = super.getById(id);
        if (boat == null) {
            throw new RuntimeException("船舶不存在");
        }

        // 必须属于当前供应商
        if (!boat.getVendorId().equals(getVendor().getId())) {
            throw new RuntimeException("无权限操作");
        }
    }

    /**
     * 更新供应商船舶
     *
     * @param id  船舶ID
     * @param dto 船舶数据传输对象
     * @return 更新后的船舶视图对象
     */
    public BaseBoatsVO vendorUpdateBoat(Long id, BaseBoatsDTO dto) {
        vendorCheckBoatExist(id);
        Boats boat = POJOUtils.asOther(dto, Boats.class);
        boat.setId(id);
        super.updateById(boat);
        return POJOUtils.asOther(super.getById(id), BaseBoatsVO.class);
    }

    /**
     * 删除供应商船舶
     *
     * @param id 船舶ID
     */
    public void vendorDeleteBoat(Long id) {
        vendorCheckBoatExist(id);
        super.removeById(id);
    }
}
