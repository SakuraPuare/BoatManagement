package com.sakurapuare.boatmanagement.pojo.entity;

import java.sql.Timestamp;

import com.mybatisflex.annotation.Column;

import lombok.Data;

@Data
public class BaseEntity {
    @Column(isLogicDelete = true)
    private Boolean isDeleted;

    @Column(onInsertValue = "now()")
    private Timestamp createdAt;

    @Column(onUpdateValue = "now()")
    private Timestamp updatedAt;
}
