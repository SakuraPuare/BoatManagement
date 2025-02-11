package com.sakurapuare.boatmanagement.pojo.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 单位表 表定义层。
 *
 * @author sakurapuare
 * @since 2025-02-12
 */
public class UnitsTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 单位表
     */
    public static final UnitsTableDef UNITS = new UnitsTableDef();

    
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 对外名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 单位类型
     */
    public final QueryColumn TYPES = new QueryColumn(this, "types");

    /**
     * 审核状态
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 单位地址
     */
    public final QueryColumn ADDRESS = new QueryColumn(this, "address");

    /**
     * 单位名称
     */
    public final QueryColumn UNIT_NAME = new QueryColumn(this, "unit_name");

    /**
     * 单位管理员
     */
    public final QueryColumn ADMIN_USER_ID = new QueryColumn(this, "admin_user_id");

    /**
     * 法人代表
     */
    public final QueryColumn LEGAL_PERSON = new QueryColumn(this, "legal_person");

    /**
     * 联系电话
     */
    public final QueryColumn CONTACT_PHONE = new QueryColumn(this, "contact_phone");

    /**
     * 统一社会信用代码
     */
    public final QueryColumn SOCIAL_CREDIT_CODE = new QueryColumn(this, "social_credit_code");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, NAME, UNIT_NAME, SOCIAL_CREDIT_CODE, LEGAL_PERSON, ADDRESS, CONTACT_PHONE, STATUS, ADMIN_USER_ID, TYPES};

    public UnitsTableDef() {
        super("", "units");
    }

    private UnitsTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public UnitsTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new UnitsTableDef("", "units", alias));
    }

}
