package moe.dazecake.moremid.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("book")
public class BookEntity {
    @TableId
    Long id;

    //用户ID
    Long userId;

    //收入
    Double income;

    //支出
    Double spend;

    //余额
    Double balance;

    //种类
    String type;

    //消息摘要
    String msg;
}
