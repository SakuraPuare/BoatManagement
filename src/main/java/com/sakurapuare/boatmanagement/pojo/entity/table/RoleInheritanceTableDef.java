package com.sakurapuare.boatmanagement.pojo.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 角色继承关系表 表定义层。
 *
 * @author sakurapuare
 * @since 2025-02-10
 */
public class RoleInheritanceTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色继承关系表
     */
    public static final RoleInheritanceTableDef ROLE_INHERITANCE = new RoleInheritanceTableDef();

    
    public final QueryColumn CHILD_ROLE_ID = new QueryColumn(this, "child_role_id");

    
    public final QueryColumn PARENT_ROLE_ID = new QueryColumn(this, "parent_role_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{PARENT_ROLE_ID, CHILD_ROLE_ID};

    public RoleInheritanceTableDef() {
        super("", "role_inheritance");
    }

    private RoleInheritanceTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public RoleInheritanceTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new RoleInheritanceTableDef("", "role_inheritance", alias));
    }

}
