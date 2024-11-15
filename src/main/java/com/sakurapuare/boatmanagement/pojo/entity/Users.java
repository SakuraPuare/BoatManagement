package com.sakurapuare.boatmanagement.pojo.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

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
public class Users extends BaseEntity implements Serializable  {

    @Serial
    private static final long serialVersionUID = 1L;

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
