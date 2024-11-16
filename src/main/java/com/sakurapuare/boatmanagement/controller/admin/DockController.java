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
import com.sakurapuare.boatmanagement.pojo.entity.Dock;
import com.sakurapuare.boatmanagement.service.DockService;

/**
 * 控制层。
 *
 * @author sakurapuare
 * @since 2024-11-15
 */
@RestController
@RequestMapping("/docks")
public class DockController {

    private final DockService dockService;

    public DockController(DockService dockService) {
        this.dockService = dockService;
    }

    /**
     * 添加。
     *
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody Dock dock) {
        return dockService.save(dock);
    }

    /**
     * 根据主键删除。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Integer id) {
        return dockService.removeById(id);
    }

    /**
     * 根据主键更新。
     *
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody Dock dock) {
        return dockService.updateById(dock);
    }

    /**
     * 查询所有。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<Dock> list() {
        return dockService.list();
    }

    /**
     * 根据主键获取详细信息。
     *
     * @param id 主键
     * @return 详情
     */
    @GetMapping("getInfo/{id}")
    public Dock getInfo(@PathVariable Integer id) {
        return dockService.getById(id);
    }

    /**
     * 分页查询。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<Dock> page(Page<Dock> page) {
        return dockService.page(page);
    }

}
