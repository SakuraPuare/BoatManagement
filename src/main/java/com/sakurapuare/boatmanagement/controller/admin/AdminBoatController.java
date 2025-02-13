package com.sakurapuare.boatmanagement.controller.admin;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.common.Response;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseBoatsDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Boats;
import com.sakurapuare.boatmanagement.service.BoatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/boat")
@Tag(name = "管理员-船舶模块", description = "船舶模块")
@RequiredArgsConstructor
public class AdminBoatController {

    private final BoatsService boatService;

    @GetMapping("/list")
    @Operation(summary = "获取船舶列表")
    public Response<List<Boats>> list() {
        return Response.success("获取船舶列表成功", boatService.list());
    }

    @GetMapping("/page")
    @Operation(summary = "获取船舶列表分页")
    public Response<Page<Boats>> listPage(@RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer size) {
        return Response.success("获取船舶列表分页成功", boatService.page(new Page<>(page, size)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取船舶详情")
    public Response<Boats> get(@PathVariable Long id) {
        return Response.success("获取船舶详情成功", boatService.getById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新船舶")
    public Response<Boats> update(@PathVariable Long id, @RequestBody BaseBoatsDTO baseBoatsDTO) {
        Boats boats = new Boats();
        BeanUtils.copyProperties(baseBoatsDTO, boats);
        boats.setId(id);
        boatService.updateById(boats);
        return Response.success("更新船舶成功", boats);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除船舶")
    public Response<String> delete(@PathVariable Long id) {
        boatService.removeById(id);
        return Response.success("删除船舶成功");
    }

    @PostMapping("/")
    @Operation(summary = "创建船舶")
    public Response<Boats> create(@RequestBody BaseBoatsDTO baseBoatsDTO) {
        Boats boats = new Boats();
        BeanUtils.copyProperties(baseBoatsDTO, boats);
        boatService.save(boats);
        return Response.success("创建船舶成功", boats);
    }

}
