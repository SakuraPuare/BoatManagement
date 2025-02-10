package com.sakurapuare.boatmanagement.pojo.vo;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class BaseCertifyVO<T> {
    
    private T certify;

    private String status;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
