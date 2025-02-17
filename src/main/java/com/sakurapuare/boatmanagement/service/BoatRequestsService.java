package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.common.context.UserContext;
import com.sakurapuare.boatmanagement.constant.OrderStatus;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseBoatRequestsDTO;
import com.sakurapuare.boatmanagement.pojo.entity.BoatRequests;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseBoatRequestsVO;
import com.sakurapuare.boatmanagement.service.base.impl.BaseBoatRequestsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.BOAT_REQUESTS;

@Service
@RequiredArgsConstructor
public class BoatRequestsService extends BaseBoatRequestsServiceImpl {

    /*
     * 商家函数
     */

    private QueryWrapper getVendorBoatRequestsQueryWrapper(BaseBoatRequestsDTO boatRequestDTO) {
        BoatRequests query = new BoatRequests();
        BeanUtils.copyProperties(boatRequestDTO, query);
        return QueryWrapper.create(query);
    }

    public List<BaseBoatRequestsVO> getVendorBoatRequestsListQuery(BaseBoatRequestsDTO boatRequestDTO) {
        QueryWrapper queryWrapper = getVendorBoatRequestsQueryWrapper(boatRequestDTO);
        return super.listAs(queryWrapper, BaseBoatRequestsVO.class);
    }

    public Page<BaseBoatRequestsVO> getVendorBoatRequestsPageQuery(Integer pageNum, Integer pageSize, BaseBoatRequestsDTO boatRequestDTO) {
        QueryWrapper queryWrapper = getVendorBoatRequestsQueryWrapper(boatRequestDTO);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseBoatRequestsVO.class);
    }

    /*
     * 用户函数
     */

    private QueryWrapper getUserBoatRequestsQueryWrapper(BaseBoatRequestsDTO boatRequestDTO) {
        BoatRequests boatRequest = new BoatRequests();
        BeanUtils.copyProperties(boatRequestDTO, boatRequest);
        QueryWrapper queryWrapper = QueryWrapper.create(boatRequest);
        queryWrapper.where(BOAT_REQUESTS.USER_ID.eq(UserContext.getAccount().getId()));
        return queryWrapper;
    }

    public List<BaseBoatRequestsVO> getUserBoatRequestsListQuery(BaseBoatRequestsDTO boatRequestDTO) {
        QueryWrapper queryWrapper = getUserBoatRequestsQueryWrapper(boatRequestDTO);
        return super.listAs(queryWrapper, BaseBoatRequestsVO.class);
    }

    public Page<BaseBoatRequestsVO> getUserBoatRequestsPageQuery(Integer pageNum, Integer pageSize, BaseBoatRequestsDTO boatRequestDTO) {
        QueryWrapper queryWrapper = getUserBoatRequestsQueryWrapper(boatRequestDTO);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseBoatRequestsVO.class);
    }

    public void createUserBoatRequest(BaseBoatRequestsDTO boatRequestDTO) {
        BoatRequests boatRequest = new BoatRequests();
        BeanUtils.copyProperties(boatRequestDTO, boatRequest);
        boatRequest.setUserId(UserContext.getAccount().getId());
        boatRequest.setStatus(OrderStatus.PENDING);
        super.save(boatRequest);
    }

    private void verifyBoatRequestId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id不能为空");
        }

        BoatRequests boatRequest = super.getById(id);
        if (boatRequest == null) {
            throw new IllegalArgumentException("请求不存在");
        }

        if (!Objects.equals(boatRequest.getUserId(), UserContext.getAccount().getId())) {
            throw new IllegalArgumentException("请求不存在");
        }
    }

    public void cancelUserBoatRequest(Long id) {
        verifyBoatRequestId(id);
        BoatRequests boatRequest = super.getById(id);
        boatRequest.setStatus(OrderStatus.CANCELLED);
        super.updateById(boatRequest);
    }

    public void updateUserBoatRequest(Long id, BaseBoatRequestsDTO boatRequestDTO) {
        verifyBoatRequestId(id);
        BoatRequests boatRequest = super.getById(id);
        BeanUtils.copyProperties(boatRequestDTO, boatRequest);
        super.updateById(boatRequest);
    }
}
