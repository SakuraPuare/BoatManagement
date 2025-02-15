package com.sakurapuare.boatmanagement.controller;

import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.UnitCertifyRequestDTO;
import com.sakurapuare.boatmanagement.pojo.dto.UserCertifyRequestDTO;
import com.sakurapuare.boatmanagement.pojo.vo.BaseCertifyVO;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseUnitsVO;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseUserCertifyVO;
import com.sakurapuare.boatmanagement.service.CertifyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/certify")
@Tag(name = "CertifyController", description = "资质认证模块")
@RequiredArgsConstructor
public class CertifyController {

    private final CertifyService certifyService;

    @GetMapping("user")
    @Operation(summary = "获取用户实名认证信息")
    public Response<BaseCertifyVO<BaseUserCertifyVO>> getUserCertify() {
        return Response.success(certifyService.getUserCertify());
    }

    @PostMapping("user")
    @Operation(summary = "用户实名认证")
    public Response<String> certifyUser(@RequestBody UserCertifyRequestDTO userCertifyRequestDTO) {
        certifyService.certifyUser(userCertifyRequestDTO);
        return Response.success("实名认证中");
    }

    @GetMapping("merchant")
    @Operation(summary = "获取商户实名认证信息")
    public Response<BaseCertifyVO<BaseUnitsVO>> getMerchantCertify() {
        return Response.success(certifyService.getMerchantCertify());
    }

    @PostMapping("merchant")
    @Operation(summary = "商户实名认证")
    public Response<String> certifyMerchant(@RequestBody UnitCertifyRequestDTO unitCertifyRequestDTO) {
        certifyService.certifyMerchant(unitCertifyRequestDTO);
        return Response.success("实名认证中");
    }

    @GetMapping("vendor")
    @Operation(summary = "获取供应商实名认证信息")
    public Response<BaseCertifyVO<BaseUnitsVO>> getVendorCertify() {
        return Response.success(certifyService.getVendorCertify());
    }

    @PostMapping("vendor")
    @Operation(summary = "供应商实名认证")
    public Response<String> certifyVendor(@RequestBody UnitCertifyRequestDTO unitCertifyRequestDTO) {
        certifyService.certifyVendor(unitCertifyRequestDTO);
        return Response.success("实名认证中");
    }

    @PutMapping("join/{types}/{unitId}")
    @Operation(summary = "加入单位")
    public Response<String> joinUnit(@PathVariable String types, @PathVariable Long unitId) {
        certifyService.joinUnit(types, unitId);
        return Response.success("已加入单位");
    }

    @PutMapping("transfer/{types}/{userId}")
    @Operation(summary = "转让单位")
    public Response<String> transferUnit(@PathVariable String types, @PathVariable Long userId) {
        certifyService.transferUnit(types, userId);
        return Response.success("已转让单位");
    }

    @DeleteMapping("leave/{types}")
    @Operation(summary = "离开单位")
    public Response<String> leaveUnit(@PathVariable String types) {
        certifyService.leaveUnit(types);
        return Response.success("已离开单位");
    }
}
