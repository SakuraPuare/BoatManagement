package com.sakurapuare.boatmanagement.pojo.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 验证码防刷记录 表定义层。
 *
 * @author sakurapuare
 * @since 2025-02-10
 */
public class CaptchaLimitTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 验证码防刷记录
     */
    public static final CaptchaLimitTableDef CAPTCHA_LIMIT = new CaptchaLimitTableDef();

    
    public final QueryColumn ID = new QueryColumn(this, "id");

    
    public final QueryColumn IP = new QueryColumn(this, "ip");

    /**
     * 请求次数
     */
    public final QueryColumn COUNT = new QueryColumn(this, "count");

    
    public final QueryColumn SCENE = new QueryColumn(this, "scene");

    
    public final QueryColumn TARGET = new QueryColumn(this, "target");

    /**
     * 是否锁定
     */
    public final QueryColumn IS_BLOCKED = new QueryColumn(this, "is_blocked");

    /**
     * 最后请求时间
     */
    public final QueryColumn LAST_REQUEST = new QueryColumn(this, "last_request");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, TARGET, SCENE, IP, COUNT, LAST_REQUEST, IS_BLOCKED};

    public CaptchaLimitTableDef() {
        super("", "captcha_limit");
    }

    private CaptchaLimitTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public CaptchaLimitTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new CaptchaLimitTableDef("", "captcha_limit", alias));
    }

}
