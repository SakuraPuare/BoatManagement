package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.pojo.dto.base.BasePermissionDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Permission;
import com.sakurapuare.boatmanagement.pojo.vo.base.BasePermissionVO;
import com.sakurapuare.boatmanagement.service.base.BasePermissionService;
import com.sakurapuare.boatmanagement.utils.POJOUtils;
import com.sakurapuare.boatmanagement.utils.ParamsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.PERMISSION;

@Service
@RequiredArgsConstructor
public class PermissionService extends BasePermissionService {

    /**
     * 管理员搜索字段
     */
    private static final QueryColumn[] SEARCH_FIELDS = {
            PERMISSION.ID,
            PERMISSION.NAME,
            PERMISSION.CODE,
            PERMISSION.DESCRIPTION
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
     * 管理员获取权限列表
     *
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 权限视图对象列表
     */
    public List<BasePermissionVO> adminGetPermissionList(
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BasePermissionDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, Permission.class);
        adminParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.listAs(queryWrapper, BasePermissionVO.class);
    }

    /**
     * 管理员分页获取权限列表
     *
     * @param pageNum       页码
     * @param pageSize      每页大小
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 分页权限视图对象
     */
    public Page<BasePermissionVO> adminGetPermissionPage(
            Integer pageNum,
            Integer pageSize,
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BasePermissionDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, Permission.class);
        adminParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.pageAs(
                Page.of(pageNum, pageSize), queryWrapper, BasePermissionVO.class);
    }

    /**
     * 管理员根据 ID 获取权限
     *
     * @param ids 权限 ID 字符串，多个 ID 用逗号分隔
     * @return 权限视图对象列表
     * @throws RuntimeException 当权限不存在时抛出
     */
    public List<BasePermissionVO> adminGetPermissionByIds(String ids) {
        List<Long> idList = ParamsUtils.getListFromIds(ids);

        if (idList.isEmpty()) {
            throw new RuntimeException("权限不存在");
        }

        List<Permission> permissions = POJOUtils.getListFromIds(idList, super::getById);
        return POJOUtils.asOtherList(permissions, BasePermissionVO.class);
    }

    /**
     * 管理员创建权限
     *
     * @param dto 权限数据传输对象
     * @return 创建的权限视图对象
     */
    public BasePermissionVO adminCreatePermission(BasePermissionDTO dto) {
        Permission permission = POJOUtils.asOther(dto, Permission.class);
        super.save(permission);
        return POJOUtils.asOther(permission, BasePermissionVO.class);
    }

    /**
     * 检查权限是否存在
     *
     * @param id 权限ID
     * @throws RuntimeException 当权限不存在时抛出
     */
    private void checkPermissionExist(Long id) {
        if (super.getById(id) == null) {
            throw new RuntimeException("权限不存在");
        }
    }

    /**
     * 管理员更新权限信息
     *
     * @param id  权限ID
     * @param dto 权限数据传输对象
     * @return 更新后的权限视图对象
     */
    public BasePermissionVO adminUpdatePermission(Long id, BasePermissionDTO dto) {
        checkPermissionExist(id);
        Permission permission = POJOUtils.asOther(dto, Permission.class);
        permission.setId(id);
        super.updateById(permission);
        return POJOUtils.asOther(super.getById(id), BasePermissionVO.class);
    }

    /**
     * 管理员删除权限
     *
     * @param id 权限ID
     */
    public void adminDeletePermission(Long id) {
        checkPermissionExist(id);
        super.removeById(id);
    }
}
