package com.sakurapuare.boatmanagement.pojo.vo;

import lombok.Data;

import java.sql.Timestamp;


@Data
public class UserPersonalInfoVO {
    private Long id;

    private String username;

    private String email;

    private String phone;

    private Integer role;

    private Boolean isActive;

    private Boolean isBlocked;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
