package com.sakurapuare.boatmanagement.pojo.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 船只类型表 表定义层。
 *
 * @author sakurapuare
 * @since 2025-02-10
 */
public class ShipTypesTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 船只类型表
     */
    public static final ShipTypesTableDef SHIP_TYPES = new ShipTypesTableDef();

    
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

    public ShipTypesTableDef() {
        super("", "ship_types");
    }

    private ShipTypesTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public ShipTypesTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new ShipTypesTableDef("", "ship_types", alias));
    }

}
