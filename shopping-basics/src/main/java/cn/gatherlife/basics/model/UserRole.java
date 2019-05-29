package cn.gatherlife.basics.model;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * @Author chy
 * @Description
 */
@Data
@TableName("sys_user_role")
public class UserRole extends BaseModel{

    private Long userId;

    private Long roleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
