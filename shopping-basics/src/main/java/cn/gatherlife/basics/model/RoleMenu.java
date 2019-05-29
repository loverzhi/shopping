package cn.gatherlife.basics.model;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * @Author chy
 * @Description
 */
@Data
@TableName("sys_role_menu")
public class RoleMenu extends BaseModel{
    private Long roleId;

    private Long menuId;
}
