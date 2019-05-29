package cn.gatherlife.basics.controller;

import cn.gatherlife.basics.constants.SysConstants;
import cn.gatherlife.basics.model.User;
import cn.gatherlife.basics.service.UserService;
import cn.gatherlife.basics.utils.PasswordUtils;
import cn.gatherlife.basics.utils.ShiroUtils;
import cn.gatherlife.common.http.HttpResult;
import cn.gatherlife.core.page.PageRequest;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author chy
 * @Description  用户控制器
 */
@RestController
@RequestMapping("sys/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequiresPermissions(value = {"sys:user:add","sys:user:edit"},logical = Logical.OR)
    @PostMapping(value = "/save")
    public HttpResult save(@RequestBody User record){
        User user = null;
        if(record.getId() != 0){
            user = userService.selectById(record.getId());
        }
        if(null != user){
            if(SysConstants.ADMIN.equalsIgnoreCase(user.getUsername())){
                return HttpResult.error("超级管理员不允许修改!");
            }
        }
        if(record.getPassword() != null){
            String salt = PasswordUtils.getSalt();
            if(user == null){
                // 新增用户
                if(userService.findByUsername(record.getUsername()) != null) {
                    return HttpResult.error("用户名已存在!");
                }
                String password = PasswordUtils.encrypte(record.getPassword(),salt);
                record.setSalt(salt);
                record.setPassword(password);
            } else {
                // 修改用户, 且修改了密码
                if(!record.getPassword().equals(user.getPassword())) {
                    String password = PasswordUtils.encrypte(record.getPassword(), salt);
                    record.setSalt(salt);
                    record.setPassword(password);
                }
            }
        }
        return HttpResult.ok(userService.insertOrUpdate(record));
    }

    @RequiresPermissions({"sys:user:delete"})
    @PostMapping(value="/delete")
    public HttpResult delete(@RequestBody List<Long> records) {
        for(Long id: records) {
            User user = userService.selectById(id);
            if(user != null && SysConstants.ADMIN.equalsIgnoreCase(user.getUsername())) {
                return HttpResult.error("超级管理员不允许删除!");
            }
        }
        return HttpResult.ok(userService.deleteBatchIds(records));
    }

    @GetMapping(value="/findByUsername")
    public HttpResult findByUserName(@RequestParam String username) {
        return HttpResult.ok(userService.findByUsername(username));
    }

    @GetMapping(value="/findPermissions")
    public HttpResult findPermissions(@RequestParam String name) {
        return HttpResult.ok(userService.findPermissions(name));
    }

    @GetMapping(value="/findUserRoles")
    public HttpResult findUserRoles(@RequestParam Long userId) {
        return HttpResult.ok(userService.findUserRoles(userId));
    }

    @RequiresPermissions("sys:user:view")
    @PostMapping(value="/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        return HttpResult.ok(userService.selectPage(pageRequest));
    }

    /**
     * 修改登录用户密码
     */
    @GetMapping("/updatePassword")
    public HttpResult updatePassword(@RequestParam String password, @RequestParam String newPassword) {
        User user = ShiroUtils.getUser();
        if(user != null && password != null && newPassword != null){
            String oldPassword = PasswordUtils.encrypte(password, user.getSalt());
            if(!oldPassword.equals(user.getPassword())){
                return HttpResult.error("原密码不正确");
            }
            user.setPassword(PasswordUtils.encrypte(newPassword,user.getSalt()));
            return HttpResult.ok(userService.insertOrUpdate(user));
        }
        return HttpResult.error();
    }
}
