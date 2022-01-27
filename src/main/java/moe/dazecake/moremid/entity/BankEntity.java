package moe.dazecake.moremid.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("bank")
public class BankEntity {
    @TableId
    Long id;//ID

    Long userId;//用户ID

    String userName;//用户名

    String xuid;//XUID

    Double balance;//余额

    LocalDateTime createTime;//创建时间

    LocalDateTime updateTime;//上次更新时间
}
