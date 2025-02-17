package com.sakurapuare.boatmanagement.controller.admin;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.CertifyQueryDTO;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseUnitsVO;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseUserCertifyVO;
import com.sakurapuare.boatmanagement.service.CertifyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/audit")
@Tag(name = "AdminAudit", description = "审核模块")
@RequiredArgsConstructor
public class AdminAuditController {

    private final CertifyService certifyService;

    @PostMapping("unit/list")
    @Operation(summary = "获取所有单位认证列表")
    public Response<List<BaseUnitsVO>> getAdminUnitListQuery(@RequestBody CertifyQueryDTO queryDTO) {
        return Response.success("获取单位认证列表成功", certifyService.getAdminUnitListQuery(queryDTO));
    }

    @PostMapping("unit/page")
    @Operation(summary = "获取所有单位认证列表分页")
    public Response<Page<BaseUnitsVO>> getAdminUnitPageQuery(@RequestParam(defaultValue = "1") Integer pageNum,
                                                @RequestParam(defaultValue = "10") Integer pageSize, @RequestBody CertifyQueryDTO queryDTO) {
        return Response.success("获取单位认证列表成功", certifyService.getAdminUnitPageQuery(pageNum, pageSize, queryDTO));
    }

    @PutMapping("unit/{id}")
    @Operation(summary = "审核操作")
    public Response<String> auditAdminUnit(@PathVariable Long id, String types) {
        certifyService.auditAdminUnit(types, id);
        return Response.success("审核完成");
    }

    @PostMapping("user/list")
    @Operation(summary = "获取所有用户认证列表")
    public Response<List<BaseUserCertifyVO>> getAdminUserListQuery(@RequestBody CertifyQueryDTO queryDTO) {
        return Response.success("获取用户认证列表成功", certifyService.getAdminUserListQuery(queryDTO));
    }

    @PostMapping("user/page")
    @Operation(summary = "获取所有用户认证列表分页")
    public Response<Page<BaseUserCertifyVO>> getAdminUserPageQuery(@RequestParam(defaultValue = "1") Integer pageNum,
                                                          @RequestParam(defaultValue = "10") Integer pageSize, @RequestBody CertifyQueryDTO queryDTO) {
        return Response.success("获取用户认证列表成功", certifyService.getAdminUserPageQuery(pageNum, pageSize, queryDTO));
    }

    @PutMapping("user/{id}")
    @Operation(summary = "审核操作")
    public Response<String> auditAdminUser(@PathVariable Long id, String types) {
        certifyService.auditAdminUser(types, id);
        return Response.success("审核完成");
    }

}
