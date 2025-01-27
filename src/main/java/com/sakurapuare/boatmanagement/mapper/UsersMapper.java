package com.sakurapuare.boatmanagement.mapper;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.pojo.entity.Users;

import static com.sakurapuare.boatmanagement.pojo.entity.table.UsersTableDef.USERS;

import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表 映射层。
 *
 * @author sakurapuare
 * @since 2024-12-17
 */
@Mapper
public interface UsersMapper extends BaseMapper<Users> {
    default Users selectOneByOpenid(String openid) {
        return this.selectOneByQuery(
                QueryWrapper.create()
                        .where(USERS.OPENID.eq(openid))
                        .and(USERS.IS_DELETED.eq(false))
        );
    }
}
