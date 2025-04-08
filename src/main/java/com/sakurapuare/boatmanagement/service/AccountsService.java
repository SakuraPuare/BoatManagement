package com.sakurapuare.boatmanagement.service;

import cn.hutool.json.JSONObject;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseAccountsDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Accounts;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseAccountsVO;
import com.sakurapuare.boatmanagement.service.base.BaseAccountsService;
import com.sakurapuare.boatmanagement.utils.JWTUtils;
import com.sakurapuare.boatmanagement.utils.POJOUtils;
import com.sakurapuare.boatmanagement.utils.ParamsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.ACCOUNTS;

@Service
@RequiredArgsConstructor
public class AccountsService extends BaseAccountsService {

    /**
     * 管理员搜索字段
     */
    private static final QueryColumn[] SEARCH_FIELDS = {
            ACCOUNTS.ID,
            ACCOUNTS.USERNAME,
            ACCOUNTS.EMAIL,
            ACCOUNTS.PHONE
    };

    public Accounts getAccountByToken(String token) {
        JSONObject payload = JWTUtils.parseToken(token);
        Long userId = payload.getLong("userId");
        return super.getById(userId);
    }

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
     * 管理员获取用户列表
     *
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 用户视图对象列表
     */
    public List<BaseAccountsVO> adminGetUserList(
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseAccountsDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, Accounts.class);
        adminParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.listAs(queryWrapper, BaseAccountsVO.class);
    }

    /**
     * 管理员分页获取用户列表
     *
     * @param pageNum       页码
     * @param pageSize      每页大小
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 分页用户视图对象
     */
    public Page<BaseAccountsVO> adminGetUserPage(
            Integer pageNum,
            Integer pageSize,
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseAccountsDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, Accounts.class);
        adminParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseAccountsVO.class);
    }

    /**
     * 管理员根据ID获取用户
     *
     * @param ids 用户ID字符串，多个ID用逗号分隔
     * @return 用户视图对象列表
     * @throws RuntimeException 当用户不存在时抛出
     */
    public List<BaseAccountsVO> adminGetUserByIds(String ids) {
        List<Long> idList = ParamsUtils.getListFromIds(ids);

        if (idList.isEmpty()) {
            throw new RuntimeException("用户不存在");
        }

        List<Accounts> accounts = POJOUtils.getListFromIds(idList, super::getById);

        return POJOUtils.asOtherList(accounts, BaseAccountsVO.class);
    }

    /**
     * 管理员创建用户
     *
     * @param dto 用户数据传输对象
     * @return 创建的用户视图对象
     */
    public BaseAccountsVO adminCreateUser(BaseAccountsDTO dto) {
        Accounts accounts = POJOUtils.asOther(dto, Accounts.class);
        super.save(accounts);
        return POJOUtils.asOther(accounts, BaseAccountsVO.class);
    }

    /**
     * 检查用户是否存在
     *
     * @param id 用户ID
     * @throws RuntimeException 当用户不存在时抛出
     */
    private void checkUserExist(Long id) {
        if (super.getById(id) == null) {
            throw new RuntimeException("用户不存在");
        }
    }

    /**
     * 管理员更新用户信息
     *
     * @param id  用户ID
     * @param dto 用户数据传输对象
     * @return 更新后的用户视图对象
     */
    public BaseAccountsVO adminUpdateUser(Long id, BaseAccountsDTO dto) {
        checkUserExist(id);
        Accounts accounts = POJOUtils.asOther(dto, Accounts.class);
        accounts.setId(id);
        super.updateById(accounts);
        return POJOUtils.asOther(super.getById(id), BaseAccountsVO.class);
    }

    /**
     * 管理员删除用户
     *
     * @param id 用户ID
     */
    public void adminDeleteUser(Long id) {
        checkUserExist(id);
        super.removeById(id);
    }
}
