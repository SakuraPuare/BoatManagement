package com.sakurapuare.boatmanagement.controller.admin;

import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.service.CertifyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/audit")
@Tag(name = "AdminAudit", description = "审核模块")
@RequiredArgsConstructor
public class AdminAuditController {

    private final CertifyService certifyService;

    @PutMapping("unit/{id}")
    @Operation(summary = "审核操作")
    public Response<String> auditAdminUnit(@PathVariable Long id, String types) {
        certifyService.auditAdminUnit(types, id);
        return Response.success("审核完成");
    }

    @PutMapping("user/{id}")
    @Operation(summary = "审核操作")
    public Response<String> auditAdminUser(@PathVariable Long id, String types) {
        certifyService.auditAdminUser(types, id);
        return Response.success("审核完成");
    }

}
