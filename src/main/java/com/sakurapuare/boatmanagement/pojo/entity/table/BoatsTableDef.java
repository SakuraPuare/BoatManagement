package com.sakurapuare.boatmanagement.pojo.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 船只表 表定义层。
 *
 * @author sakurapuare
 * @since 2025-02-14
 */
public class BoatsTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 船只表
     */
    public static final BoatsTableDef BOATS = new BoatsTableDef();

    
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 船只名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 所属码头
     */
    public final QueryColumn DOCK_ID = new QueryColumn(this, "dock_id");

    /**
     * 船只类型
     */
    public final QueryColumn TYPE_ID = new QueryColumn(this, "type_id");

    /**
     * 所属单位_serverside
     */
    public final QueryColumn UNIT_ID = new QueryColumn(this, "unit_id");

    /**
     * 船主ID_serverside
     */
    public final QueryColumn VENDOR_ID = new QueryColumn(this, "vendor_id");

    /**
     * 船只类型
     */
    public final QueryColumn BOAT_TYPE_ID = new QueryColumn(this, "boat_type_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, NAME, TYPE_ID, BOAT_TYPE_ID, DOCK_ID, VENDOR_ID, UNIT_ID};

    public BoatsTableDef() {
        super("", "boats");
    }

    private BoatsTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public BoatsTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new BoatsTableDef("", "boats", alias));
    }

}
