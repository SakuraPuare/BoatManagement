package com.sakurapuare.boatmanagement.pojo.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.*;

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
    private Integer userId;

    @Column(onInsertValue = "UUID()")
    private String uuid;

    private String username;

    private String password;

    private String email;

    private String phone;

    private Integer role;

    @Column(onInsertValue = "FALSE")
    private Boolean isActive;

    @Column(onInsertValue = "FALSE")
    private Boolean isBlocked;

}
