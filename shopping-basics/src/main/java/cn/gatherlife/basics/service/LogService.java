package cn.gatherlife.basics.service;

import cn.gatherlife.basics.model.Log;
import cn.gatherlife.core.page.PageRequest;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * 日志管理
 * @author Weir
 * @date Oct 29, 2018
 */
public interface LogService extends IService<Log> {

    /**
     *分页查询
     * @param pageRequest
     * @return
     */
    Page<Log> selectPage(PageRequest pageRequest);

}
