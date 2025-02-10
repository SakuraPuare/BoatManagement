package com.sakurapuare.boatmanagement.pojo.dto;

import lombok.Data;

@Data
public class UnitCertifyRequestDTO {
    private String unitName;
    private String socialCreditCode;
    private String legalPerson;
    private String address;
    private String contactPhone;
}
