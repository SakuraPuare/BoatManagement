package com.sakurapuare.boatmanagement.pojo.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 角色权限关联表 表定义层。
 *
 * @author sakurapuare
 * @since 2025-02-11
 */
public class RolePermissionTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色权限关联表
     */
    public static final RolePermissionTableDef ROLE_PERMISSION = new RolePermissionTableDef();

    
    public final QueryColumn ROLE_ID = new QueryColumn(this, "role_id");

    
    public final QueryColumn PERMISSION_ID = new QueryColumn(this, "permission_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ROLE_ID, PERMISSION_ID};

    public RolePermissionTableDef() {
        super("", "role_permission");
    }

    private RolePermissionTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public RolePermissionTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new RolePermissionTableDef("", "role_permission", alias));
    }

}
