package cn.gatherlife.basics.service.impl;

import cn.gatherlife.basics.mapper.LogMapper;
import cn.gatherlife.basics.model.Log;
import cn.gatherlife.basics.service.LogService;
import cn.gatherlife.core.page.PageRequest;
import cn.gatherlife.core.utils.Constant;
import cn.gatherlife.core.utils.Query;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

	/*@Override
	public int insert(Log record) {
		if(record.getId() == null || record.getId() == 0) {
			return this.baseMapper.insert(record);
		}
		return this.baseMapper.update(record,
				new EntityWrapper<Log>().eq("id", record.getId()));
	}

	@Override
	public int delete(Log record) {
		return this.baseMapper.deleteById(record.getId());
	}

	@Override
	public int delete(List<Log> records) {
		for(Log record:records) {
			delete(record);
		}
		return 1;
	}

	@Override
	public Log selectById(Long id) {
		return this.baseMapper.selectById(id);
	}
*/

	@Override
	public Page selectPage(PageRequest pageRequest) {
		Page<Log> page = this.selectPage(
				new Query<Log>(pageRequest).getPage(),
				new EntityWrapper<Log>().orderBy("id", false)
						.addFilterIfNeed(pageRequest.getColumnFilters().get(Constant.SQL_FILTER) != null,
								pageRequest.getColumnFilters().get(Constant.SQL_FILTER))
		);
		return page;
	}

}
