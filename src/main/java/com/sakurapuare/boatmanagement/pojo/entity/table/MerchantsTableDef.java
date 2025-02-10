package com.sakurapuare.boatmanagement.pojo.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 商家表 表定义层。
 *
 * @author sakurapuare
 * @since 2025-02-10
 */
public class MerchantsTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 商家表
     */
    public static final MerchantsTableDef MERCHANTS = new MerchantsTableDef();

    
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 审核状态
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 所属单位
     */
    public final QueryColumn UNIT_ID = new QueryColumn(this, "unit_id");

    /**
     * 关联用户
     */
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    /**
     * 店铺名称
     */
    public final QueryColumn SHOP_NAME = new QueryColumn(this, "shop_name");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, USER_ID, UNIT_ID, SHOP_NAME, STATUS};

    public MerchantsTableDef() {
        super("", "merchants");
    }

    private MerchantsTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public MerchantsTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new MerchantsTableDef("", "merchants", alias));
    }

}
