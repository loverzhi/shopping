package cn.gatherlife.basics.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.List;

/**
 * @Author chy
 * @Description
 */
@Data
@TableName("sys_dept")
public class Dept extends BaseModel{

    private String name;

    private Long parentId;

    private Integer orderNum;

    @TableLogic
    private Byte delFlag;

    @TableField(exist = false)
    private List<Dept> children;

    //非数据库字段
    @TableField(exist = false)
    private String parentName;

    //非数据库字段
    @TableField(exist = false)
    private Integer level;
}
