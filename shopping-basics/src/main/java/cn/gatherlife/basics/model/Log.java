package cn.gatherlife.basics.model;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * @Author chy
 * @Description
 */
@Data
@TableName("sys_log")
public class Log extends BaseModel{

    private String username;

    private String operation;

    private String method;

    private String params;

    private Long time;

    private String ip;
}
