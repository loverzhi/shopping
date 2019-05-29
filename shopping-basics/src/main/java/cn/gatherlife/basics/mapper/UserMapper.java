package cn.gatherlife.basics.mapper;

import cn.gatherlife.basics.model.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 分页查询用户
     * @param page
     * @param entityWrapper
     * @return
     */
    List<User> selectPage(Pagination page, @Param("ew") EntityWrapper<User> entityWrapper);

}