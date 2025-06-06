package com.sakurapuare.boatmanagement.controller.user;

import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.common.context.UserContext;
import com.sakurapuare.boatmanagement.pojo.entity.Accounts;
import com.sakurapuare.boatmanagement.pojo.entity.Merchants;
import com.sakurapuare.boatmanagement.pojo.entity.Role;
import com.sakurapuare.boatmanagement.pojo.vo.UserInfoVO;
import com.sakurapuare.boatmanagement.pojo.vo.UserWithRoleVO;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseAccountsVO;
import com.sakurapuare.boatmanagement.service.AccountsService;
import com.sakurapuare.boatmanagement.service.MerchantsService;
import com.sakurapuare.boatmanagement.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.MERCHANTS;

@RestController
@RequestMapping("/user/info")
@Tag(name = "UserInfo", description = "用户信息模块")
@RequiredArgsConstructor
public class UserInfoController {

    private final AccountsService accountsService;
    private final RoleService roleService;
    private final MerchantsService merchantsService;

    @GetMapping("/{id}")
    @Operation(summary = "获取用户信息")
    public Response<UserInfoVO> userGetUser(@PathVariable Long id) {
        List<BaseAccountsVO> users = accountsService.adminGetUserByIds(id.toString());
        if (users.isEmpty()) {
            return Response.error("用户不存在");
        }
        
        // 转换为用户视图对象
        UserInfoVO userInfoVO = new UserInfoVO();
        BaseAccountsVO user = users.get(0);
        userInfoVO.setId(user.getId());
        userInfoVO.setUsername(user.getUsername());
        // 设置其他需要的属性...
        
        return Response.success(userInfoVO);
    }

    @GetMapping("/merchant/{merchantId}")
    @Operation(summary = "检查当前用户是否是指定商家")
    public Response<Boolean> userCheckIsMerchant(@PathVariable Long merchantId) {
        Accounts account = UserContext.getAccount();
        // 查询当前用户是否是该商家
        Merchants merchant = merchantsService.getOne(
                QueryWrapper.create().where(MERCHANTS.USER_ID.eq(account.getId()))
                        .and(MERCHANTS.ID.eq(merchantId)));
        return Response.success(merchant != null);
    }

    @GetMapping("/me")
    @Operation(summary = "获取当前用户信息")
    public Response<UserWithRoleVO> userGetCurrentUser() {
        Accounts account = UserContext.getAccount();
        List<BaseAccountsVO> users = accountsService.adminGetUserByIds(account.getId().toString());
        if (users.isEmpty()) {
            return Response.error("用户不存在");
        }
        
        BaseAccountsVO baseUser = users.get(0);
        UserWithRoleVO userWithRole = new UserWithRoleVO();
        
        // 复制基础用户信息
        userWithRole.setId(baseUser.getId());
        userWithRole.setUsername(baseUser.getUsername());
        userWithRole.setPassword(baseUser.getPassword());
        userWithRole.setPhone(baseUser.getPhone());
        userWithRole.setEmail(baseUser.getEmail());
        userWithRole.setIsActive(baseUser.getIsActive());
        userWithRole.setIsBlocked(baseUser.getIsBlocked());
        userWithRole.setCreatedAt(baseUser.getCreatedAt());
        userWithRole.setUpdatedAt(baseUser.getUpdatedAt());
        
        // 获取用户角色并计算角色掩码
        List<Role> userRoles = roleService.getUserRoles(account.getId());
        int roleMask = 0;
        for (Role role : userRoles) {
            switch (role.getName()) {
                case "ADMIN" -> roleMask |= 1; // 1 << 0
                case "MERCHANT" -> roleMask |= 2; // 1 << 1
                case "VENDOR" -> roleMask |= 4; // 1 << 2
                case "BOAT_OWNER" -> roleMask |= 8; // 1 << 3
                case "USER" -> roleMask |= 16; // 1 << 4
            }
        }
        userWithRole.setRole(roleMask);
        
        return Response.success(userWithRole);
    }
}
