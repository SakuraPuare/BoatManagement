package com.sakurapuare.boatmanagement.pojo.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 船只类型表 表定义层。
 *
 * @author sakurapuare
 * @since 2025-02-12
 */
public class BoatTypesTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 船只类型表
     */
    public static final BoatTypesTableDef BOAT_TYPES = new BoatTypesTableDef();

    
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 类型名称
     */
    public final QueryColumn TYPE_NAME = new QueryColumn(this, "type_name");

    /**
     * 类型描述
     */
    public final QueryColumn DESCRIPTION = new QueryColumn(this, "description");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, TYPE_NAME, DESCRIPTION};

    public BoatTypesTableDef() {
        super("", "boat_types");
    }

    private BoatTypesTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public BoatTypesTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new BoatTypesTableDef("", "boat_types", alias));
    }

}
