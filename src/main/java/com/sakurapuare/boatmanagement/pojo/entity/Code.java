package com.sakurapuare.boatmanagement.pojo.entity;

import com.mybatisflex.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 实体类。
 *
 * @author sakurapuare
 * @since 2024-11-16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("verification_codes")
public class Code {

    @Id(keyType = KeyType.Auto)
    private Long id;

    @RelationManyToOne(selfField = "userId", targetField = "id")
    private User user;

    private String code;

    @Column(onInsertValue = "now()")
    private Timestamp requestTime;

    private Timestamp expirationTime;

    @Column(onInsertValue = "FALSE")
    private Boolean isUsed;
}
