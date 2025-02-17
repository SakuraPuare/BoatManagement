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

import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.DOCKS;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocksService extends BaseDocksServiceImpl {

    /*
     * 管理员函数
     */

    private QueryWrapper getAdminQueryWrapper(BaseDocksDTO queryDTO) {
        Docks docks = new Docks();
        BeanUtils.copyProperties(queryDTO, docks);
        return QueryWrapper.create(docks);
    }

    public List<BaseDocksVO> getListQuery(BaseDocksDTO queryDTO) {
        QueryWrapper queryWrapper = getAdminQueryWrapper(queryDTO);

        return super.listAs(queryWrapper, BaseDocksVO.class);
    }

    public Page<BaseDocksVO> getPageQuery(Integer pageNum, Integer pageSize, BaseDocksDTO queryDTO) {
        QueryWrapper queryWrapper = getAdminQueryWrapper(queryDTO);
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

    private QueryWrapper getEnabledQueryWrapper(BaseDocksDTO queryDTO) {
        Docks docks = new Docks();
        BeanUtils.copyProperties(queryDTO, docks);
        return QueryWrapper.create(docks).eq(DOCKS.IS_ENABLED.getName(), true);
    }

    /*
     * 供应商函数
     */

    public List<BaseDocksVO> getVendorDockListQuery(BaseDocksDTO queryDTO) {
        QueryWrapper queryWrapper = getEnabledQueryWrapper(queryDTO);
        return super.listAs(queryWrapper, BaseDocksVO.class);
    }

    public Page<BaseDocksVO> getVendorDockPageQuery(Integer pageNum, Integer pageSize, BaseDocksDTO queryDTO) {
        QueryWrapper queryWrapper = getEnabledQueryWrapper(queryDTO);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseDocksVO.class);
    }

    /*
     * 用户函数
     */

    public List<BaseDocksVO> getUserDockListQuery(BaseDocksDTO queryDTO) {
        QueryWrapper queryWrapper = getEnabledQueryWrapper(queryDTO);
        return super.listAs(queryWrapper, BaseDocksVO.class);
    }

    public Page<BaseDocksVO> getUserDockPageQuery(Integer pageNum, Integer pageSize, BaseDocksDTO queryDTO) {
        QueryWrapper queryWrapper = getEnabledQueryWrapper(queryDTO);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseDocksVO.class);
    }

}
