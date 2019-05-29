package cn.gatherlife.basics.controller;

import cn.gatherlife.basics.service.LogService;
import cn.gatherlife.common.http.HttpResult;
import cn.gatherlife.core.page.PageRequest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author chy
 * @Description 日志控制器
 */
@RestController
@RequestMapping("sys/log")
public class LogController {

    @Autowired
    private LogService logService;

    @RequiresPermissions("sys:log:view")
    @PostMapping(value="/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        return HttpResult.ok(logService.selectPage(pageRequest));
    }
}
