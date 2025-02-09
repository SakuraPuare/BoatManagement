package com.sakurapuare.boatmanagement.pojo.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 船主表 表定义层。
 *
 * @author sakurapuare
 * @since 2025-02-10
 */
public class OwnersTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 船主表
     */
    public static final OwnersTableDef OWNERS = new OwnersTableDef();

    
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 所属单位
     */
    public final QueryColumn UNIT_ID = new QueryColumn(this, "unit_id");

    /**
     * 关联用户
     */
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, USER_ID, UNIT_ID};

    public OwnersTableDef() {
        super("", "owners");
    }

    private OwnersTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public OwnersTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new OwnersTableDef("", "owners", alias));
    }

}
