package com.sakurapuare.boatmanagement.pojo.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.RelationOneToOne;
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
@Table("alerts")
public class Alerts extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Integer alertId;

    @RelationOneToOne(selfField="boatId", targetField="boatId")
    private Boats boat;

    private String alertType;

    private Timestamp alertTime;

    private String description;

    private Integer status;


}
