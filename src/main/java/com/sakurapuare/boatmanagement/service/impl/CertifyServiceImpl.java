package com.sakurapuare.boatmanagement.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.common.UserContext;
import com.sakurapuare.boatmanagement.constant.CertifyStatus;
import com.sakurapuare.boatmanagement.mapper.MerchantsMapper;
import com.sakurapuare.boatmanagement.mapper.UnitsMapper;
import com.sakurapuare.boatmanagement.mapper.UserCertifyMapper;
import com.sakurapuare.boatmanagement.mapper.VendorsMapper;
import com.sakurapuare.boatmanagement.pojo.dto.UnitCertifyRequestDTO;
import com.sakurapuare.boatmanagement.pojo.dto.UserCertifyRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Merchants;
import com.sakurapuare.boatmanagement.pojo.entity.Units;
import com.sakurapuare.boatmanagement.pojo.entity.UserCertify;
import com.sakurapuare.boatmanagement.pojo.entity.Vendors;
import com.sakurapuare.boatmanagement.pojo.entity.table.MerchantsTableDef;
import com.sakurapuare.boatmanagement.pojo.entity.table.UnitsTableDef;
import com.sakurapuare.boatmanagement.pojo.entity.table.UserCertifyTableDef;
import com.sakurapuare.boatmanagement.pojo.entity.table.VendorsTableDef;
import com.sakurapuare.boatmanagement.pojo.vo.UnitCertifyVO;
import com.sakurapuare.boatmanagement.pojo.vo.UserCertifyVO;
import com.sakurapuare.boatmanagement.service.CertifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

enum CertifyType {
    MERCHANT, VENDOR
}

@Service
@RequiredArgsConstructor
public class CertifyServiceImpl implements CertifyService {

    private static final UnitsTableDef unitsTableDef = new UnitsTableDef();
    private static final MerchantsTableDef merchantsTableDef = new MerchantsTableDef();
    private static final VendorsTableDef vendorsTableDef = new VendorsTableDef();
    private static final UserCertifyTableDef userCertifyTableDef = new UserCertifyTableDef();
    private final UserCertifyMapper userCertifyMapper;
    private final UnitsMapper unitMapper;
    private final MerchantsMapper merchantsMapper;
    private final VendorsMapper vendorsMapper;

    @Override
    public void certifyUser(UserCertifyRequestDTO request) {
        UserCertify userCertify = userCertifyMapper.selectOneByQuery(
                new QueryWrapper().eq(userCertifyTableDef.USER_ID.getName(), UserContext.getAccount().getId()));

        if (userCertify != null) {
            validateStatus(userCertify.getStatus(), "用户");
            BeanUtils.copyProperties(request, userCertify);
            userCertify.setStatus(CertifyStatus.PENDING);
        } else {
            userCertify = UserCertify.builder()
                    .userId(UserContext.getAccount().getId())
                    .realName(request.getRealName())
                    .idCard(request.getIdCard())
                    .status(CertifyStatus.PENDING)
                    .build();
        }
        userCertifyMapper.insertOrUpdateSelective(userCertify);
    }

    @Override
    public void certifyMerchant(UnitCertifyRequestDTO request) {
        certifyRole(request, CertifyType.MERCHANT);
    }

    @Override
    public void certifyVendor(UnitCertifyRequestDTO request) {
        certifyRole(request, CertifyType.VENDOR);
    }

    private void certifyRole(UnitCertifyRequestDTO request, CertifyType type) {
        Long userId = UserContext.getAccount().getId();
        Units unit = certifyUnit(request);

        if (type == CertifyType.MERCHANT) {
            processMerchant(userId, unit, request.getUnitName());
        } else {
            processVendor(userId, unit);
        }
    }

