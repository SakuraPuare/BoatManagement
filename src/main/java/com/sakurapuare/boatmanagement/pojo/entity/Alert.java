package com.sakurapuare.boatmanagement.pojo.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.RelationOneToOne;
import com.mybatisflex.annotation.Table;
import lombok.*;

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
@Table("alerts")
public class Alert extends BaseEntity {

    @Id(keyType = KeyType.Auto)
    private Long alertId;

    @RelationOneToOne(selfField = "boatId", targetField = "boatId")
    private Boat boat;

    private String alertType;

    private Timestamp alertTime;

    private String description;

    private Integer status;


}
