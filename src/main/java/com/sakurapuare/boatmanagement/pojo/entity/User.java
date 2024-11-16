package com.sakurapuare.boatmanagement.pojo.entity;

import com.mybatisflex.annotation.*;
import com.sakurapuare.boatmanagement.constant.UserRole;
import com.sakurapuare.boatmanagement.utils.JWTUtils;
import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实体类。
 *
 * @author sakurapuare
 * @since 2024-11-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class User extends BaseEntity {

    @Id(keyType = KeyType.Auto)
    private Long userId;

    @Column(onInsertValue = "UUID()")
    private String uuid;

    private String username;

    private String password;

    private String email;

    private String phone;

    private UserRole role;

    @RelationOneToMany(selfField = "codes", targetField = "userId")
    private List<Code> codes;

    @Column(onInsertValue = "FALSE")
    private Boolean isActive;

    @Column(onInsertValue = "FALSE")
    private Boolean isBlocked;

    public String getToken() {
        Map<String, Object> map = new HashMap<>();

        map.put("userId", this.userId);
        map.put("uuid", this.uuid);
        map.put("role", this.role);

        return JWTUtils.generateToken(map);
    }
}
