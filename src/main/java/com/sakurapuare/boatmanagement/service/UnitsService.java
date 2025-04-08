package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseUnitsDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Units;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseUnitsVO;
import com.sakurapuare.boatmanagement.service.base.BaseUnitsService;
import com.sakurapuare.boatmanagement.utils.POJOUtils;
import com.sakurapuare.boatmanagement.utils.ParamsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.UNITS;

@Service
@RequiredArgsConstructor
public class UnitsService extends BaseUnitsService {

    /**
     * 管理员搜索字段
     */
    private static final QueryColumn[] SEARCH_FIELDS = {
            UNITS.ID,
            UNITS.NAME,
            UNITS.UNIT_NAME,
            UNITS.TYPES,
            UNITS.STATUS,
            UNITS.LEGAL_PERSON,
            UNITS.CONTACT_PHONE,
            UNITS.SOCIAL_CREDIT_CODE
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
     * 获取单位列表
     *
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 单位视图对象列表
     */
    public List<BaseUnitsVO> adminGetUnitList(
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseUnitsDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, Units.class);
        adminParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.listAs(queryWrapper, BaseUnitsVO.class);
    }

    /**
     * 分页获取单位列表
     *
     * @param pageNum          页码
     * @param pageSize         每页大小
     * @param search           搜索关键词
     * @param sort             排序方式
     * @param startDateTime    开始时间
     * @param endDateTime      结束时间
     * @param filter           过滤条件
     * @return 分页单位视图对象
     */
    public Page<BaseUnitsVO> adminGetUnitPage(
            Integer pageNum,
            Integer pageSize,
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseUnitsDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, Units.class);
        adminParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseUnitsVO.class);
    }

    /**
     * 根据ID获取单位
     *
     * @param ids 单位ID字符串，多个ID用逗号分隔
     * @return 单位视图对象列表
     * @throws RuntimeException 当单位不存在时抛出
     */
    public List<BaseUnitsVO> adminGetUnitByIds(String ids) {
        List<Long> idList = ParamsUtils.getListFromIds(ids);

        if (idList.isEmpty()) {
            throw new RuntimeException("单位不存在");
        }

        List<Units> units = POJOUtils.getListFromIds(idList, super::getById);

        return POJOUtils.asOtherList(units, BaseUnitsVO.class);
    }

    /**
     * 获取单位详情
     *
     * @param id 单位ID
     * @return 单位视图对象
     * @throws RuntimeException 当单位不存在时抛出
     */
    public BaseUnitsVO adminGetUnit(Long id) {
        Units unit = super.getById(id);
        if (unit == null) {
            throw new RuntimeException("单位不存在");
        }
        return POJOUtils.asOther(unit, BaseUnitsVO.class);
    }

    /**
     * 创建单位
     *
     * @param dto 单位数据传输对象
     * @return 创建的单位视图对象
     */
    public BaseUnitsVO adminCreateUnit(BaseUnitsDTO dto) {
        Units unit = POJOUtils.asOther(dto, Units.class);
        super.save(unit);
        return POJOUtils.asOther(unit, BaseUnitsVO.class);
    }

    /**
     * 检查单位是否存在
     *
     * @param id 单位ID
     * @throws RuntimeException 当单位不存在时抛出
     */
    private void checkUnitExist(Long id) {
        if (super.getById(id) == null) {
            throw new RuntimeException("单位不存在");
        }
    }

    /**
     * 更新单位信息
     *
     * @param id  单位ID
     * @param dto 单位数据传输对象
     * @return 更新后的单位视图对象
     */
    public BaseUnitsVO adminUpdateUnit(Long id, BaseUnitsDTO dto) {
        checkUnitExist(id);
        Units unit = POJOUtils.asOther(dto, Units.class);
        unit.setId(id);
        super.updateById(unit);
        return POJOUtils.asOther(super.getById(id), BaseUnitsVO.class);
    }

    /**
     * 删除单位
     *
     * @param id 单位ID
     */
    public void adminDeleteUnit(Long id) {
        checkUnitExist(id);
        super.removeById(id);
    }
}
