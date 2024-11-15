package com.sakurapuare.boatmanagement.pojo.entity;

import com.mybatisflex.annotation.Column;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(isLogicDelete = true)
    private Boolean isDeleted;

    @Column(onInsertValue = "now()")
    private Timestamp createdAt;

    @Column(onUpdateValue = "now()")
    private Timestamp updatedAt;
}
