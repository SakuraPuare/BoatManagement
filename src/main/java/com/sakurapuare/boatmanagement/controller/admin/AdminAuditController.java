package com.sakurapuare.boatmanagement.controller.admin;

import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.CertifyQueryDTO;
import com.sakurapuare.boatmanagement.service.CertifyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/audit")
@Tag(name = "管理员-审核模块", description = "审核模块")
@RequiredArgsConstructor
public class AdminAuditController {

    private final CertifyService certifyService;

    @GetMapping("/{type}/list")
    @Operation(summary = "获取所有认证列表")
    public Response<List<CertifyService>> list(@PathVariable String type, CertifyQueryDTO queryDTO) {
        // return Response.success("获取认证列表成功", certifyService.getListByType(type,
        // status));
        return null;
    }
}
