package com.sakurapuare.boatmanagement.pojo.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 用户实名认证表 表定义层。
 *
 * @author sakurapuare
 * @since 2025-02-12
 */
public class UserCertifyTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户实名认证表
     */
    public static final UserCertifyTableDef USER_CERTIFY = new UserCertifyTableDef();

    
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 身份证号
     */
    public final QueryColumn ID_CARD = new QueryColumn(this, "id_card");

    /**
     * 审核状态
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 关联用户
     */
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    /**
     * 真实姓名
     */
    public final QueryColumn REAL_NAME = new QueryColumn(this, "real_name");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, USER_ID, REAL_NAME, ID_CARD, STATUS};

    public UserCertifyTableDef() {
        super("", "user_certify");
    }

    private UserCertifyTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public UserCertifyTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new UserCertifyTableDef("", "user_certify", alias));
    }

}
