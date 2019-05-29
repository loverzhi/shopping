package cn.gatherlife.basics.model;

import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * @Author chy
 * @Description
 */
@Data
@TableName("sys_role")
public class Role extends BaseModel {

    private String name;

    private String remark;

    @TableLogic
    private Byte delFlag;
}
