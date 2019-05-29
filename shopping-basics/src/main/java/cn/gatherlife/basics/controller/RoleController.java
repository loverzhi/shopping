package cn.gatherlife.basics.controller;

import cn.gatherlife.basics.constants.SysConstants;
import cn.gatherlife.basics.model.Role;
import cn.gatherlife.basics.model.RoleMenu;
import cn.gatherlife.basics.service.RoleService;
import cn.gatherlife.common.http.HttpResult;
import cn.gatherlife.core.page.PageRequest;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author chy
 * @Description
 */
@RestController
@RequestMapping("sys/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequiresPermissions(value = {"sys:role:add","sys:role.edit"},logical = Logical.OR)
    @PostMapping(value = "/save")
    public HttpResult save(@RequestBody Role record) {
        Role role = roleService.selectById(record.getId());
        if(role != null) {
            if(SysConstants.ADMIN.equalsIgnoreCase(role.getName())) {
                return HttpResult.error("超级管理员不允许修改!");
            }
        }
        // 新增角色
        if((record.getId() == null || record.getId() ==0) && !roleService.findByName(record.getName()).isEmpty()) {
            return HttpResult.error("角色名已存在!");
        }
        return HttpResult.ok(roleService.insert(record));
    }

    @RequiresPermissions("sys:role:delete")
    @PostMapping(value="/delete")
    public HttpResult delete(@RequestBody List<Long> records) {
        return HttpResult.ok(roleService.deleteBatchIds(records));
    }

    @RequiresPermissions("sys:role:view")
    @PostMapping(value="/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        return HttpResult.ok(roleService.selectPage(pageRequest));
    }

    @GetMapping(value="/findAll")
    public HttpResult findAll() {
        return HttpResult.ok(roleService.findAll());
    }

    @GetMapping(value="/findRoleMenus")
    public HttpResult findRoleMenus(@RequestParam Long roleId) {
        return HttpResult.ok(roleService.findRoleMenus(roleId));
    }

    @PostMapping(value="/saveRoleMenus")
    public HttpResult saveRoleMenus(@RequestBody List<RoleMenu> records) {
        for(RoleMenu record:records) {
            Role role = roleService.selectById(record.getRoleId());
            if(SysConstants.ADMIN.equalsIgnoreCase(role.getName())) {
                // 如果是超级管理员，不允许修改
                return HttpResult.error("超级管理员拥有所有菜单权限，不允许修改！");
            }
        }
        return HttpResult.ok(roleService.saveRoleMenus(records));
    }

}
