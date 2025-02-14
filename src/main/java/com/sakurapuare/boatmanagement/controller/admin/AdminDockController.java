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

    @GetMapping("/list")
    @Operation(summary = "获取码头列表")
    public Response<List<BaseDocksVO>> getDocks(@RequestParam BaseDocksDTO queryDTO) {
        return Response.success("获取码头列表成功", docksService.getListQuery(queryDTO));
    }

    @GetMapping("/page")
    @Operation(summary = "获取码头列表分页")
    public Response<Page<BaseDocksVO>> getDocksPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                                                    @RequestParam BaseDocksDTO queryDTO) {
        return Response.success("获取码头列表分页成功", docksService.getPageQuery(pageNum, pageSize, queryDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取码头详情")
    public Response<BaseDocksVO> getDocksById(@PathVariable Long id) {
        Docks docks = docksService.getById(id);
        BaseDocksVO docksVO = new BaseDocksVO();
        BeanUtils.copyProperties(docks, docksVO);
        return Response.success("获取码头详情成功", docksVO);
    }

    @PostMapping("/")
    @Operation(summary = "添加码头")
    public Response<String> addDocks(@RequestBody BaseDocksDTO docksDTO) {
        docksService.addDocks(docksDTO);
        return Response.success("添加码头成功");
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新码头")
    public Response<String> updateDocks(@PathVariable Long id, @RequestBody BaseDocksDTO docksDTO) {
        docksService.updateDocks(id, docksDTO);
        return Response.success("更新码头成功");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除码头")
    public Response<String> deleteDocks(@PathVariable Long id) {
        docksService.deleteDocks(id);
        return Response.success("删除码头成功");
    }
}
