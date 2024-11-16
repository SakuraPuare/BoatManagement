package com.sakurapuare.boatmanagement.pojo.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.RelationOneToOne;
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
@Table("boats")
public class Boat extends BaseEntity {

    @Id(keyType = KeyType.Auto)
    private Long boatId;

    private String boatName;

    private Long boatTypeId;

    @RelationOneToOne(selfField = "boatTypeId", targetField = "boatTypeId")
    private BoatType boatType;

    private Integer status;

}
