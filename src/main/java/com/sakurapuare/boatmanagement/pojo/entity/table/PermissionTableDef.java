package com.sakurapuare.boatmanagement.pojo.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 权限表 表定义层。
 *
 * @author sakurapuare
 * @since 2025-02-12
 */
public class PermissionTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 权限表
     */
    public static final PermissionTableDef PERMISSION = new PermissionTableDef();

    
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 权限代码
     */
    public final QueryColumn CODE = new QueryColumn(this, "code");

    /**
     * 权限名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 权限描述
     */
    public final QueryColumn DESCRIPTION = new QueryColumn(this, "description");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, NAME, CODE, DESCRIPTION};

    public PermissionTableDef() {
        super("", "permission");
    }

    private PermissionTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public PermissionTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new PermissionTableDef("", "permission", alias));
    }

}
