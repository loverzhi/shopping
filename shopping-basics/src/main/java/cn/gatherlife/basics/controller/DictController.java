package cn.gatherlife.basics.controller;

import cn.gatherlife.basics.model.Dict;
import cn.gatherlife.basics.service.DictService;
import cn.gatherlife.common.http.HttpResult;
import cn.gatherlife.core.page.PageRequest;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author chy
 * @Description 字典控制器
 */
@RestController
@RequestMapping("sys/dict")
public class DictController {

    @Autowired
    private DictService dictService;

    @RequiresPermissions(value={"sys:dict:add", "sys:dict:edit"},logical= Logical.OR)
    @PostMapping(value="/save")
    public HttpResult save(@RequestBody Dict record) {
        return HttpResult.ok(dictService.insertOrUpdate(record));
    }

    @RequiresPermissions("sys:dict:delete")
    @PostMapping(value="/delete")
    public HttpResult delete(@RequestBody List<Long> records) {
        return HttpResult.ok(dictService.deleteBatchIds(records));
    }

    @RequiresPermissions("sys:dict:view")
    @PostMapping(value="/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        return HttpResult.ok(dictService.selectPage(pageRequest));
    }

    @GetMapping(value="/findByLable")
    public HttpResult findByLable(@RequestParam String lable) {
        return HttpResult.ok(dictService.findByLable(lable));
    }
}
