package cn.gatherlife.basics.controller;

import cn.gatherlife.basics.model.Menu;
import cn.gatherlife.basics.service.MenuService;
import cn.gatherlife.common.http.HttpResult;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author chy
 * @Description 菜单控制器
 */
@RestController
@RequestMapping("sys/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @RequiresPermissions(value={"sys:menu:add", "sys:menu:edit"},logical= Logical.OR)
    @PostMapping(value="/save")
    public HttpResult save(@RequestBody Menu record) {
        return HttpResult.ok(menuService.insertOrUpdate(record));
    }

    @RequiresPermissions("sys:menu:delete")
    @PostMapping(value="/delete")
    public HttpResult delete(@RequestBody List<Long> records) {
        return HttpResult.ok(menuService.deleteBatchIds(records));
    }

    @GetMapping(value="/findNavTree")
    public HttpResult findNavTree(@RequestParam String userName) {
        return HttpResult.ok(menuService.findTree(userName, 1));
    }

    @RequiresPermissions("sys:menu:view")
    @GetMapping(value="/findMenuTree")
    public HttpResult findMenuTree() {
        return HttpResult.ok(menuService.findTree(null, 0));
    }
}
