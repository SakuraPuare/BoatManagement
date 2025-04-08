package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseUserCertifyDTO;
import com.sakurapuare.boatmanagement.pojo.entity.UserCertify;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseUserCertifyVO;
import com.sakurapuare.boatmanagement.service.base.BaseUserCertifyService;
import com.sakurapuare.boatmanagement.utils.POJOUtils;
import com.sakurapuare.boatmanagement.utils.ParamsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.USER_CERTIFY;

@Service
@RequiredArgsConstructor
public class UserCertifyService extends BaseUserCertifyService {

    /**
     * 管理员搜索字段
     */
    private static final QueryColumn[] SEARCH_FIELDS = {
            USER_CERTIFY.ID,
            USER_CERTIFY.USER_ID,
            USER_CERTIFY.REAL_NAME,
            USER_CERTIFY.ID_CARD,
            USER_CERTIFY.STATUS
    };

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
     * 管理员获取用户认证列表
     *
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 用户认证视图对象列表
     */
    public List<BaseUserCertifyVO> adminGetUserCertifyList(
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseUserCertifyDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, UserCertify.class);
        adminParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.listAs(queryWrapper, BaseUserCertifyVO.class);
    }

    /**
     * 管理员分页获取用户认证列表
     *
     * @param pageNum       页码
     * @param pageSize      每页大小
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 分页用户认证视图对象
     */
    public Page<BaseUserCertifyVO> adminGetUserCertifyPage(
            Integer pageNum,
            Integer pageSize,
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseUserCertifyDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, UserCertify.class);
        adminParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseUserCertifyVO.class);
    }

    /**
     * 管理员根据ID获取用户认证
     *
     * @param ids 用户认证ID字符串，多个ID用逗号分隔
     * @return 用户认证视图对象列表
     */
    public List<BaseUserCertifyVO> adminGetUserCertifyByIds(String ids) {
        List<Long> idList = ParamsUtils.getListFromIds(ids);

        if (idList.isEmpty()) {
            return List.of();
        }

        List<UserCertify> userCertifies = POJOUtils.getListFromIds(idList, super::getById);

        return POJOUtils.asOtherList(userCertifies, BaseUserCertifyVO.class);
    }

    /**
     * 管理员创建用户认证
     *
     * @param dto 用户认证数据传输对象
     * @return 创建的用户认证视图对象
     */
    public BaseUserCertifyVO adminCreateUserCertify(BaseUserCertifyDTO dto) {
        UserCertify userCertify = POJOUtils.asOther(dto, UserCertify.class);
        super.save(userCertify);
        return POJOUtils.asOther(userCertify, BaseUserCertifyVO.class);
    }

    /**
     * 检查用户认证是否存在
     *
     * @param id 用户认证ID
     * @throws RuntimeException 当用户认证不存在时抛出
     */
    private void checkUserCertifyExist(Long id) {
        if (super.getById(id) == null) {
            throw new RuntimeException("用户认证不存在");
        }
    }

    /**
     * 管理员更新用户认证信息
     *
     * @param id  用户认证ID
     * @param dto 用户认证数据传输对象
     * @return 更新后的用户认证视图对象
     */
    public BaseUserCertifyVO adminUpdateUserCertify(Long id, BaseUserCertifyDTO dto) {
        checkUserCertifyExist(id);
        UserCertify userCertify = POJOUtils.asOther(dto, UserCertify.class);
        userCertify.setId(id);
        super.updateById(userCertify);
        return POJOUtils.asOther(super.getById(id), BaseUserCertifyVO.class);
    }

    /**
     * 管理员删除用户认证
     *
     * @param id 用户认证ID
     */
    public void adminDeleteUserCertify(Long id) {
        checkUserCertifyExist(id);
        super.removeById(id);
    }
}
