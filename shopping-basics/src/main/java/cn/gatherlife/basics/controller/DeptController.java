package cn.gatherlife.basics.controller;

import cn.gatherlife.basics.model.Dept;
import cn.gatherlife.basics.service.DeptService;
import cn.gatherlife.common.http.HttpResult;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 机构控制器
 * @author Weir
 * @date Oct 29, 2018
 */
@RestController
@RequestMapping("sys/dept")
public class DeptController {

	@Autowired
	private DeptService deptService;

	@RequiresPermissions(value={"sys:dept:add", "sys:dept:edit"},logical= Logical.OR)
	@PostMapping(value="/save")
	public HttpResult save(@RequestBody Dept record) {
		return HttpResult.ok(deptService.insertOrUpdate(record));
	}

	@RequiresPermissions("sys:dept:delete")
	@PostMapping(value="/delete")
	public HttpResult delete(@RequestBody List<Long> records) {
		return HttpResult.ok(deptService.deleteBatchIds(records));
	}

	@GetMapping(value="/findTree")
	public HttpResult findTree() {
		return HttpResult.ok(deptService.findTree());
	}

}
