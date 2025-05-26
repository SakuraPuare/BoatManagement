package com.sakurapuare.boatmanagement.service.auth;

import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.constant.auth.AuthName;
import com.sakurapuare.boatmanagement.constant.auth.AuthStatus;
import com.sakurapuare.boatmanagement.constant.auth.AuthType;
import com.sakurapuare.boatmanagement.pojo.dto.AuthRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Accounts;
import com.sakurapuare.boatmanagement.service.AccountsService;
import com.sakurapuare.boatmanagement.service.DuplicateSubmissionService;
import com.sakurapuare.boatmanagement.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.ACCOUNTS;

@Slf4j
@Component
@RequiredArgsConstructor
public class PasswordStrategy implements AuthStrategy {

    private final AccountsService accountsService;
    private final DuplicateSubmissionService duplicateSubmissionService;
    private final RoleService roleService;

    @Override
    public Accounts auth(AuthStatus status, AuthRequestDTO authRequestDTO) {
        String username = authRequestDTO.getUsername();
        String password = authRequestDTO.getPassword();

        String field = AuthStrategy.getFieldName(status);

        log.info("PasswordStrategy.auth - username: {}, field: {}, authName: {}, authType: {}", 
                username, field, status.getName(), status.getType());

        Accounts account;
        if (status.getType() == AuthType.LOGIN) {
            account = accountsService.getOne(
                    QueryWrapper.create()
                            .eq(field, username)
                            .where(ACCOUNTS.PASSWORD.eq(password)));

            if (account == null) {
                throw new IllegalArgumentException("账号或密码错误");
            }
        } else {
            // 防重复提交检查
            String lockKey = "register:" + username;
            if (!duplicateSubmissionService.tryLock(lockKey)) {
                log.warn("PasswordStrategy.auth - 检测到重复提交，拒绝处理: {}", username);
                throw new IllegalArgumentException("请求正在处理中，请勿重复提交");
            }

            try {
                account = accountsService.getOne(
                        QueryWrapper.create()
                                .eq(field, username));

                log.info("PasswordStrategy.auth - 查询结果: {}", account != null ? "账号已存在" : "账号不存在");

                if (account != null) {
                    throw new IllegalArgumentException("账号已存在");
                }

                Accounts.AccountsBuilder builder = Accounts.builder()
                        .password(password)
                        .isActive(true)
                        .isBlocked(false);

                switch (status.getName()) {
                    case AuthName.USERNAME -> builder.username(username);
                    case AuthName.EMAIL -> builder.email(username);
                    case AuthName.PHONE -> builder.phone(username);
                    default -> throw new IllegalArgumentException("不支持的注册方式");
                }

                account = builder.build();
                log.info("PasswordStrategy.auth - 创建新账户: {}", account);
                
                try {
                    accountsService.save(account);
                    log.info("PasswordStrategy.auth - 账户创建成功");
                    
                    // 为新用户分配默认的 USER 角色
                    try {
                        boolean roleAssigned = roleService.assignRole(account.getId(), 6L, null); // USER 角色 ID 为 6
                        if (roleAssigned) {
                            log.info("PasswordStrategy.auth - 默认角色分配成功: userId={}", account.getId());
                        } else {
                            log.warn("PasswordStrategy.auth - 默认角色分配失败: userId={}", account.getId());
                        }
                    } catch (Exception roleException) {
                        log.error("PasswordStrategy.auth - 角色分配异常: userId={}, error={}", 
                                account.getId(), roleException.getMessage(), roleException);
                        // 角色分配失败不影响注册流程，只记录日志
                    }
                } catch (DuplicateKeyException e) {
                    log.warn("PasswordStrategy.auth - 数据库唯一约束违反: {}", e.getMessage());
                    throw new IllegalArgumentException("账号已存在");
                } catch (Exception e) {
                    log.error("PasswordStrategy.auth - 账户创建失败: {}", e.getMessage(), e);
                    throw new RuntimeException("账户创建失败，请稍后重试");
                }
            } finally {
                // 无论成功还是失败都要释放锁
                duplicateSubmissionService.unlock(lockKey);
            }
        }

        return account;
    }

}
