package com.sakurapuare.boatmanagement.controller.admin;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseDocksDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Docks;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseDocksVO;
import com.sakurapuare.boatmanagement.service.DocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dock")
@Tag(name = "AdminDock", description = "码头模块")
@RequiredArgsConstructor
public class AdminDockController {

    private final DocksService docksService;

    @PostMapping("/list")
    @Operation(summary = "获取码头列表")
    public Response<List<BaseDocksVO>> getAdminDocksListQuery(@RequestBody BaseDocksDTO queryDTO) {
        return Response.success("获取码头列表成功", docksService.getListQuery(queryDTO));
    }

    @PostMapping("/page")
    @Operation(summary = "获取码头列表分页")
    public Response<Page<BaseDocksVO>> getAdminDocksPageQuery(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize,
                                                              @RequestBody BaseDocksDTO queryDTO) {
        return Response.success("获取码头列表分页成功", docksService.getPageQuery(pageNum, pageSize, queryDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取码头详情")
    public Response<BaseDocksVO> getAdminDocks(@PathVariable Long id) {
        Docks docks = docksService.getById(id);
        BaseDocksVO docksVO = new BaseDocksVO();
        BeanUtils.copyProperties(docks, docksVO);
        return Response.success("获取码头详情成功", docksVO);
    }

    @PostMapping("/")
    @Operation(summary = "添加码头")
    public Response<String> addAdminDocks(@RequestBody BaseDocksDTO docksDTO) {
        docksService.addAdminDocks(docksDTO);
        return Response.success("添加码头成功");
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新码头")
    public Response<String> updateAdminDocks(@PathVariable Long id, @RequestBody BaseDocksDTO docksDTO) {
        docksService.updateAdminDocks(id, docksDTO);
        return Response.success("更新码头成功");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除码头")
    public Response<String> deleteAdminDocks(@PathVariable Long id) {
        docksService.deleteAdminDocks(id);
        return Response.success("删除码头成功");
    }
}
