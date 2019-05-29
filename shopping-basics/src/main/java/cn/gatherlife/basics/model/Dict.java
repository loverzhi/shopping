package cn.gatherlife.basics.model;

import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * @Author chy
 * @Description
 */
@Data
@TableName("sys_dict")
public class Dict extends BaseModel {

    private String value;

    private String label;

    private String type;

    private String description;

    private Long sort;

    private String remark;

    @TableLogic
    private Byte delFlag;
}
