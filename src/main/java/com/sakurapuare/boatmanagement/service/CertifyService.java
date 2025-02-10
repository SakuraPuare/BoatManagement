package com.sakurapuare.boatmanagement.service;

import com.sakurapuare.boatmanagement.pojo.dto.UnitCertifyRequestDTO;
import com.sakurapuare.boatmanagement.pojo.dto.UserCertifyRequestDTO;
import com.sakurapuare.boatmanagement.pojo.vo.UnitCertifyVO;
import com.sakurapuare.boatmanagement.pojo.vo.UserCertifyVO;

public interface CertifyService {

    void certifyUser(UserCertifyRequestDTO userCertifyRequestDTO);

    void certifyMerchant(UnitCertifyRequestDTO unitCertifyRequestDTO);

    void certifyVendor(UnitCertifyRequestDTO unitCertifyRequestDTO);

    UserCertifyVO getUserCertify();

    UnitCertifyVO getMerchantCertify();

    UnitCertifyVO getVendorCertify();


}