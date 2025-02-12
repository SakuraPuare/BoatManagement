package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.pojo.dto.CertifyQueryDTO;
import com.sakurapuare.boatmanagement.pojo.dto.UnitCertifyRequestDTO;
import com.sakurapuare.boatmanagement.pojo.dto.UserCertifyRequestDTO;
import com.sakurapuare.boatmanagement.pojo.vo.UnitCertifyVO;
import com.sakurapuare.boatmanagement.pojo.vo.base.UnitsVO;
import com.sakurapuare.boatmanagement.pojo.vo.UserCertifyVO;

import java.util.List;

public interface CertifyService {

    void certifyUser(UserCertifyRequestDTO userCertifyRequestDTO);

    void certifyMerchant(UnitCertifyRequestDTO unitCertifyRequestDTO);

    void certifyVendor(UnitCertifyRequestDTO unitCertifyRequestDTO);

    UserCertifyVO getUserCertify();

    UnitCertifyVO getMerchantCertify();

    UnitCertifyVO getVendorCertify();

    void joinUnit(String types, Long unitId);

    void transferUnit(String types, Long userId);

    void leaveUnit(String types);

    List<UnitsVO> getListQuery(CertifyQueryDTO queryDTO);

    Page<UnitsVO> getPageQuery(Integer pageNum, Integer pageSize, CertifyQueryDTO queryDTO);
}