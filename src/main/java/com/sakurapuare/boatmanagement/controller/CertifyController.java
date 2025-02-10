package com.sakurapuare.boatmanagement.controller;

import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.UnitCertifyRequestDTO;
import com.sakurapuare.boatmanagement.pojo.dto.UserCertifyRequestDTO;
import com.sakurapuare.boatmanagement.service.CertifyService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/certify")
public class CertifyController {

    private final CertifyService certifyService;

    public CertifyController(CertifyService certifyService) {
        this.certifyService = certifyService;
    }

    @PostMapping("user")
    @Operation(summary = "用户实名认证")
    public Response<Boolean> certifyUser(@RequestBody UserCertifyRequestDTO userCertifyRequestDTO) {
        certifyService.certifyUser(userCertifyRequestDTO);
        return Response.success("实名认证中", true);
    }

    @PostMapping("merchant")
    @Operation(summary = "商户实名认证")
    public Response<Boolean> certifyMerchant(@RequestBody UnitCertifyRequestDTO unitCertifyRequestDTO) {
        certifyService.certifyMerchant(unitCertifyRequestDTO);
        return Response.success("实名认证中", true);
    }

    @PostMapping("vendor")
    @Operation(summary = "供应商实名认证")
    public Response<Boolean> certifyVendor(@RequestBody UnitCertifyRequestDTO unitCertifyRequestDTO) {
        certifyService.certifyVendor(unitCertifyRequestDTO);
        return Response.success("实名认证中", true);
    }

    @PostMapping("admin")
    @Operation(summary = "管理员实名认证")
    public Response<Boolean> certifyAdmin() {
        certifyService.certifyAdmin();
        return Response.success("实名认证中", true);
    }
}
