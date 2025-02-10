package com.sakurapuare.boatmanagement.pojo.vo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class BaseCertifyVO<T> {

    private T certify;

    private String status;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
