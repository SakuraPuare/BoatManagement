package com.sakurapuare.boatmanagement.controller.admin;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.CertifyQueryDTO;
import com.sakurapuare.boatmanagement.pojo.vo.UnitCertifyVO;
import com.sakurapuare.boatmanagement.pojo.vo.base.UnitsVO;
import com.sakurapuare.boatmanagement.service.CertifyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/audit")
@Tag(name = "管理员-审核模块", description = "审核模块")
@RequiredArgsConstructor
public class AdminAuditController {

    private final CertifyService certifyService;

    @GetMapping("/list")
    @Operation(summary = "获取所有认证列表")
    public Response<List<UnitsVO>> list(CertifyQueryDTO queryDTO) {
        return Response.success("获取认证列表成功", certifyService.getListQuery(queryDTO));
    }

    @GetMapping("/list/page")
    @Operation(summary = "获取所有认证列表分页")
    public Response<Page<UnitsVO>> listPage(@RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize, CertifyQueryDTO queryDTO) {
        return Response.success("获取认证列表成功", certifyService.getPageQuery(pageNum, pageSize, queryDTO));
    }
}
