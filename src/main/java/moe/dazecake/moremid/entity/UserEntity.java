package moe.dazecake.moremid.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户表实体类
 *
 * @author DazeCake
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("user")
public class UserEntity {
    @TableId
    Long id;

    String userName;//用户名

    String xuid;//XUID

    String email;//邮箱

    String password;//密码

    Long permissions;//权限

    Boolean delete;//账号状态

    String lastLoginIp;//最后登陆IP

    LocalDateTime lastLoginTime;//上次登陆时间

    LocalDateTime createTime;//创建时间
}
