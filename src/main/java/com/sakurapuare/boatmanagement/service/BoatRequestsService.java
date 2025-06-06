package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.common.context.UserContext;
import com.sakurapuare.boatmanagement.constant.OrderStatus;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseBoatRequestsDTO;
import com.sakurapuare.boatmanagement.pojo.entity.BoatRequests;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseBoatRequestsVO;
import com.sakurapuare.boatmanagement.service.base.BaseBoatRequestsService;
import com.sakurapuare.boatmanagement.utils.POJOUtils;
import com.sakurapuare.boatmanagement.utils.ParamsUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.BOAT_REQUESTS;

@Service
@RequiredArgsConstructor
public class BoatRequestsService extends BaseBoatRequestsService {

    /**
     * 搜索字段
     */
    private static final QueryColumn[] SEARCH_FIELDS = {
            BOAT_REQUESTS.ID,
            BOAT_REQUESTS.USER_ID,
            BOAT_REQUESTS.ORDER_ID,
            BOAT_REQUESTS.START_DOCK_ID,
            BOAT_REQUESTS.END_DOCK_ID,
            BOAT_REQUESTS.STATUS,
            BOAT_REQUESTS.TYPE
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
     * 管理员获取船只请求列表
     *
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 船只请求视图对象列表
     */
    public List<BaseBoatRequestsVO> adminGetBoatRequestList(
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseBoatRequestsDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, BoatRequests.class);
        adminParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.listAs(queryWrapper, BaseBoatRequestsVO.class);
    }

