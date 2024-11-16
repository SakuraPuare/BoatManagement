package com.sakurapuare.boatmanagement.pojo.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.RelationOneToOne;
import com.mybatisflex.annotation.Table;
import lombok.*;

import java.math.BigDecimal;
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
@Table("payments")
public class Payment extends BaseEntity {

    @Id(keyType = KeyType.Auto)
    private Long paymentId;

    @RelationOneToOne(selfField = "orderId", targetField = "orderId")
    private Order order;

    private Timestamp paymentTime;

    private BigDecimal amount;

    private Integer paymentMethod;


}
