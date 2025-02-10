package com.sakurapuare.boatmanagement.pojo.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 角色表 表定义层。
 *
 * @author sakurapuare
 * @since 2025-02-11
 */
public class RoleTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色表
     */
    public static final RoleTableDef ROLE = new RoleTableDef();

    
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 角色名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 角色描述
     */
    public final QueryColumn DESCRIPTION = new QueryColumn(this, "description");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, NAME, DESCRIPTION};

    public RoleTableDef() {
        super("", "role");
    }

    private RoleTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public RoleTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new RoleTableDef("", "role", alias));
    }

}