    /**
     * 管理员分页获取船只请求列表
     *
     * @param pageNum       页码
     * @param pageSize      每页大小
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 分页船只请求视图对象
     */
    public Page<BaseBoatRequestsVO> adminGetBoatRequestPage(
            Integer pageNum,
            Integer pageSize,
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseBoatRequestsDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, BoatRequests.class);
        adminParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseBoatRequestsVO.class);
    }

    /**
     * 管理员根据 ID 获取船只请求
     *
     * @param ids 船只请求 ID 字符串，多个 ID 用逗号分隔
     * @return 船只请求视图对象列表
     */
    public List<BaseBoatRequestsVO> adminGetBoatRequestByIds(String ids) {
        List<Long> idList = ParamsUtils.getListFromIds(ids);

        if (idList.isEmpty()) {
            return List.of();
        }

        List<BoatRequests> requests = POJOUtils.getListFromIds(idList, super::getById);
        return POJOUtils.asOtherList(requests, BaseBoatRequestsVO.class);
    }

    /**
     * 管理员创建船只请求
     *
     * @param dto 船只请求数据传输对象
     * @return 创建的船只请求视图对象
     */
    public BaseBoatRequestsVO adminCreateBoatRequest(BaseBoatRequestsDTO dto) {
        BoatRequests boatRequest = POJOUtils.asOther(dto, BoatRequests.class);
        super.save(boatRequest);
        return POJOUtils.asOther(boatRequest, BaseBoatRequestsVO.class);
    }

    /**
     * 检查船只请求是否存在
     *
     * @param id 船只请求 ID
     * @return 船只请求实体
     * @throws RuntimeException 当船只请求不存在时抛出
     */
    private BoatRequests checkAdminBoatRequestExist(Long id) {
        if (id == null) {
            throw new RuntimeException("请求 ID 不能为空");
        }

        BoatRequests boatRequest = super.getById(id);
        if (boatRequest == null) {
            throw new RuntimeException("船只请求不存在");
        }
        
        return boatRequest;
    }

    /**
     * 管理员更新船只请求
     *
     * @param id  船只请求 ID
     * @param dto 船只请求数据传输对象
     * @return 更新后的船只请求视图对象
     */
    public BaseBoatRequestsVO adminUpdateBoatRequest(Long id, BaseBoatRequestsDTO dto) {
        checkAdminBoatRequestExist(id);
        BoatRequests boatRequest = POJOUtils.asOther(dto, BoatRequests.class);
        boatRequest.setId(id);
        super.updateById(boatRequest);
        return POJOUtils.asOther(super.getById(id), BaseBoatRequestsVO.class);
    }

    /**
     * 管理员删除船只请求
     *
     * @param id 船只请求 ID
     */
    public void adminDeleteBoatRequest(Long id) {
        checkAdminBoatRequestExist(id);
        super.removeById(id);
    }

    /*
     * 供应商函数
     */

    /**
     * 解析商家查询参数
     *
     * @param queryWrapper  查询包装器
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     */
    private void vendorParseParams(QueryWrapper queryWrapper, String search, String sort, String startDateTime,
                                   String endDateTime) {
        POJOUtils.search(queryWrapper, SEARCH_FIELDS, search);
        POJOUtils.dateRange(queryWrapper, startDateTime, endDateTime);
        POJOUtils.sort(queryWrapper, ParamsUtils.getSortFromParams(sort));
    }

    /**
     * 商家获取船只请求列表
     *
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 船只请求视图对象列表
     */
    public List<BaseBoatRequestsVO> vendorGetBoatRequestList(
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseBoatRequestsDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, BoatRequests.class);
        vendorParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.listAs(queryWrapper, BaseBoatRequestsVO.class);
    }

    /**
     * 商家分页获取船只请求列表
     *
     * @param pageNum       页码
     * @param pageSize      每页大小
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 分页船只请求视图对象
     */
    public Page<BaseBoatRequestsVO> vendorGetBoatRequestPage(
            Integer pageNum,
            Integer pageSize,
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseBoatRequestsDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, BoatRequests.class);
        vendorParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseBoatRequestsVO.class);
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
        POJOUtils.search(queryWrapper, SEARCH_FIELDS, search);
        POJOUtils.dateRange(queryWrapper, startDateTime, endDateTime);
        POJOUtils.sort(queryWrapper, ParamsUtils.getSortFromParams(sort));
        // 只查询当前用户的请求
        queryWrapper.where(BOAT_REQUESTS.USER_ID.eq(UserContext.getAccount().getId()));
    }

    /**
     * 用户获取船只请求列表
     *
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 船只请求视图对象列表
     */
    public List<BaseBoatRequestsVO> userGetBoatRequestList(
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseBoatRequestsDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, BoatRequests.class);
        userParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.listAs(queryWrapper, BaseBoatRequestsVO.class);
    }

    /**
     * 用户分页获取船只请求列表
     *
     * @param pageNum       页码
     * @param pageSize      每页大小
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 分页船只请求视图对象
     */
    public Page<BaseBoatRequestsVO> userGetBoatRequestPage(
            Integer pageNum,
            Integer pageSize,
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseBoatRequestsDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, BoatRequests.class);
        userParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseBoatRequestsVO.class);
    }

    /**
     * 根据 ID 获取船只请求
     *
     * @param ids 船只请求 ID 字符串，多个 ID 用逗号分隔
     * @return 船只请求视图对象列表
     */
    public List<BaseBoatRequestsVO> userGetBoatRequestByIds(String ids) {
        List<Long> idList = ParamsUtils.getListFromIds(ids);

        if (idList.isEmpty()) {
            return List.of();
        }

        List<BoatRequests> requests = POJOUtils.getListFromIds(idList, super::getById);
        // 过滤掉不属于当前用户的请求
        requests = requests.stream()
                .filter(request -> Objects.equals(request.getUserId(), UserContext.getAccount().getId()))
                .toList();

        return POJOUtils.asOtherList(requests, BaseBoatRequestsVO.class);
    }

    /**
     * 用户创建船只请求
     *
     * @param dto 船只请求数据传输对象
     * @return 创建的船只请求视图对象
     */
    public BaseBoatRequestsVO userCreateBoatRequest(BaseBoatRequestsDTO dto) {
        BoatRequests boatRequest = POJOUtils.asOther(dto, BoatRequests.class);
        boatRequest.setUserId(UserContext.getAccount().getId());
        boatRequest.setStatus(OrderStatus.PENDING);
        super.save(boatRequest);
        return POJOUtils.asOther(boatRequest, BaseBoatRequestsVO.class);
    }

    /**
     * 检查船只请求是否存在并属于当前用户
     *
     * @param id 船只请求 ID
     * @return 船只请求实体
     * @throws RuntimeException 当船只请求不存在或不属于当前用户时抛出
     */
    private BoatRequests checkUserBoatRequestExist(Long id) {
        if (id == null) {
            throw new RuntimeException("请求 ID 不能为空");
        }

        BoatRequests boatRequest = super.getById(id);
        if (boatRequest == null) {
            throw new RuntimeException("船只请求不存在");
        }

        if (!Objects.equals(boatRequest.getUserId(), UserContext.getAccount().getId())) {
            throw new RuntimeException("无权操作此船只请求");
        }
        
        return boatRequest;
    }

    /**
     * 用户取消船只请求
     *
     * @param id 船只请求 ID
     * @return 更新后的船只请求视图对象
     */
    public BaseBoatRequestsVO userCancelBoatRequest(Long id) {
        BoatRequests boatRequest = checkUserBoatRequestExist(id);
        boatRequest.setStatus(OrderStatus.CANCELLED);
        super.updateById(boatRequest);
        return POJOUtils.asOther(boatRequest, BaseBoatRequestsVO.class);
    }

    /**
     * 用户更新船只请求
     *
     * @param id  船只请求 ID
     * @param dto 船只请求数据传输对象
     * @return 更新后的船只请求视图对象
     */
    public BaseBoatRequestsVO userUpdateBoatRequest(Long id, BaseBoatRequestsDTO dto) {
        BoatRequests boatRequest = checkUserBoatRequestExist(id);
        
        // 保留原有的用户 ID 和状态
        Long userId = boatRequest.getUserId();
        String status = boatRequest.getStatus();
        
        // 更新实体
        BeanUtils.copyProperties(dto, boatRequest);
        boatRequest.setUserId(userId);
        boatRequest.setStatus(status);
        
        super.updateById(boatRequest);
        return POJOUtils.asOther(boatRequest, BaseBoatRequestsVO.class);
    }

    /**
     * 检查船只请求是否存在
     *
     * @param id 船只请求 ID
     * @return 船只请求实体
     * @throws RuntimeException 当船只请求不存在时抛出
     */
    private BoatRequests checkBoatRequestExist(Long id) {
        if (id == null) {
            throw new RuntimeException("请求 ID 不能为空");
        }

        BoatRequests boatRequest = super.getById(id);
        if (boatRequest == null) {
            throw new RuntimeException("船只请求不存在");
        }
        
        return boatRequest;
    }

    /**
     * 商家更新船只请求状态
     *
     * @param id     船只请求 ID
     * @param status 新状态
     * @return 更新后的船只请求视图对象
     */
    public BaseBoatRequestsVO vendorUpdateBoatRequestStatus(Long id, String status) {
        BoatRequests boatRequest = checkBoatRequestExist(id);
        boatRequest.setStatus(status);
        super.updateById(boatRequest);
        return POJOUtils.asOther(boatRequest, BaseBoatRequestsVO.class);
    }

    /**
     * 商家根据 ID 获取船只请求
     *
     * @param ids 船只请求 ID 字符串，多个 ID 用逗号分隔
     * @return 船只请求视图对象列表
     */
    public List<BaseBoatRequestsVO> vendorGetBoatRequestByIds(String ids) {
        List<Long> idList = ParamsUtils.getListFromIds(ids);

        if (idList.isEmpty()) {
            return List.of();
        }

        List<BoatRequests> requests = POJOUtils.getListFromIds(idList, super::getById);
        return POJOUtils.asOtherList(requests, BaseBoatRequestsVO.class);
    }
}
