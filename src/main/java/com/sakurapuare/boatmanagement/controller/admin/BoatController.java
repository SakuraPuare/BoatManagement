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
import com.sakurapuare.boatmanagement.pojo.entity.Boat;
import com.sakurapuare.boatmanagement.service.BoatService;

/**
 * 控制层。
 *
 * @author sakurapuare
 * @since 2024-11-15
 */
@RestController
@RequestMapping("/boats")
public class BoatController {

    private final BoatService boatService;

    public BoatController(BoatService boatService) {
        this.boatService = boatService;
    }

    /**
     * 添加。
     *
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody Boat boat) {
        return boatService.save(boat);
    }

    /**
     * 根据主键删除。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Integer id) {
        return boatService.removeById(id);
    }

    /**
     * 根据主键更新。
     *
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody Boat boat) {
        return boatService.updateById(boat);
    }

    /**
     * 查询所有。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<Boat> list() {
        return boatService.list();
    }

    /**
     * 根据主键获取详细信息。
     *
     * @param id 主键
     * @return 详情
     */
    @GetMapping("getInfo/{id}")
    public Boat getInfo(@PathVariable Integer id) {
        return boatService.getById(id);
    }

    /**
     * 分页查询。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<Boat> page(Page<Boat> page) {
        return boatService.page(page);
    }

}
