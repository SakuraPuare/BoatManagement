package com.sakurapuare.boatmanagement.pojo.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 基础账号表 表定义层。
 *
 * @author sakurapuare
 * @since 2025-02-14
 */
public class AccountsTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 基础账号表
     */
    public static final AccountsTableDef ACCOUNTS = new AccountsTableDef();

    
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 角色MASK
     */
    public final QueryColumn ROLE = new QueryColumn(this, "role");

    /**
     * 邮箱
     */
    public final QueryColumn EMAIL = new QueryColumn(this, "email");

    /**
     * 手机号
     */
    public final QueryColumn PHONE = new QueryColumn(this, "phone");

    
    public final QueryColumn IS_ACTIVE = new QueryColumn(this, "is_active");

    /**
     * 密码
     */
    public final QueryColumn PASSWORD = new QueryColumn(this, "password");

    /**
     * 用户名
     */
    public final QueryColumn USERNAME = new QueryColumn(this, "username");

    
    public final QueryColumn IS_BLOCKED = new QueryColumn(this, "is_blocked");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, USERNAME, PASSWORD, PHONE, EMAIL, ROLE, IS_ACTIVE, IS_BLOCKED};

    public AccountsTableDef() {
        super("", "accounts");
    }

    private AccountsTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public AccountsTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new AccountsTableDef("", "accounts", alias));
    }

}
