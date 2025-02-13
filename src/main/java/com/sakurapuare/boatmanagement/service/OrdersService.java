package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseOrdersDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Orders;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseOrdersVO;
import com.sakurapuare.boatmanagement.service.base.impl.BaseOrdersServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersService extends BaseOrdersServiceImpl {

    /*
     * 管理员函数
     */

    public List<BaseOrdersVO> getListQuery(BaseOrdersDTO queryDTO) {
        Orders query = new Orders();
        BeanUtils.copyProperties(queryDTO, query);

        QueryWrapper queryWrapper = QueryWrapper.create(query);
        return super.listAs(queryWrapper, BaseOrdersVO.class);
    }

    public Page<BaseOrdersVO> getPageQuery(Integer pageNum, Integer pageSize, BaseOrdersDTO queryDTO) {
        Orders query = new Orders();
        BeanUtils.copyProperties(queryDTO, query);

        QueryWrapper queryWrapper = QueryWrapper.create(query);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseOrdersVO.class);
    }


}
