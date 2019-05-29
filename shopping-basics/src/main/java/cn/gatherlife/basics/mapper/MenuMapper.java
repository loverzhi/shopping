package cn.gatherlife.basics.mapper;

import cn.gatherlife.basics.model.Menu;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

	List<Menu> selectByUserName(@Param(value = "userName") String userName);

	List<Menu> selectRoleMenus(@Param(value = "roleId") Long roleId);
}