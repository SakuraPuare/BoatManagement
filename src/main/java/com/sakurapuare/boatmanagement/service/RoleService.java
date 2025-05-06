package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.pojo.entity.Permission;
import com.sakurapuare.boatmanagement.pojo.entity.Role;
import com.sakurapuare.boatmanagement.pojo.entity.RoleInheritance;
import com.sakurapuare.boatmanagement.pojo.entity.RolePermission;
import com.sakurapuare.boatmanagement.pojo.entity.UserRole;
import com.sakurapuare.boatmanagement.service.base.BaseRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.ROLE;
import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.PERMISSION;
import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.ROLE_PERMISSION;
import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.USER_ROLE;
import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.ROLE_INHERITANCE;

@Service
@RequiredArgsConstructor
public class RoleService extends BaseRoleService {

    private final PermissionService permissionService;
    private final RolePermissionService rolePermissionService;
    private final UserRoleService userRoleService;
    private final RoleInheritanceService roleInheritanceService;

    /**
     * 获取用户的所有角色
     *
     * @param userId 用户 ID
     * @return 角色列表
     */
    public List<Role> getUserRoles(Long userId) {
        QueryWrapper queryWrapper = new QueryWrapper()
                .eq(USER_ROLE.USER_ID.getName(), userId)
                .eq(USER_ROLE.IS_DELETED.getName(), false);
        
        List<UserRole> userRoles = userRoleService.list(queryWrapper);
        if (userRoles.isEmpty()) {
            return Collections.emptyList();
        }
        
        List<Long> roleIds = userRoles.stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());
        
        QueryWrapper roleQueryWrapper = new QueryWrapper()
                .in(ROLE.ID.getName(), roleIds)
                .eq(ROLE.IS_DELETED.getName(), false);
        
