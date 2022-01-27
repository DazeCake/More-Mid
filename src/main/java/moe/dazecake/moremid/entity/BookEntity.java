package moe.dazecake.moremid.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("book")
public class BookEntity {
    @TableId
    Long id;//ID

    Long userId;//用户ID

    Double income;//收入

    Double spend;//支出

    Double balance;//余额

    String type;//种类

    String msg;//消息摘要
}
