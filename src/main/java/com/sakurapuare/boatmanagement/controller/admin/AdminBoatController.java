package com.sakurapuare.boatmanagement.controller.admin;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseBoatsDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Boats;
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

    @GetMapping("/list")
    @Operation(summary = "获取船舶列表")
    public Response<List<BaseBoatsVO>> list(@RequestParam BaseBoatsDTO queryDTO) {
        return Response.success("获取船舶列表成功", boatService.getListQuery(queryDTO));
    }

    @GetMapping("/page")
    @Operation(summary = "获取船舶列表分页")
    public Response<Page<BaseBoatsVO>> listPage(@RequestParam(defaultValue = "1") Integer page,
                                                @RequestParam(defaultValue = "10") Integer size, @RequestParam BaseBoatsDTO queryDTO) {
        return Response.success("获取船舶列表分页成功", boatService.getPageQuery(page, size, queryDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取船舶详情")
    public Response<BaseBoatsVO> get(@PathVariable Long id) {
        Boats boats = boatService.getById(id);
        BaseBoatsVO boatsVO = new BaseBoatsVO();
        BeanUtils.copyProperties(boats, boatsVO);
        return Response.success("获取船舶详情成功", boatsVO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新船舶")
    public Response<String> update(@PathVariable Long id, @RequestBody BaseBoatsDTO baseBoatsDTO) {
        boatService.updateBoat(id, baseBoatsDTO);
        return Response.success("更新船舶成功");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除船舶")
    public Response<String> delete(@PathVariable Long id) {
        boatService.deleteBoat(id);
        return Response.success("删除船舶成功");
    }

    @PostMapping("/")
    @Operation(summary = "创建船舶")
    public Response<String> create(@RequestBody BaseBoatsDTO baseBoatsDTO) {
        boatService.addBoat(baseBoatsDTO);
        return Response.success("创建船舶成功");
    }

}
