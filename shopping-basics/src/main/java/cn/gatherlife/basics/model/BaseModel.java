package cn.gatherlife.basics.model;

import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @Author chy
 * @Description 基础模型
 *
 */
@Data
public class BaseModel {

    @TableId
    private Long id;

    private String createBy;

    private Date createTime;

    private String lastUpdateBy;

    private Date lastUpdateTime;

}
