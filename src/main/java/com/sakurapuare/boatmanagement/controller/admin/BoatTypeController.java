package com.sakurapuare.boatmanagement.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.pojo.entity.BoatType;
import com.sakurapuare.boatmanagement.service.BoatTypeService;

/**
 * 控制层。
 *
 * @author sakurapuare
 * @since 2024-11-15
 */
@RestController
@RequestMapping("/boatTypes")
public class BoatTypeController {

    private final BoatTypeService boatTypeService;

    public BoatTypeController(BoatTypeService boatTypeService) {
        this.boatTypeService = boatTypeService;
    }

    /**
     * 添加。
     *
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody BoatType boatType) {
        return boatTypeService.save(boatType);
    }

    /**
     * 根据主键删除。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Integer id) {
        return boatTypeService.removeById(id);
    }

    /**
     * 根据主键更新。
     *
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody BoatType boatType) {
        return boatTypeService.updateById(boatType);
    }

    /**
     * 查询所有。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<BoatType> list() {
        return boatTypeService.list();
    }

    /**
     * 根据主键获取详细信息。
     *
     * @param id 主键
     * @return 详情
     */
    @GetMapping("getInfo/{id}")
    public BoatType getInfo(@PathVariable Integer id) {
        return boatTypeService.getById(id);
    }

    /**
     * 分页查询。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<BoatType> page(Page<BoatType> page) {
        return boatTypeService.page(page);
    }

}
