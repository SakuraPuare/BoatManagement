package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseVendorsDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Vendors;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseVendorsVO;
import com.sakurapuare.boatmanagement.service.base.BaseVendorsService;
import com.sakurapuare.boatmanagement.utils.POJOUtils;
import com.sakurapuare.boatmanagement.utils.ParamsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.VENDORS;

@Service
@RequiredArgsConstructor
public class VendorsService extends BaseVendorsService {

    /**
     * 管理员搜索字段
     */
    private static final QueryColumn[] SEARCH_FIELDS = {
            VENDORS.ID,
            VENDORS.STATUS,
            VENDORS.USER_ID,
            VENDORS.UNIT_ID
    };

    /**
     * 解析管理员供应商查询参数
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
     * 获取管理员供应商列表
     *
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 供应商视图对象列表
     */
    public List<BaseVendorsVO> adminGetVendorList(
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseVendorsDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, Vendors.class);
        adminParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.listAs(queryWrapper, BaseVendorsVO.class);
    }

    /**
     * 分页获取管理员供应商列表
     *
     * @param pageNum       页码
     * @param pageSize      每页大小
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 分页供应商视图对象
     */
    public Page<BaseVendorsVO> adminGetVendorPage(
            Integer pageNum,
            Integer pageSize,
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseVendorsDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, Vendors.class);
        adminParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseVendorsVO.class);
    }

    /**
     * 根据ID获取管理员供应商
     *
     * @param ids 供应商ID字符串，多个ID用逗号分隔
     * @return 供应商视图对象列表
     * @throws RuntimeException 当供应商不存在时抛出
     */
    public List<BaseVendorsVO> adminGetVendorByIds(String ids) {
        List<Long> idList = ParamsUtils.getListFromIds(ids);

        if (idList.isEmpty()) {
            throw new RuntimeException("供应商不存在");
        }

        List<Vendors> vendors = POJOUtils.getListFromIds(idList, super::getById);

        return POJOUtils.asOtherList(vendors, BaseVendorsVO.class);
    }

    /**
     * 根据ID获取单个供应商
     *
     * @param id 供应商ID
     * @return 供应商视图对象
     * @throws RuntimeException 当供应商不存在时抛出
     */
    public BaseVendorsVO adminGetVendorById(Long id) {
        Vendors vendor = super.getById(id);
        if (vendor == null) {
            throw new RuntimeException("供应商不存在");
        }
        return POJOUtils.asOther(vendor, BaseVendorsVO.class);
    }

    /**
     * 创建供应商
     *
     * @param dto 供应商数据传输对象
     * @return 创建的供应商视图对象
     */
    public BaseVendorsVO adminCreateVendor(BaseVendorsDTO dto) {
        Vendors vendor = POJOUtils.asOther(dto, Vendors.class);
        super.save(vendor);
        return POJOUtils.asOther(vendor, BaseVendorsVO.class);
    }

    /**
     * 更新供应商信息
     *
     * @param id  供应商ID
     * @param dto 供应商数据传输对象
     * @return 更新后的供应商视图对象
     * @throws RuntimeException 当供应商不存在时抛出
     */
    public BaseVendorsVO adminUpdateVendor(Long id, BaseVendorsDTO dto) {
        Vendors vendor = super.getById(id);
        if (vendor == null) {
            throw new RuntimeException("供应商不存在");
        }
        
        Vendors updatedVendor = POJOUtils.asOther(dto, Vendors.class);
        updatedVendor.setId(id);
        super.updateById(updatedVendor);
        return POJOUtils.asOther(updatedVendor, BaseVendorsVO.class);
    }

    /**
     * 删除供应商
     *
     * @param id 供应商ID
     * @throws RuntimeException 当供应商不存在时抛出
     */
    public void adminDeleteVendor(Long id) {
        Vendors vendor = super.getById(id);
        if (vendor == null) {
            throw new RuntimeException("供应商不存在");
        }
        super.removeById(id);
    }
}
