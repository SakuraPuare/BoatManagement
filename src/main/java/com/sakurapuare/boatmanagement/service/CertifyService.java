package com.sakurapuare.boatmanagement.service;

import com.sakurapuare.boatmanagement.pojo.dto.UnitCertifyRequestDTO;
import com.sakurapuare.boatmanagement.pojo.dto.UserCertifyRequestDTO;

public interface CertifyService {

    void certifyUser(UserCertifyRequestDTO userCertifyRequestDTO);

    void certifyMerchant(UnitCertifyRequestDTO unitCertifyRequestDTO);

    void certifyVendor(UnitCertifyRequestDTO unitCertifyRequestDTO);

    void certifyAdmin();

}