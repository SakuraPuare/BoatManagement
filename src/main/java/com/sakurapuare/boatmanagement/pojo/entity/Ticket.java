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
@Table("tickets")
public class Ticket extends BaseEntity {

    @Id(keyType = KeyType.Auto)
    private Long ticketId;

    @RelationOneToOne(selfField = "userId", targetField = "userId")
    private User user;

    @RelationOneToOne(selfField = "boatId", targetField = "boatId")
    private Boat boat;

    private Timestamp startTime;

    private Timestamp endTime;

    @RelationOneToOne(selfField = "departureDockId", targetField = "dockId")
    private Dock departureDock;

    @RelationOneToOne(selfField = "destinationDockId", targetField = "dockId")
    private Dock destinationDock;

    private BigDecimal price;

    private Integer remainingTickets;

}
