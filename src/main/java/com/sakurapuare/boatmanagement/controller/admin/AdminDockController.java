package com.sakurapuare.boatmanagement.controller.admin;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseDocksDTO;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseDocksVO;
import com.sakurapuare.boatmanagement.service.DocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
    public Response<List<BaseDocksVO>> getDockList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseDocksDTO filter) {
        return Response.success("获取码头列表成功", 
                docksService.adminGetDockList(search, sort, startDateTime, endDateTime, filter));
    }

    @PostMapping("/page")
    @Operation(summary = "分页获取码头列表")
    public Response<Page<BaseDocksVO>> getDockPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseDocksDTO filter) {
        return Response.success("获取码头列表分页成功", 
                docksService.adminGetDockPage(pageNum, pageSize, search, sort, startDateTime, endDateTime, filter));
    }

    @GetMapping("/ids")
    @Operation(summary = "根据ID获取码头列表")
    public Response<List<BaseDocksVO>> getDockByIds(@RequestParam String ids) {
        return Response.success("根据ID获取码头列表成功", docksService.adminGetDockByIds(ids));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取码头详情")
    public Response<BaseDocksVO> getDock(@PathVariable Long id) {
        return Response.success("获取码头详情成功", docksService.adminGetDockByIds(id.toString()).get(0));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新码头")
    public Response<BaseDocksVO> updateDock(@PathVariable Long id, @RequestBody BaseDocksDTO docksDTO) {
        return Response.success("更新码头成功", docksService.adminUpdateDock(id, docksDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除码头")
    public Response<String> deleteDock(@PathVariable Long id) {
        docksService.adminDeleteDock(id);
        return Response.success("删除码头成功");
    }

    @PostMapping("/")
    @Operation(summary = "创建码头")
    public Response<BaseDocksVO> createDock(@RequestBody BaseDocksDTO docksDTO) {
        return Response.success("创建码头成功", docksService.adminCreateDock(docksDTO));
    }
}