    private Units certifyUnit(UnitCertifyRequestDTO request) {
        Units unit = unitMapper.selectOneByQuery(
                new QueryWrapper()
                        .eq(unitsTableDef.SOCIAL_CREDIT_CODE.getName(), request.getSocialCreditCode()));

        if (unit != null) {
            BeanUtils.copyProperties(request, unit);
            unit.setStatus(CertifyStatus.PENDING);
        } else {
            unit = Units.builder()
                    .name(request.getUnitName())
                    .socialCreditCode(request.getSocialCreditCode())
                    .legalPerson(request.getLegalPerson())
                    .address(request.getAddress())
                    .contactPhone(request.getContactPhone())
                    .adminUserId(UserContext.getAccount().getId())
                    .build();
        }
        unitMapper.insertOrUpdateSelective(unit);
        return unit;
    }

    private void processMerchant(Long userId, Units unit, String shopName) {
        Merchants merchant = merchantsMapper.selectOneByQuery(
                new QueryWrapper().eq(merchantsTableDef.USER_ID.getName(), userId));

        if (merchant != null) {
            validateStatus(merchant.getStatus(), "商户");
            updateMerchant(merchant, unit.getId(), shopName);
        } else {
            createMerchant(userId, unit.getId(), shopName);
        }
    }

    private void processVendor(Long userId, Units unit) {
        Vendors vendor = vendorsMapper.selectOneByQuery(
                new QueryWrapper().eq(vendorsTableDef.USER_ID.getName(), userId));

        if (vendor != null) {
            validateStatus(vendor.getStatus(), "供应商");
            updateVendor(vendor, unit.getId());
        } else {
            createVendor(userId, unit.getId());
        }
    }

    private void validateStatus(String status, String role) {
        if (CertifyStatus.APPROVED.equals(status)) {
            throw new IllegalArgumentException(String.format("%s已认证", role));
        }
        if (CertifyStatus.PENDING.equals(status)) {
            throw new IllegalArgumentException(String.format("%s审核中", role));
        }
    }

    private void updateMerchant(Merchants merchant, Long unitId, String shopName) {
        merchant.setUnitId(unitId);
        merchant.setShopName(shopName);
        merchant.setStatus(CertifyStatus.PENDING);
        merchantsMapper.update(merchant);
    }

    private void createMerchant(Long userId, Long unitId, String shopName) {
        Merchants newMerchant = Merchants.builder()
                .userId(userId)
                .unitId(unitId)
                .shopName(shopName)
                .status(CertifyStatus.PENDING)
                .build();
        merchantsMapper.insertSelective(newMerchant);
    }

    private void updateVendor(Vendors vendor, Long unitId) {
        vendor.setUnitId(unitId);
        vendor.setStatus(CertifyStatus.PENDING);
        vendorsMapper.update(vendor);
    }

    private void createVendor(Long userId, Long unitId) {
        Vendors newVendor = Vendors.builder()
                .userId(userId)
                .unitId(unitId)
                .status(CertifyStatus.PENDING)
                .build();
        vendorsMapper.insertSelective(newVendor);
    }

    @Override
    public UserCertifyVO getUserCertify() {
        UserCertifyVO userCertifyVO = new UserCertifyVO();
        UserCertify userCertify = userCertifyMapper.selectOneByQuery(
                new QueryWrapper().eq(userCertifyTableDef.USER_ID.getName(), UserContext.getAccount().getId()));
        if (userCertify == null) {
            userCertifyVO.setStatus(CertifyStatus.NOT_EXIST);
        } else {
            userCertifyVO.setCertify(userCertify);
            BeanUtils.copyProperties(userCertify, userCertifyVO);
        }
        return userCertifyVO;
    }

    @Override
    public UnitCertifyVO getMerchantCertify() {
        return getUnitCertify();
    }

    @Override
    public UnitCertifyVO getVendorCertify() {
        return getUnitCertify();
    }

    // 抽取公共方法处理Unit认证
    private UnitCertifyVO getUnitCertify() {
        UnitCertifyVO vo = new UnitCertifyVO();
        Units unit = unitMapper.selectOneByQuery(
            new QueryWrapper().eq(unitsTableDef.ADMIN_USER_ID.getName(), 
                UserContext.getAccount().getId())
        );

        if (unit == null) {
            vo.setStatus(CertifyStatus.NOT_EXIST);
        } else {
            vo.setCertify(unit);
            BeanUtils.copyProperties(unit, vo);
        }
        return vo;
    }

}