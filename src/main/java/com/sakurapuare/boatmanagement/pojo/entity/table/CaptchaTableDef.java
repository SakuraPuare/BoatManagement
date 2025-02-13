package com.sakurapuare.boatmanagement.pojo.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 验证码表_ndto_nvo 表定义层。
 *
 * @author sakurapuare
 * @since 2025-02-14
 */
public class CaptchaTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 验证码表_ndto_nvo
     */
    public static final CaptchaTableDef CAPTCHA = new CaptchaTableDef();

    
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 验证码
     */
    public final QueryColumn CODE = new QueryColumn(this, "code");

    /**
     * 使用状态
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 接收对象
     */
    public final QueryColumn TARGET = new QueryColumn(this, "target");

    /**
     * 请求IP
     */
    public final QueryColumn CLIENT_IP = new QueryColumn(this, "client_ip");

    /**
     * 过期时间
     */
    public final QueryColumn EXPIRE_AT = new QueryColumn(this, "expire_at");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, TARGET, CODE, STATUS, EXPIRE_AT, CLIENT_IP};

    public CaptchaTableDef() {
        super("", "captcha");
    }

    private CaptchaTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public CaptchaTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new CaptchaTableDef("", "captcha", alias));
    }

}
