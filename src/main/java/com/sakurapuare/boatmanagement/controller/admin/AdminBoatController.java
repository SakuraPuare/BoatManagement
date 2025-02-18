package com.sakurapuare.boatmanagement.controller.admin;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseBoatsDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Boats;
import com.sakurapuare.boatmanagement.pojo.vo.BoatVO;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseBoatsVO;
import com.sakurapuare.boatmanagement.service.BoatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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
    public Response<List<BoatVO>> getAdminBoatListQuery(@RequestBody BaseBoatsDTO queryDTO) {
        return Response.success("获取船舶列表成功", boatService.getAdminBoatListQuery(queryDTO));
    }

    @PostMapping("/page")
    @Operation(summary = "获取船舶列表分页")
    public Response<Page<BoatVO>> getAdminBoatPageQuery(@RequestParam(defaultValue = "1") Integer pageNum,
                                                        @RequestParam(defaultValue = "10") Integer pageSize, @RequestBody BaseBoatsDTO queryDTO) {
        return Response.success("获取船舶列表分页成功", boatService.getAdminBoatPageQuery(pageNum, pageSize, queryDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取船舶详情")
    public Response<BaseBoatsVO> getAdminBoat(@PathVariable Long id) {
        Boats boats = boatService.getById(id);
        BaseBoatsVO boatsVO = new BaseBoatsVO();
        BeanUtils.copyProperties(boats, boatsVO);
        return Response.success("获取船舶详情成功", boatsVO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新船舶")
    public Response<String> updateAdminBoat(@PathVariable Long id, @RequestBody BaseBoatsDTO baseBoatsDTO) {
        boatService.updateBoat(id, baseBoatsDTO);
        return Response.success("更新船舶成功");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除船舶")
    public Response<String> deleteAdminBoat(@PathVariable Long id) {
        boatService.deleteBoat(id);
        return Response.success("删除船舶成功");
    }

    @PostMapping("/")
    @Operation(summary = "创建船舶")
    public Response<String> createAdminBoat(@RequestBody BaseBoatsDTO baseBoatsDTO) {
        boatService.addBoat(baseBoatsDTO);
        return Response.success("创建船舶成功");
    }

}
