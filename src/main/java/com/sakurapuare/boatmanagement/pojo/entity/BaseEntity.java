package com.sakurapuare.boatmanagement.pojo.entity;

import com.mybatisflex.annotation.Column;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class BaseEntity {

    @Column(isLogicDelete = true)
    private Boolean isDeleted;

    @Column(onInsertValue = "now()")
    private Timestamp createdAt;

    @Column(onUpdateValue = "now()")
    private Timestamp updatedAt;
}