        return super.list(roleQueryWrapper);
    }
    
    /**
     * 获取角色的所有权限
     *
     * @param roleIds 角色 ID 列表
     * @return 权限列表
     */
    public List<Permission> getRolePermissions(List<Long> roleIds) {
        if (roleIds.isEmpty()) {
            return Collections.emptyList();
        }
        
        // 获取角色自身拥有的权限
        QueryWrapper queryWrapper = new QueryWrapper()
                .in(ROLE_PERMISSION.ROLE_ID.getName(), roleIds)
                .eq(ROLE_PERMISSION.IS_DELETED.getName(), false);
        
        List<RolePermission> rolePermissions = rolePermissionService.list(queryWrapper);
        if (rolePermissions.isEmpty()) {
            return Collections.emptyList();
        }
        
        List<Long> permissionIds = rolePermissions.stream()
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toList());
        
        QueryWrapper permissionQueryWrapper = new QueryWrapper()
                .in(PERMISSION.ID.getName(), permissionIds)
                .eq(PERMISSION.IS_DELETED.getName(), false);
        
        return permissionService.list(permissionQueryWrapper);
    }
    
    /**
     * 获取角色继承的所有角色 ID
     *
     * @param roleIds 角色 ID 列表
     * @return 继承的角色 ID 列表（包含原有角色 ID）
     */
    public List<Long> getInheritedRoleIds(List<Long> roleIds) {
        if (roleIds.isEmpty()) {
            return Collections.emptyList();
        }
        
        Set<Long> resultRoleIds = new HashSet<>(roleIds);
        Set<Long> parentRoleIds = new HashSet<>(roleIds);
        
        // 递归获取父角色，直到没有新的父角色
        while (!parentRoleIds.isEmpty()) {
            QueryWrapper queryWrapper = new QueryWrapper()
                    .in(ROLE_INHERITANCE.CHILD_ROLE_ID.getName(), parentRoleIds)
                    .eq(ROLE_INHERITANCE.IS_DELETED.getName(), false);
            
            List<RoleInheritance> inheritances = roleInheritanceService.list(queryWrapper);
            if (inheritances.isEmpty()) {
                break;
            }
            
            // 获取新一轮要查询的父角色 ID
            parentRoleIds = inheritances.stream()
                    .map(RoleInheritance::getParentRoleId)
                    .filter(id -> !resultRoleIds.contains(id)) // 过滤已包含的角色，避免循环依赖
                    .collect(Collectors.toSet());
            
            // 添加到结果集
            resultRoleIds.addAll(parentRoleIds);
        }
        
        return new ArrayList<>(resultRoleIds);
    }
    
    /**
     * 获取用户的所有权限
     *
     * @param userId 用户 ID
     * @return 权限列表
     */
    public List<Permission> getUserPermissions(Long userId) {
        List<Role> userRoles = getUserRoles(userId);
        if (userRoles.isEmpty()) {
            return Collections.emptyList();
        }
        
        List<Long> roleIds = userRoles.stream()
                .map(Role::getId)
                .collect(Collectors.toList());
        
        // 获取继承的角色
        List<Long> allRoleIds = getInheritedRoleIds(roleIds);
        
        // 获取所有角色的权限
        return getRolePermissions(allRoleIds);
    }
    
    /**
     * 检查用户是否拥有指定角色
     *
     * @param userId  用户 ID
     * @param roleNames 角色名列表
     * @return 是否拥有角色
     */
    public boolean hasAnyRole(Long userId, List<String> roleNames) {
        if (roleNames == null || roleNames.isEmpty()) {
            return true; // 如果不需要角色，直接返回 true
        }
        
        List<Role> userRoles = getUserRoles(userId);
        if (userRoles.isEmpty()) {
            return false;
        }
        
        List<Long> roleIds = userRoles.stream()
                .map(Role::getId)
                .collect(Collectors.toList());
        
        // 获取继承的角色
        List<Long> allRoleIds = getInheritedRoleIds(roleIds);
        
        // 获取角色名称
        QueryWrapper roleQueryWrapper = new QueryWrapper()
                .in(ROLE.ID.getName(), allRoleIds)
                .in(ROLE.NAME.getName(), roleNames)
                .eq(ROLE.IS_DELETED.getName(), false);
        
        long count = super.count(roleQueryWrapper);
        return count > 0;
    }
    
    /**
     * 检查用户是否拥有指定权限
     *
     * @param userId 用户 ID
     * @param permissionCodes 权限代码列表
     * @return 是否拥有权限
     */
    public boolean hasAnyPermission(Long userId, List<String> permissionCodes) {
        if (permissionCodes == null || permissionCodes.isEmpty()) {
            return true; // 如果不需要权限，直接返回 true
        }
        
        List<Permission> permissions = getUserPermissions(userId);
        if (permissions.isEmpty()) {
            return false;
        }
        
        Set<String> userPermissionCodes = permissions.stream()
                .map(Permission::getCode)
                .collect(Collectors.toSet());
        
        // 检查是否包含任一权限
        for (String code : permissionCodes) {
            if (userPermissionCodes.contains(code)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * 为用户分配角色
     *
     * @param userId 用户 ID
     * @param roleId 角色 ID
     * @param unitId 单位 ID（可选）
     * @return 是否成功
     */
    public boolean assignRole(Long userId, Long roleId, Long unitId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        userRole.setUnitId(unitId);
        
        return userRoleService.save(userRole);
    }
    
    /**
     * 移除用户角色
     *
     * @param userId 用户 ID
     * @param roleId 角色 ID
     * @param unitId 单位 ID（可选）
     * @return 是否成功
     */
    public boolean revokeRole(Long userId, Long roleId, Long unitId) {
        QueryWrapper queryWrapper = new QueryWrapper()
                .eq(USER_ROLE.USER_ID.getName(), userId)
                .eq(USER_ROLE.ROLE_ID.getName(), roleId);
        
        if (unitId != null) {
            queryWrapper.eq(USER_ROLE.UNIT_ID.getName(), unitId);
        }
        
        return userRoleService.remove(queryWrapper);
    }
}