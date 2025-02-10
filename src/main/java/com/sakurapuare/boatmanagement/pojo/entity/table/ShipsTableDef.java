package com.sakurapuare.boatmanagement.pojo.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 船只表 表定义层。
 *
 * @author sakurapuare
 * @since 2025-02-10
 */
public class ShipsTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 船只表
     */
    public static final ShipsTableDef SHIPS = new ShipsTableDef();

    
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 船只名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 船身宽度（米）
     */
    public final QueryColumn WIDTH = new QueryColumn(this, "width");

    /**
     * 船身长度（米）
     */
    public final QueryColumn LENGTH = new QueryColumn(this, "length");

    /**
     * 船只类型
     */
    public final QueryColumn TYPE_ID = new QueryColumn(this, "type_id");

    /**
     * 所属单位
     */
    public final QueryColumn UNIT_ID = new QueryColumn(this, "unit_id");

    /**
     * 最大载重（吨）
     */
    public final QueryColumn MAX_LOAD = new QueryColumn(this, "max_load");

    /**
     * 船主ID
     */
    public final QueryColumn VENDOR_ID = new QueryColumn(this, "vendor_id");

    /**
     * 最大续航（公里）
     */
    public final QueryColumn MAX_ENDURANCE = new QueryColumn(this, "max_endurance");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, NAME, TYPE_ID, VENDOR_ID, UNIT_ID, LENGTH, WIDTH, MAX_LOAD, MAX_ENDURANCE};

    public ShipsTableDef() {
        super("", "ships");
    }

    private ShipsTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public ShipsTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new ShipsTableDef("", "ships", alias));
    }

}
