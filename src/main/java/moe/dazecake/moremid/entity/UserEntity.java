package moe.dazecake.moremid.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户表实体类
 *
 * @author DazeCake
 */
@Data
@TableName("user")
public class UserEntity {
    @TableId
    Long id;

    //用户名
    String userName;

    //XUID
    String xuid;

    //邮箱
    String email;

    //密码
    String password;

    //权限
    Long permissions;

    //账号状态
    Boolean delete;

    //最后登陆IP
    String lastLoginIp;

    //上次登陆时间
    LocalDateTime lastLoginTime;

    //创建时间
    LocalDateTime createTime;
}
