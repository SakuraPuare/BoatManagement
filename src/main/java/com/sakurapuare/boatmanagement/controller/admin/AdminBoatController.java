package com.sakurapuare.boatmanagement.controller.admin;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseBoatsDTO;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseBoatsVO;
import com.sakurapuare.boatmanagement.service.BoatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/boat")
@Tag(name = "AdminBoat", description = "船舶模块")
@RequiredArgsConstructor
public class AdminBoatController {

    private final BoatsService boatService;

    @PostMapping("/list")
    @Operation(summary = "获取船舶列表")
    public Response<List<BaseBoatsVO>> adminGetBoatList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseBoatsDTO filter) {
        return Response.success("获取船舶列表成功", boatService.adminGetBoatList(search, sort, startDateTime, endDateTime, filter));
    }

    @PostMapping("/page")
    @Operation(summary = "分页获取船舶列表")
    public Response<Page<BaseBoatsVO>> adminGetBoatPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestBody(required = false) BaseBoatsDTO filter) {
        return Response.success("获取船舶列表分页成功", 
                boatService.adminGetBoatPage(pageNum, pageSize, search, sort, startDateTime, endDateTime, filter));
    }

    @GetMapping("/ids")
    @Operation(summary = "根据 ID 获取船舶列表")
    public Response<List<BaseBoatsVO>> adminGetBoatByIds(@RequestParam String ids) {
        return Response.success("根据 ID 获取船舶列表成功", boatService.adminGetBoatByIds(ids));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取船舶详情")
    public Response<BaseBoatsVO> adminGetBoat(@PathVariable Long id) {
        return Response.success("获取船舶详情成功", boatService.adminGetBoatByIds(id.toString()).get(0));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新船舶")
    public Response<BaseBoatsVO> adminUpdateBoat(@PathVariable Long id, @RequestBody BaseBoatsDTO baseBoatsDTO) {
        return Response.success("更新船舶成功", boatService.adminUpdateBoat(id, baseBoatsDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除船舶")
    public Response<String> adminDeleteBoat(@PathVariable Long id) {
        boatService.adminDeleteBoat(id);
        return Response.success("删除船舶成功");
    }

    @PostMapping("/")
    @Operation(summary = "创建船舶")
    public Response<BaseBoatsVO> adminCreateBoat(@RequestBody BaseBoatsDTO baseBoatsDTO) {
        return Response.success("创建船舶成功", boatService.adminCreateBoat(baseBoatsDTO));
    }
}
