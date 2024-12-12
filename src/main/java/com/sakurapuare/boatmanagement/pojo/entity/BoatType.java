package com.sakurapuare.boatmanagement.pojo.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.*;

/**
 * 船只类型实体类。
 *
 * @author sakurapuare
 * @since 2024-11-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("boat_types")
public class BoatType extends BaseEntity {

    @Id(keyType = KeyType.Auto)
    private Long boatTypeId;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 类型代码
     */
    private String typeCode;

    /**
     * 最大载量（单位：吨）
     */
    private Double maxCapacity;

    /**
     * 最高速度（单位：节）
     */
    private Double maxSpeed;

    /**
     * 燃料类型
     */
    private String fuelType;

    /**
     * 状态（0-禁用 1-启用）
     */
    private Integer status;

}
