package cn.gatherlife.basics.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author chy
 * @Description
 */
@Data
@TableName("sys_user")
public class User extends BaseModel{

    private String username;

    private String name;

    private String password;

    private String salt;

    private String email;

    private String mobile;

    private Byte status;

    private Long deptId;

    @TableLogic
    private Byte delFlag;

    @TableField(exist = false)
    private String deptName;

    @TableField(exist = false)
    private String roleName;

    @TableField(exist = false)
    private List<UserRole> userRoles = new ArrayList<>();


}
