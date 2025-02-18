package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.query.QueryWrapperAdapter;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseUserCertifyDTO;
import com.sakurapuare.boatmanagement.pojo.entity.UserCertify;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseUserCertifyVO;
import com.sakurapuare.boatmanagement.service.base.impl.BaseUserCertifyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCertifyService extends BaseUserCertifyServiceImpl {

    /*
     * 管理员函数
     */
    private QueryWrapper getAdminUserCertifyListQueryWrapper(BaseUserCertifyDTO queryDTO) {
        UserCertify userCertify = new UserCertify();
        BeanUtils.copyProperties(queryDTO, userCertify);
        return QueryWrapperAdapter.create(userCertify);
    }

    public List<BaseUserCertifyVO> getAdminUserCertifyList(BaseUserCertifyDTO queryDTO) {
        return super.listAs(getAdminUserCertifyListQueryWrapper(queryDTO), BaseUserCertifyVO.class);
    }

    public Page<BaseUserCertifyVO> getAdminUserCertifyPageQuery(Integer pageNum, Integer pageSize, BaseUserCertifyDTO queryDTO) {
        return super.pageAs(Page.of(pageNum, pageSize), getAdminUserCertifyListQueryWrapper(queryDTO), BaseUserCertifyVO.class);
    }

    public BaseUserCertifyVO getAdminUserCertify(Long id) {
        UserCertify userCertify = super.getById(id);
        if (userCertify == null) {
            throw new IllegalArgumentException("用户认证不存在");
        }
        BaseUserCertifyVO baseUserCertifyVO = new BaseUserCertifyVO();
        BeanUtils.copyProperties(userCertify, baseUserCertifyVO);
        return baseUserCertifyVO;
    }
}
