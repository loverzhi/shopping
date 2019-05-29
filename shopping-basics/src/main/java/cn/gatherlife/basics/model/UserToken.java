package cn.gatherlife.basics.model;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author chy
 * @Description
 */
@Data
@TableName("sys_user_token")
public class UserToken extends BaseModel {

    private Long userId;

    private String token;

    private Date expireTime;
}
