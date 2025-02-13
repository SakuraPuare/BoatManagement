package com.sakurapuare.boatmanagement.controller.admin;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.CertifyQueryDTO;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseUnitsVO;
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

    @GetMapping("/list")
    @Operation(summary = "获取所有认证列表")
    public Response<List<BaseUnitsVO>> list(@RequestParam CertifyQueryDTO queryDTO) {
        return Response.success("获取认证列表成功", certifyService.getListQuery(queryDTO));
    }

    @GetMapping("/page")
    @Operation(summary = "获取所有认证列表分页")
    public Response<Page<BaseUnitsVO>> listPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                                @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam CertifyQueryDTO queryDTO) {
        return Response.success("获取认证列表成功", certifyService.getPageQuery(pageNum, pageSize, queryDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "审核操作")
    public Response<String> audit(@PathVariable Long id, String types) {
        certifyService.audit(types, id);
        return Response.success("审核完成");
    }

}
