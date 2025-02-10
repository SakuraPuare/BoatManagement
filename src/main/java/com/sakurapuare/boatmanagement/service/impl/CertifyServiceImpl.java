package com.sakurapuare.boatmanagement.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.sakurapuare.boatmanagement.common.UserContext;
import com.sakurapuare.boatmanagement.constant.CertifyStatus;
import com.sakurapuare.boatmanagement.constant.UnitsTypes;
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
import com.sakurapuare.boatmanagement.service.base.BaseMerchantsService;
import com.sakurapuare.boatmanagement.service.base.BaseUnitsService;
import com.sakurapuare.boatmanagement.service.base.BaseUserCertifyService;
import com.sakurapuare.boatmanagement.service.base.BaseVendorsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CertifyServiceImpl implements CertifyService {

    private static final UnitsTableDef unitsTableDef = new UnitsTableDef();
    private static final MerchantsTableDef merchantsTableDef = new MerchantsTableDef();
    private static final VendorsTableDef vendorsTableDef = new VendorsTableDef();
    private static final UserCertifyTableDef userCertifyTableDef = new UserCertifyTableDef();
    private final BaseUserCertifyService baseUserCertifyService;
    private final BaseUnitsService unitsService;
    private final BaseMerchantsService baseMerchantsService;
    private final BaseVendorsService baseVendorsService;


    @Override
    public void certifyUser(UserCertifyRequestDTO request) {
        UserCertify userCertify = baseUserCertifyService.getOne(
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
        baseUserCertifyService.saveOrUpdate(userCertify);
    }

    @Override
    public void certifyMerchant(UnitCertifyRequestDTO request) {
        certifyRole(request, UnitsTypes.MERCHANT);
    }

    @Override
    public void certifyVendor(UnitCertifyRequestDTO request) {
        certifyRole(request, UnitsTypes.VENDOR);
    }

    private void certifyRole(UnitCertifyRequestDTO request, String type) {
        Long userId = UserContext.getAccount().getId();
        Units unit = certifyUnit(request, type);

        if (UnitsTypes.MERCHANT.equals(type)) {
            processMerchant(userId, unit, request.getUnitName());
        } else {
            processVendor(userId, unit);
        }
    }

    private Units certifyUnit(UnitCertifyRequestDTO request, String types) {
        Units unit = unitsService.getOne(
                new QueryWrapper()
                        .eq(unitsTableDef.SOCIAL_CREDIT_CODE.getName(), request.getSocialCreditCode()));

        if (unit != null) {
            BeanUtils.copyProperties(request, unit);
            unit.setStatus(CertifyStatus.PENDING);
        } else {
            unit = Units.builder()
                    .name(request.getUnitName())
                    .unitName(request.getUnitName())
                    .socialCreditCode(request.getSocialCreditCode())
                    .legalPerson(request.getLegalPerson())
                    .address(request.getAddress())
                    .contactPhone(request.getContactPhone())
                    .adminUserId(UserContext.getAccount().getId())
                    .types(types)
                    .build();
        }
        unitsService.saveOrUpdate(unit);
        return unit;
    }

    private void processMerchant(Long userId, Units unit, String shopName) {
        Merchants merchant = baseMerchantsService.getOne(
                new QueryWrapper().eq(merchantsTableDef.USER_ID.getName(), userId));

        if (merchant != null) {
            validateStatus(merchant.getStatus(), "商户");
            updateMerchant(merchant, unit.getId());
        } else {
            createMerchant(userId, unit.getId());
        }
    }

    private void processVendor(Long userId, Units unit) {
        Vendors vendor = baseVendorsService.getOne(
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

    private void updateMerchant(Merchants merchant, Long unitId) {
        merchant.setUnitId(unitId);
        merchant.setStatus(CertifyStatus.PENDING);
        baseMerchantsService.updateById(merchant);
    }

    private void createMerchant(Long userId, Long unitId) {
        Merchants newMerchant = Merchants.builder()
                .userId(userId)
                .unitId(unitId)
                .status(CertifyStatus.PENDING)
                .build();
        baseMerchantsService.save(newMerchant);
    }

    private void updateVendor(Vendors vendor, Long unitId) {
        vendor.setUnitId(unitId);
        vendor.setStatus(CertifyStatus.PENDING);
        baseVendorsService.updateById(vendor);
    }

    private void createVendor(Long userId, Long unitId) {
        Vendors newVendor = Vendors.builder()
                .userId(userId)
                .unitId(unitId)
                .status(CertifyStatus.PENDING)
                .build();
        baseVendorsService.save(newVendor);
    }

    @Override
    public UserCertifyVO getUserCertify() {
        UserCertifyVO userCertifyVO = new UserCertifyVO();
        UserCertify userCertify = baseUserCertifyService.getOne(
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
        UnitCertifyVO vo = new UnitCertifyVO();
        Merchants merchant = baseMerchantsService.getOne(
                new QueryWrapper().eq(merchantsTableDef.USER_ID.getName(), UserContext.getAccount().getId()));

        if (merchant == null) {
            vo.setStatus(CertifyStatus.NOT_EXIST);
        } else {
            Units unit = unitsService.getById(merchant.getUnitId());
            if (unit == null) {
                vo.setStatus(CertifyStatus.NOT_EXIST);
            } else {
                vo.setCertify(unit);
                BeanUtils.copyProperties(unit, vo);
            }
        }
        return vo;
    }

    @Override
    public UnitCertifyVO getVendorCertify() {
        UnitCertifyVO vo = new UnitCertifyVO();
        Vendors vendor = baseVendorsService.getOne(
                new QueryWrapper().eq(vendorsTableDef.USER_ID.getName(), UserContext.getAccount().getId()));

        if (vendor == null) {
            vo.setStatus(CertifyStatus.NOT_EXIST);
        } else {
            Units unit = unitsService.getById(vendor.getUnitId());
            if (unit == null) {
                vo.setStatus(CertifyStatus.NOT_EXIST);
            } else {
                vo.setCertify(unit);
                BeanUtils.copyProperties(unit, vo);
            }
        }
        return vo;
    }

    @Override
    public void joinUnit(String types, Long unitId) {
        validateUnitType(types);
        Units unit = validateAndGetUnit(types, unitId);

        switch (types) {
            case UnitsTypes.MERCHANT:
                handleJoinUnit(baseMerchantsService, Merchants.class, unitId);
                break;
            case UnitsTypes.VENDOR:
                handleJoinUnit(baseVendorsService, Vendors.class, unitId);
                break;
        }
    }

    @Override
    public void transferUnit(String types, Long userId) {
        validateUnitType(types);
        if (UserContext.getAccount().getId().equals(userId)) {
            throw new IllegalArgumentException("您不能转让单位给自己");
        }

        switch (types) {
            case UnitsTypes.MERCHANT:
                handleTransferUnit(baseMerchantsService, userId);
                break;
            case UnitsTypes.VENDOR:
                handleTransferUnit(baseVendorsService, userId);
                break;
        }
    }

    @Override
    public void leaveUnit(String types) {
        validateUnitType(types);

        switch (types) {
            case UnitsTypes.MERCHANT:
                handleLeaveUnit(baseMerchantsService);
                break;
            case UnitsTypes.VENDOR:
                handleLeaveUnit(baseVendorsService);
                break;
        }
    }

    private void validateUnitType(String types) {
        if (!UnitsTypes.isValidType(types)) {
            throw new IllegalArgumentException("单位类型不正确");
        }
    }

    private Units validateAndGetUnit(String types, Long unitId) {
        Units unit = unitsService.getById(unitId);
        if (unit == null) {
            throw new IllegalArgumentException("单位不存在");
        }
        if (!unit.getTypes().equals(types)) {
            throw new IllegalArgumentException("单位类型不正确");
        }
        // if (unit.getId().equals(unitId)) {
        // throw new IllegalArgumentException("您已加入该单位");
        // }
        return unit;
    }

    private <T> void handleJoinUnit(IService<T> baseService, Class<T> entityClass, Long unitId) {
        Long userId = UserContext.getAccount().getId();
        T entity = baseService.getOne(
                new QueryWrapper().eq("user_id", userId));

        if (entity == null) {
            // 创建新实体
            try {
                T newEntity = entityClass.getDeclaredConstructor().newInstance();
                // 使用反射设置属性
                entityClass.getMethod("setUserId", Long.class).invoke(newEntity, userId);
                entityClass.getMethod("setUnitId", Long.class).invoke(newEntity, unitId);
                entityClass.getMethod("setStatus", String.class).invoke(newEntity, CertifyStatus.PENDING);
                baseService.save(newEntity);
            } catch (Exception e) {
                throw new RuntimeException("创建实体失败", e);
            }
        } else {
            // 更新现有实体
            Units unit = unitsService.getById(unitId);
            if (userId.equals(unit.getAdminUserId())) {
                throw new IllegalArgumentException("加入其他单位之前，请先转让单位");
            }
            try {
                entityClass.getMethod("setUnitId", Long.class).invoke(entity, unitId);
                baseService.updateById(entity);
            } catch (Exception e) {
                throw new RuntimeException("更新实体失败", e);
            }
        }
    }

    private <T> void handleTransferUnit(IService<T> baseService, Long targetUserId) {
        Long currentUserId = UserContext.getAccount().getId();
        T currentEntity = baseService.getOne(
                new QueryWrapper().eq("user_id", currentUserId));

        if (currentEntity == null) {
            throw new IllegalArgumentException("您未管理任何单位");
        }

        Long unitId = (Long) getFieldValue(currentEntity, "unitId");
        Units unit = unitsService.getById(unitId);
        validateUnitTransfer(unit, currentUserId);

        T targetEntity = baseService.getOne(
                new QueryWrapper().eq("user_id", targetUserId));
        if (targetEntity == null) {
            throw new IllegalArgumentException("目标员工不存在");
        }

        Long targetUnitId = (Long) getFieldValue(targetEntity, "unitId");
        if (!unitId.equals(targetUnitId)) {
            throw new IllegalArgumentException("目标员工不在您管理单位");
        }

        unit.setAdminUserId(targetUserId);
        unitsService.updateById(unit);
    }

    private <T> void handleLeaveUnit(IService<T> baseService) {
        Long userId = UserContext.getAccount().getId();
        T entity = baseService.getOne(
                new QueryWrapper().eq("user_id", userId));

        if (entity == null) {
            throw new IllegalArgumentException("您未加入任何单位");
        }

        Long unitId = (Long) getFieldValue(entity, "unitId");
        Units unit = unitsService.getById(unitId);
        if (unit == null) {
            throw new IllegalArgumentException("您未加入任何单位");
        }

        try {
            entity.getClass().getMethod("setUnitId", Long.class).invoke(entity, (Long) null);
            baseService.saveOrUpdate(entity);
        } catch (Exception e) {
            throw new RuntimeException("离开单位失败", e);
        }
    }

    private void validateUnitTransfer(Units unit, Long currentUserId) {
        if (unit == null) {
            throw new IllegalArgumentException("您未管理任何单位");
        }
        if (!unit.getAdminUserId().equals(currentUserId)) {
            throw new IllegalArgumentException("您不是该单位管理员，无法转让单位");
        }
    }

    private Object getFieldValue(Object obj, String fieldName) {
        try {
            String getterMethod = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            return obj.getClass().getMethod(getterMethod).invoke(obj);
        } catch (Exception e) {
            throw new RuntimeException("获取字段值失败", e);
        }
    }
}