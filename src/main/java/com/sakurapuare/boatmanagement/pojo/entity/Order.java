package com.sakurapuare.boatmanagement.pojo.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.RelationOneToOne;
import com.mybatisflex.annotation.Table;
import lombok.*;

import java.math.BigDecimal;

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
@Table("orders")
public class Order extends BaseEntity {

    @Id(keyType = KeyType.Auto)
    private Long orderId;

    @RelationOneToOne(selfField = "userId", targetField = "userId")
    private User user;

    @RelationOneToOne(selfField = "ticketId", targetField = "ticketId")
    private Ticket ticket;

    private BigDecimal totalAmount;

    private Integer paymentStatus;
}
