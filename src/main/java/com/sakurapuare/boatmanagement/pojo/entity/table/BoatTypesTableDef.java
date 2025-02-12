package com.sakurapuare.boatmanagement.pojo.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 船只类型表 表定义层。
 *
 * @author sakurapuare
 * @since 2025-02-13
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
     * 船身宽度（米）
     */
    public final QueryColumn WIDTH = new QueryColumn(this, "width");

    /**
     * 船身长度（米）
     */
    public final QueryColumn LENGTH = new QueryColumn(this, "length");

    /**
     * 最大载重（吨）
     */
    public final QueryColumn MAX_LOAD = new QueryColumn(this, "max_load");

    /**
     * 最大航速（公里/小时）
     */
    public final QueryColumn MAX_SPEED = new QueryColumn(this, "max_speed");

    /**
     * 类型名称
     */
    public final QueryColumn TYPE_NAME = new QueryColumn(this, "type_name");

    /**
     * 类型描述
     */
    public final QueryColumn DESCRIPTION = new QueryColumn(this, "description");

    /**
     * 核载人数（人）
     */
    public final QueryColumn GROSS_NUMBER = new QueryColumn(this, "gross_number");

    /**
     * 最大续航（公里）
     */
    public final QueryColumn MAX_ENDURANCE = new QueryColumn(this, "max_endurance");

    /**
     * 创建单位
     */
    public final QueryColumn CREATED_UNIT_ID = new QueryColumn(this, "created_unit_id");

    /**
     * 创建者
     */
    public final QueryColumn CREATED_VENDOR_ID = new QueryColumn(this, "created_vendor_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, TYPE_NAME, DESCRIPTION, LENGTH, WIDTH, GROSS_NUMBER, MAX_LOAD, MAX_SPEED, MAX_ENDURANCE, CREATED_VENDOR_ID, CREATED_UNIT_ID};

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
