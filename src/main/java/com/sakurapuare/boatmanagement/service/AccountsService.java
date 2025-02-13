package com.sakurapuare.boatmanagement.service;

import cn.hutool.json.JSONObject;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseAccountsDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Accounts;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseAccountsVO;
import com.sakurapuare.boatmanagement.service.base.impl.BaseAccountsServiceImpl;
import com.sakurapuare.boatmanagement.utils.JWTUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountsService extends BaseAccountsServiceImpl {

    public Accounts getAccountByToken(String token) {
        JSONObject payload = JWTUtils.parseToken(token);
        Long userId = payload.getLong("userId");
        return super.getById(userId);
    }

    /*
     * 管理员函数
     */

    public List<BaseAccountsVO> getListQuery(BaseAccountsDTO queryDTO) {
        Accounts query = new Accounts();
        BeanUtils.copyProperties(queryDTO, query);
        QueryWrapper queryWrapper = QueryWrapper.create(query);
        return super.listAs(queryWrapper, BaseAccountsVO.class);
    }

    public Page<BaseAccountsVO> getPageQuery(Integer pageNum, Integer pageSize, BaseAccountsDTO queryDTO) {
        Accounts query = new Accounts();
        BeanUtils.copyProperties(queryDTO, query);
        QueryWrapper queryWrapper = QueryWrapper.create(query);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseAccountsVO.class);
    }

    public void addAccount(BaseAccountsDTO baseAccountsDTO) {
        Accounts accounts = new Accounts();
        BeanUtils.copyProperties(baseAccountsDTO, accounts);
        super.save(accounts);
    }

    private void verifyId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }

        Accounts accounts = super.getById(id);
        if (accounts == null) {
            throw new IllegalArgumentException("用户不存在");
        }
    }

    public void deleteAccount(Long id) {
        verifyId(id);
        super.removeById(id);
    }

    public void updateAccount(Long id, BaseAccountsDTO baseAccountsDTO) {
        verifyId(id);
        Accounts accounts = new Accounts();
        BeanUtils.copyProperties(baseAccountsDTO, accounts);
        accounts.setId(id);
        super.updateById(accounts);
    }

}
