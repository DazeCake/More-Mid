package moe.dazecake.moremid.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("bank")
public class BankEntity {
    @TableId
    Long id;

    //用户ID
    Long userId;

    //用户名
    String userName;

    //XUID
    String xuid;

    //余额
    Double balance;

    //创建时间
    LocalDateTime createTime;

    //上次更新时间
    LocalDateTime updateTime;
}
