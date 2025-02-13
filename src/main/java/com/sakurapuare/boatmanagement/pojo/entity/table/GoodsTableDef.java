package com.sakurapuare.boatmanagement.pojo.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 商品表 表定义层。
 *
 * @author sakurapuare
 * @since 2025-02-13
 */
public class GoodsTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 商品表
     */
    public static final GoodsTableDef GOODS = new GoodsTableDef();

    
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 商品名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 商品价格
     */
    public final QueryColumn PRICE = new QueryColumn(this, "price");

    /**
     * 销量_serverside
     */
    public final QueryColumn SALES = new QueryColumn(this, "sales");

    /**
     * 库存_serverside
     */
    public final QueryColumn STOCK = new QueryColumn(this, "stock");

    /**
     * 商品描述
     */
    public final QueryColumn DESCRIPTION = new QueryColumn(this, "description");

    /**
     * 创建单位_serverside
     */
    public final QueryColumn CREATED_UNIT_ID = new QueryColumn(this, "created_unit_id");

    /**
     * 创建商家_serverside
     */
    public final QueryColumn CREATED_MERCHANT_ID = new QueryColumn(this, "created_merchant_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, NAME, DESCRIPTION, PRICE, STOCK, SALES, CREATED_MERCHANT_ID, CREATED_UNIT_ID};

    public GoodsTableDef() {
        super("", "goods");
    }

    private GoodsTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public GoodsTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new GoodsTableDef("", "goods", alias));
    }

}
