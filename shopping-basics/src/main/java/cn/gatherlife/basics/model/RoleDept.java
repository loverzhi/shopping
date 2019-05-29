package cn.gatherlife.basics.model;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * @Author chy
 * @Description
 */
@Data
@TableName("sys_role_dept")
public class RoleDept {
    private Long roleId;

    private Long deptId;
}
