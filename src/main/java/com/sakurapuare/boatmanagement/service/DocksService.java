package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseDocksDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Docks;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseDocksVO;
import com.sakurapuare.boatmanagement.service.base.BaseDocksService;
import com.sakurapuare.boatmanagement.utils.POJOUtils;
import com.sakurapuare.boatmanagement.utils.ParamsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.DOCKS;

@Service
@RequiredArgsConstructor
public class DocksService extends BaseDocksService {

    /**
     * 搜索字段
     */
    private static final QueryColumn[] SEARCH_FIELDS = {
            DOCKS.ID,
            DOCKS.NAME,
            DOCKS.ADDRESS,
            DOCKS.CONTACT_PHONE
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
     * 获取码头列表
     *
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 码头视图对象列表
     */
    public List<BaseDocksVO> adminGetDockList(
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseDocksDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, Docks.class);
        adminParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.listAs(queryWrapper, BaseDocksVO.class);
    }

    /**
     * 分页获取码头列表
     *
     * @param pageNum          页码
     * @param pageSize         每页大小
     * @param search           搜索关键词
     * @param sort             排序方式
     * @param startDateTime    开始时间
     * @param endDateTime      结束时间
     * @param filter           过滤条件
     * @return 分页码头视图对象
     */
    public Page<BaseDocksVO> adminGetDockPage(
            Integer pageNum,
            Integer pageSize,
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseDocksDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, Docks.class);
        adminParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseDocksVO.class);
    }

    /**
     * 根据ID获取码头
     *
     * @param ids 码头ID字符串，多个ID用逗号分隔
     * @return 码头视图对象列表
     * @throws RuntimeException 当码头不存在时抛出
     */
    public List<BaseDocksVO> adminGetDockByIds(String ids) {
        List<Long> idList = ParamsUtils.getListFromIds(ids);

        if (idList.isEmpty()) {
            throw new RuntimeException("码头不存在");
        }

        List<Docks> docks = POJOUtils.getListFromIds(idList, super::getById);

        return POJOUtils.asOtherList(docks, BaseDocksVO.class);
    }

    /**
     * 创建码头
     *
     * @param dto 码头数据传输对象
     * @return 创建的码头视图对象
     */
    public BaseDocksVO adminCreateDock(BaseDocksDTO dto) {
        Docks dock = POJOUtils.asOther(dto, Docks.class);
        super.save(dock);
        return POJOUtils.asOther(dock, BaseDocksVO.class);
    }

    /**
     * 检查码头是否存在
     *
     * @param id 码头ID
     * @throws RuntimeException 当码头不存在时抛出
     */
    private void checkDockExist(Long id) {
        if (super.getById(id) == null) {
            throw new RuntimeException("码头不存在");
        }
    }

    /**
     * 更新码头信息
     *
     * @param id  码头ID
     * @param dto 码头数据传输对象
     * @return 更新后的码头视图对象
     */
    public BaseDocksVO adminUpdateDock(Long id, BaseDocksDTO dto) {
        checkDockExist(id);
        Docks dock = POJOUtils.asOther(dto, Docks.class);
        dock.setId(id);
        super.updateById(dock);
        return POJOUtils.asOther(super.getById(id), BaseDocksVO.class);
    }

    /**
     * 删除码头
     *
     * @param id 码头ID
     */
    public void adminDeleteDock(Long id) {
        checkDockExist(id);
        super.removeById(id);
    }

    /*
     * 供应商函数
     */

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
        queryWrapper.where(DOCKS.IS_ENABLED.eq(true));
        POJOUtils.search(queryWrapper, SEARCH_FIELDS, search);
        POJOUtils.dateRange(queryWrapper, startDateTime, endDateTime);
        POJOUtils.sort(queryWrapper, ParamsUtils.getSortFromParams(sort));
    }

    /**
     * 获取供应商码头列表
     *
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 码头视图对象列表
     */
    public List<BaseDocksVO> vendorGetDockList(
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseDocksDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, Docks.class);
        vendorParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.listAs(queryWrapper, BaseDocksVO.class);
    }

    /**
     * 分页获取供应商码头列表
     *
     * @param pageNum          页码
     * @param pageSize         每页大小
     * @param search           搜索关键词
     * @param sort             排序方式
     * @param startDateTime    开始时间
     * @param endDateTime      结束时间
     * @param filter           过滤条件
     * @return 分页码头视图对象
     */
    public Page<BaseDocksVO> vendorGetDockPage(
            Integer pageNum,
            Integer pageSize,
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseDocksDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, Docks.class);
        vendorParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseDocksVO.class);
    }

    /**
     * 根据ID获取供应商码头
     *
     * @param ids 码头ID字符串，多个ID用逗号分隔
     * @return 码头视图对象列表
     * @throws RuntimeException 当码头不存在时抛出
     */
    public List<BaseDocksVO> vendorGetDockByIds(String ids) {
        List<Long> idList = ParamsUtils.getListFromIds(ids);

        if (idList.isEmpty()) {
            throw new RuntimeException("码头不存在");
        }

        List<Docks> docks = POJOUtils.getListFromIds(idList, id -> {
            Docks dock = super.getById(id);
            if (dock != null && !dock.getIsEnabled()) {
                throw new RuntimeException("码头未启用");
            }
            return dock;
        });

        return POJOUtils.asOtherList(docks, BaseDocksVO.class);
    }

    /*
     * 用户函数
     */

    /**
     * 解析用户查询参数
     *
     * @param queryWrapper  查询包装器
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     */
    private void userParseParams(QueryWrapper queryWrapper, String search, String sort, String startDateTime,
                                String endDateTime) {
        queryWrapper.where(DOCKS.IS_ENABLED.eq(true));
        POJOUtils.search(queryWrapper, SEARCH_FIELDS, search);
        POJOUtils.dateRange(queryWrapper, startDateTime, endDateTime);
        POJOUtils.sort(queryWrapper, ParamsUtils.getSortFromParams(sort));
    }

    /**
     * 获取用户码头列表
     *
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 码头视图对象列表
     */
    public List<BaseDocksVO> userGetDockList(
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseDocksDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, Docks.class);
        userParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.listAs(queryWrapper, BaseDocksVO.class);
    }

    /**
     * 分页获取用户码头列表
     *
     * @param pageNum          页码
     * @param pageSize         每页大小
     * @param search           搜索关键词
     * @param sort             排序方式
     * @param startDateTime    开始时间
     * @param endDateTime      结束时间
     * @param filter           过滤条件
     * @return 分页码头视图对象
     */
    public Page<BaseDocksVO> userGetDockPage(
            Integer pageNum,
            Integer pageSize,
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseDocksDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, Docks.class);
        userParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseDocksVO.class);
    }

    /**
     * 根据ID获取用户码头
     *
     * @param ids 码头ID字符串，多个ID用逗号分隔
     * @return 码头视图对象列表
     * @throws RuntimeException 当码头不存在时抛出
     */
    public List<BaseDocksVO> userGetDockByIds(String ids) {
        List<Long> idList = ParamsUtils.getListFromIds(ids);

        if (idList.isEmpty()) {
            throw new RuntimeException("码头不存在");
        }

        List<Docks> docks = POJOUtils.getListFromIds(idList, id -> {
            Docks dock = super.getById(id);
            if (dock != null && !dock.getIsEnabled()) {
                throw new RuntimeException("码头未启用");
            }
            return dock;
        });

        return POJOUtils.asOtherList(docks, BaseDocksVO.class);
    }

    /*
     * 公共函数（不需要登录）
     */

    /**
     * 解析公共查询参数
     *
     * @param queryWrapper  查询包装器
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     */
    private void publicParseParams(QueryWrapper queryWrapper, String search, String sort, String startDateTime,
                                  String endDateTime) {
        queryWrapper.where(DOCKS.IS_ENABLED.eq(true));
        POJOUtils.search(queryWrapper, SEARCH_FIELDS, search);
        POJOUtils.dateRange(queryWrapper, startDateTime, endDateTime);
        POJOUtils.sort(queryWrapper, ParamsUtils.getSortFromParams(sort));
    }

    /**
     * 获取公共码头列表
     *
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 码头视图对象列表
     */
    public List<BaseDocksVO> publicGetDockList(
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseDocksDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, Docks.class);
        publicParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.listAs(queryWrapper, BaseDocksVO.class);
    }

    /**
     * 分页获取公共码头列表
     *
     * @param pageNum          页码
     * @param pageSize         每页大小
     * @param search           搜索关键词
     * @param sort             排序方式
     * @param startDateTime    开始时间
     * @param endDateTime      结束时间
     * @param filter           过滤条件
     * @return 分页码头视图对象
     */
    public Page<BaseDocksVO> publicGetDockPage(
            Integer pageNum,
            Integer pageSize,
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseDocksDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, Docks.class);
        publicParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseDocksVO.class);
    }

    /**
     * 根据ID获取公共码头
     *
     * @param ids 码头ID字符串，多个ID用逗号分隔
     * @return 码头视图对象列表
     * @throws RuntimeException 当码头不存在时抛出
     */
    public List<BaseDocksVO> publicGetDockByIds(String ids) {
        List<Long> idList = ParamsUtils.getListFromIds(ids);

        if (idList.isEmpty()) {
            throw new RuntimeException("码头不存在");
        }

        List<Docks> docks = POJOUtils.getListFromIds(idList, id -> {
            Docks dock = super.getById(id);
            if (dock != null && !dock.getIsEnabled()) {
                throw new RuntimeException("码头未启用");
            }
            return dock;
        });

        return POJOUtils.asOtherList(docks, BaseDocksVO.class);
    }
}
