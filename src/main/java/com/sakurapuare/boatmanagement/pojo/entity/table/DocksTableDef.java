package com.sakurapuare.boatmanagement.pojo.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 码头表 表定义层。
 *
 * @author sakurapuare
 * @since 2025-02-10
 */
public class DocksTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 码头表
     */
    public static final DocksTableDef DOCKS = new DocksTableDef();

    
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 码头名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 详细地址
     */
    public final QueryColumn ADDRESS = new QueryColumn(this, "address");

    /**
     * 地理位置
     */
    public final QueryColumn LOCATION = new QueryColumn(this, "location");

    /**
     * 联系电话
     */
    public final QueryColumn CONTACT_PHONE = new QueryColumn(this, "contact_phone");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, NAME, LOCATION, ADDRESS, CONTACT_PHONE};

    public DocksTableDef() {
        super("", "docks");
    }

    private DocksTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public DocksTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new DocksTableDef("", "docks", alias));
    }

}
