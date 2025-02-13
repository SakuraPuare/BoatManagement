package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseDocksDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Docks;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseDocksVO;
import com.sakurapuare.boatmanagement.service.base.impl.BaseDocksServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocksService extends BaseDocksServiceImpl {

    /*
     * 管理员函数
     */

    public List<BaseDocksVO> getListQuery(BaseDocksDTO queryDTO) {
        Docks query = new Docks();
        BeanUtils.copyProperties(queryDTO, query);

        QueryWrapper queryWrapper = QueryWrapper.create(query);
        return super.listAs(queryWrapper, BaseDocksVO.class);
    }

    public Page<BaseDocksVO> getPageQuery(Integer pageNum, Integer pageSize, BaseDocksDTO queryDTO) {
        Docks query = new Docks();
        BeanUtils.copyProperties(queryDTO, query);

        QueryWrapper queryWrapper = QueryWrapper.create(query);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseDocksVO.class);
    }

    public void addDocks(BaseDocksDTO docksDTO) {
        Docks docks = new Docks();
        BeanUtils.copyProperties(docksDTO, docks);
        super.save(docks);
    }

    public void deleteDocks(Long id) {
        super.removeById(id);
    }

    public void updateDocks(Long id, BaseDocksDTO docksDTO) {
        Docks docks = new Docks();
        BeanUtils.copyProperties(docksDTO, docks);
        docks.setId(id);
        super.updateById(docks);
    }
}
