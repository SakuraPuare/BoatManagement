package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.sakurapuare.boatmanagement.common.context.UserContext;
import com.sakurapuare.boatmanagement.constant.AuditOperation;
import com.sakurapuare.boatmanagement.constant.CertifyStatus;
import com.sakurapuare.boatmanagement.constant.Roles;
import com.sakurapuare.boatmanagement.constant.UnitsTypes;
import com.sakurapuare.boatmanagement.pojo.dto.UnitCertifyRequestDTO;
import com.sakurapuare.boatmanagement.pojo.dto.UserCertifyRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.*;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseUnitsVO;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseUserCertifyVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.*;

@Service
@RequiredArgsConstructor
public class CertifyService {

    private final UserCertifyService userCertifyService;
    private final UnitsService unitsService;
    private final MerchantsService merchantsService;
    private final VendorsService vendorsService;
    private final AccountsService accountsService;
    private final LogsService logsService;
    private final RoleService roleService;

    public void certifyUser(UserCertifyRequestDTO request) {
        UserCertify userCertify = userCertifyService.getOne(
                new QueryWrapper().where(USER_CERTIFY.USER_ID.eq(UserContext.getAccount().getId())));

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
        userCertifyService.saveOrUpdate(userCertify);
    }

    public void certifyMerchant(UnitCertifyRequestDTO request) {
        certifyRole(request, UnitsTypes.MERCHANT);
    }

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
                        .where(UNITS.SOCIAL_CREDIT_CODE.eq(request.getSocialCreditCode())));

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
        Merchants merchant = merchantsService.getOne(
                new QueryWrapper().where(MERCHANTS.USER_ID.eq(userId)));

        if (merchant != null) {
            validateStatus(merchant.getStatus(), "商户");
            updateMerchant(merchant, unit.getId());
        } else {
            createMerchant(userId, unit.getId());
        }
    }

    private void processVendor(Long userId, Units unit) {
        Vendors vendor = vendorsService.getOne(
                new QueryWrapper().where(VENDORS.USER_ID.eq(userId)));

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
        merchantsService.updateById(merchant);
    }

    private void createMerchant(Long userId, Long unitId) {
        Merchants newMerchant = Merchants.builder()
                .userId(userId)
                .unitId(unitId)
                .status(CertifyStatus.PENDING)
                .build();
        merchantsService.save(newMerchant);
    }

    private void updateVendor(Vendors vendor, Long unitId) {
        vendor.setUnitId(unitId);
        vendor.setStatus(CertifyStatus.PENDING);
        vendorsService.updateById(vendor);
    }

    private void createVendor(Long userId, Long unitId) {
        Vendors newVendor = Vendors.builder()
                .userId(userId)
                .unitId(unitId)
                .status(CertifyStatus.PENDING)
                .build();
        vendorsService.save(newVendor);
    }

    public BaseUserCertifyVO getUserCertify() {
        UserCertify userCertify = userCertifyService.getOne(
                new QueryWrapper().where(USER_CERTIFY.USER_ID.eq(UserContext.getAccount().getId())));
        BaseUserCertifyVO baseUserCertifyVO = new BaseUserCertifyVO();
        if (userCertify == null) {
            baseUserCertifyVO.setStatus(CertifyStatus.NOT_EXIST);
        } else {
            BeanUtils.copyProperties(userCertify, baseUserCertifyVO);
        }

        return baseUserCertifyVO;
    }

    public BaseUnitsVO buildUnitCertifyVO(Units unit) {
        BaseUnitsVO unitVO = new BaseUnitsVO();
        BeanUtils.copyProperties(unit, unitVO);
        return unitVO;
    }

    public BaseUnitsVO getMerchantCertify() {
        BaseUnitsVO vo = new BaseUnitsVO();
        Merchants merchant = merchantsService.getOne(
                new QueryWrapper().where(MERCHANTS.USER_ID.eq(UserContext.getAccount().getId())));

        if (merchant == null) {
            vo.setStatus(CertifyStatus.NOT_EXIST);
        } else {
            Units unit = unitsService.getById(merchant.getUnitId());
            if (unit == null) {
                vo.setStatus(CertifyStatus.NOT_EXIST);
            } else {
                vo = buildUnitCertifyVO(unit);
            }
        }
        return vo;
    }

    public BaseUnitsVO getVendorCertify() {
        BaseUnitsVO vo = new BaseUnitsVO();
        Vendors vendor = vendorsService.getOne(
                new QueryWrapper().where(VENDORS.USER_ID.eq(UserContext.getAccount().getId())));

        if (vendor == null) {
            vo.setStatus(CertifyStatus.NOT_EXIST);
        } else {
            Units unit = unitsService.getById(vendor.getUnitId());
            if (unit == null) {
                vo.setStatus(CertifyStatus.NOT_EXIST);
            } else {
                vo = buildUnitCertifyVO(unit);
            }
        }
        return vo;
    }

    public void joinUnit(String types, Long unitId) {
        validateUnitType(types);
        validateAndGetUnit(types, unitId);

        switch (types) {
            case UnitsTypes.MERCHANT:
                handleJoinUnit(merchantsService, Merchants.class, unitId);
                break;
            case UnitsTypes.VENDOR:
                handleJoinUnit(vendorsService, Vendors.class, unitId);
                break;
        }
    }

    public void transferUnit(String types, Long userId) {
        validateUnitType(types);
        if (UserContext.getAccount().getId().equals(userId)) {
            throw new IllegalArgumentException("您不能转让单位给自己");
        }

        switch (types) {
            case UnitsTypes.MERCHANT:
                handleTransferUnit(merchantsService, userId);
                break;
            case UnitsTypes.VENDOR:
                handleTransferUnit(vendorsService, userId);
                break;
        }
    }

    public void leaveUnit(String types) {
        validateUnitType(types);

        switch (types) {
            case UnitsTypes.MERCHANT:
                handleLeaveUnit(merchantsService);
                break;
            case UnitsTypes.VENDOR:
                handleLeaveUnit(vendorsService);
                break;
        }
    }

    private void validateUnitType(String types) {
        if (!UnitsTypes.isValidType(types)) {
            throw new IllegalArgumentException("单位类型不正确");
        }
    }

    private void validateAndGetUnit(String types, Long unitId) {
        Units unit = unitsService.getById(unitId);
        if (unit == null) {
            throw new IllegalArgumentException("单位不存在");
        }
        if (!unit.getTypes().equals(types)) {
            throw new IllegalArgumentException("单位类型不正确");
        }
        if (unit.getStatus().equals(CertifyStatus.PENDING)) {
            throw new IllegalArgumentException("单位审核中");
        }
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
            Long entityUnitId = (Long) getFieldValue(entity, "unitId");
            if (entityUnitId != null) {
                throw new IllegalArgumentException("您已加入其他单位，请先离开此单位");
            }

            try {
                entityClass.getMethod("setUnitId", Long.class).invoke(entity, unitId);
                entityClass.getMethod("setStatus", String.class).invoke(entity, CertifyStatus.PENDING);
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
                new QueryWrapper().eq("user_id", targetUserId)
                        .eq("status", CertifyStatus.APPROVED));
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

    private void approveUnit(Units unit) {
        // 1. 更新单位状态
        unit.setStatus(CertifyStatus.APPROVED);
        unitsService.updateById(unit);

        Accounts account = null;
        Role merchantRole = null;
        Role vendorRole = null;

        switch (unit.getTypes()) {
            case UnitsTypes.MERCHANT: {
                // 2. 更新商户状态
                Merchants merchant = merchantsService.getOne(
                        new QueryWrapper().where(MERCHANTS.UNIT_ID.eq(unit.getId())));
                merchant.setStatus(CertifyStatus.APPROVED);
                merchantsService.updateById(merchant);

                // 3. 更新账号权限 - 找到 MERCHANT 角色
                account = accountsService.getById(merchant.getUserId());
                merchantRole = roleService.getOne(
                        new QueryWrapper().where(ROLE.NAME.eq("MERCHANT")));
                if (merchantRole != null) {
                    roleService.assignRole(merchant.getUserId(), merchantRole.getId(), unit.getId());
                }
                break;
            }
            case UnitsTypes.VENDOR: {
                // 2. 更新供应商状态
                Vendors vendor = vendorsService.getOne(
                        new QueryWrapper().where(VENDORS.UNIT_ID.eq(unit.getId())));
                vendor.setStatus(CertifyStatus.APPROVED);
                vendorsService.updateById(vendor);

                // 3. 更新账号权限 - 找到 VENDOR 角色
                account = accountsService.getById(vendor.getUserId());
                vendorRole = roleService.getOne(
                        new QueryWrapper().where(ROLE.NAME.eq("VENDOR")));
                if (vendorRole != null) {
                    roleService.assignRole(vendor.getUserId(), vendorRole.getId(), unit.getId());
                }
                break;
            }
        }
    }

    private void rejectUnit(Units unit) {
        // 1. 更新单位状态
        unit.setStatus(CertifyStatus.REJECTED);
        unitsService.updateById(unit);

        Accounts account = null;
        Role merchantRole = null;
        Role vendorRole = null;

        switch (unit.getTypes()) {

            case UnitsTypes.MERCHANT: {
                // 2. 更新商户状态
                Merchants merchant = merchantsService.getOne(
                        new QueryWrapper().where(MERCHANTS.UNIT_ID.eq(unit.getId())));
                merchant.setStatus(CertifyStatus.REJECTED);
                merchantsService.updateById(merchant);

                // 3. 更新账号权限 - 撤销 MERCHANT 角色
                account = accountsService.getById(merchant.getUserId());
                merchantRole = roleService.getOne(
                        new QueryWrapper().where(ROLE.NAME.eq(Roles.MERCHANT)));
                if (merchantRole != null) {
                    roleService.revokeRole(merchant.getUserId(), merchantRole.getId(), unit.getId());
                }
                break;
            }
            case UnitsTypes.VENDOR: {
                Vendors vendor = vendorsService.getOne(
                        new QueryWrapper().where(VENDORS.UNIT_ID.eq(unit.getId())));
                vendor.setStatus(CertifyStatus.REJECTED);
                vendorsService.updateById(vendor);

                // 3. 更新账号权限 - 撤销 VENDOR 角色
                account = accountsService.getById(vendor.getUserId());
                vendorRole = roleService.getOne(
                        new QueryWrapper().where(ROLE.NAME.eq(Roles.VENDOR)));
                if (vendorRole != null) {
                    roleService.revokeRole(vendor.getUserId(), vendorRole.getId(), unit.getId());
                }
                break;
            }
        }
    }

    private void approveUser(UserCertify userCertify) {
        userCertify.setStatus(CertifyStatus.APPROVED);
        userCertifyService.updateById(userCertify);
    }

    private void rejectUser(UserCertify userCertify) {
        userCertify.setStatus(CertifyStatus.REJECTED);
        userCertifyService.updateById(userCertify);
    }

    public void auditAdminUnit(String types, Long id) {
        if (!AuditOperation.isAuditOperation(types)) {
            throw new IllegalArgumentException("不支持的审核操作");
        }

        Units unit = unitsService.getById(id);
        if (unit == null) {
            throw new IllegalArgumentException("单位不存在");
        }

        // add to logs
        switch (types) {
            case AuditOperation.APPROVED:
                approveUnit(unit);
                logsService.info(AuditOperation.AUDIT, "审核通过" + unit.getName());
                break;
            case AuditOperation.REJECTED:
                rejectUnit(unit);
                logsService.info(AuditOperation.AUDIT, "审核拒绝" + unit.getName());
                break;
        }
    }

    public void auditAdminUser(String types, Long id) {
        if (!AuditOperation.isAuditOperation(types)) {
            throw new IllegalArgumentException("不支持的审核操作");
        }

        UserCertify userCertify = userCertifyService.getById(id);
        if (userCertify == null) {
            throw new IllegalArgumentException("用户不存在");
        }

        switch (types) {
            case AuditOperation.APPROVED:
                approveUser(userCertify);
                break;
            case AuditOperation.REJECTED:
                rejectUser(userCertify);
                break;
        }
    }
}