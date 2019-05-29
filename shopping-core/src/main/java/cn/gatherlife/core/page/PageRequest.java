package cn.gatherlife.core.page;

import java.util.HashMap;
import java.util.Map;

/**
 * 分页请求
 * @author Weir
 * @date Aug 19, 2018
 */
public class PageRequest {
	/**
	 * 当前页码
	 */
	private Integer pageNum = 1;
	/**
	 * 每页数量
	 */
	private Integer pageSize = 10;
	/**
	 * 每页数量
	 */
	private Map<String, String> columnFilters = new HashMap<String, String>();
	
	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Map<String, String> getColumnFilters() {
		return columnFilters;
	}

	public void setColumnFilters(Map<String, String> columnFilters) {
		this.columnFilters = columnFilters;
	}

	public ColumnFilter getColumnFilter(String name) {
		return new ColumnFilter(name,  columnFilters.get(name));
	}
}
