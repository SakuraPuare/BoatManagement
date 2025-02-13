package com.sakurapuare.boatmanagement.pojo.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 第三方登录表_ndto_nvo 表定义层。
 *
 * @author sakurapuare
 * @since 2025-02-14
 */
public class SocialAuthTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 第三方登录表_ndto_nvo
     */
    public static final SocialAuthTableDef SOCIAL_AUTH = new SocialAuthTableDef();

    
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 第三方唯一ID
     */
    public final QueryColumn OPEN_ID = new QueryColumn(this, "open_id");

    /**
     * 跨平台统一ID
     */
    public final QueryColumn UNION_ID = new QueryColumn(this, "union_id");

    /**
     * 第三方平台
     */
    public final QueryColumn PLATFORM = new QueryColumn(this, "platform");

    
    public final QueryColumn ACCOUNT_ID = new QueryColumn(this, "account_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, ACCOUNT_ID, PLATFORM, OPEN_ID, UNION_ID};

    public SocialAuthTableDef() {
        super("", "social_auth");
    }

    private SocialAuthTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public SocialAuthTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new SocialAuthTableDef("", "social_auth", alias));
    }

}
