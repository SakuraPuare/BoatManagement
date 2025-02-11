package com.sakurapuare.boatmanagement.pojo.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 订单表 表定义层。
 *
 * @author sakurapuare
 * @since 2025-02-12
 */
public class OrdersTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 订单表
     */
    public static final OrdersTableDef ORDERS = new OrdersTableDef();

    
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 订单类型
     */
    public final QueryColumn TYPE = new QueryColumn(this, "type");

    /**
     * 指定船只
     */
    public final QueryColumn BOAT_ID = new QueryColumn(this, "boat_id");

    
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 下单用户
     */
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    /**
     * 租用结束时间
     */
    public final QueryColumn END_TIME = new QueryColumn(this, "end_time");

    /**
     * 订单编号
     */
    public final QueryColumn ORDER_NO = new QueryColumn(this, "order_no");

    /**
     * 目的码头
     */
    public final QueryColumn END_DOCK_ID = new QueryColumn(this, "end_dock_id");

    /**
     * 租用开始时间
     */
    public final QueryColumn START_TIME = new QueryColumn(this, "start_time");

    /**
     * 起始码头
     */
    public final QueryColumn START_DOCK_ID = new QueryColumn(this, "start_dock_id");

    /**
     * 订单总金额
     */
    public final QueryColumn TOTAL_AMOUNT = new QueryColumn(this, "total_amount");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, ORDER_NO, USER_ID, BOAT_ID, START_DOCK_ID, END_DOCK_ID, START_TIME, END_TIME, TOTAL_AMOUNT, STATUS, TYPE};

    public OrdersTableDef() {
        super("", "orders");
    }

    private OrdersTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public OrdersTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new OrdersTableDef("", "orders", alias));
    }

}
