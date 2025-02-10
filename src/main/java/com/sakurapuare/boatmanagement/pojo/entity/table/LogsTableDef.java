package com.sakurapuare.boatmanagement.pojo.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 系统日志表 表定义层。
 *
 * @author sakurapuare
 * @since 2025-02-11
 */
public class LogsTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 系统日志表
     */
    public static final LogsTableDef LOGS = new LogsTableDef();

    
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 日志类型
     */
    public final QueryColumn TYPE = new QueryColumn(this, "type");

    /**
     * 日志等级
     */
    public final QueryColumn LEVEL = new QueryColumn(this, "level");

    /**
     * 来源模块
     */
    public final QueryColumn SOURCE = new QueryColumn(this, "source");

    /**
     * 日志内容
     */
    public final QueryColumn CONTENT = new QueryColumn(this, "content");

    /**
     * 操作人
     */
    public final QueryColumn OPERATOR_ID = new QueryColumn(this, "operator_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, TYPE, LEVEL, CONTENT, SOURCE, OPERATOR_ID};

    public LogsTableDef() {
        super("", "logs");
    }

    private LogsTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public LogsTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new LogsTableDef("", "logs", alias));
    }

}
