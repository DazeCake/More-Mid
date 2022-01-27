package moe.dazecake.moremid.service;

import moe.dazecake.moremid.entity.BankEntity;
import moe.dazecake.moremid.entity.BookEntity;
import moe.dazecake.moremid.util.Result;

import java.util.List;

public interface EconomicService {

    //查询
    Result<BankEntity> queryByUserId(Long userId);

    Result<BankEntity> queryByUserName(String userName);

    Result<BankEntity> queryByXUID(String xuid);

    //转账
    Result payByUserId(Long userId, Double money, Long getterId);

    Result payByUserName(String userName, Double money, Long getterName);

    Result payByXUID(String xuid, Double money, Long getterXuid);

    //余额设置
    Result setByUserId(Long userId, Double money);

    Result setByUserName(String userName, Double money);

    Result setByXUID(String xuid, Double money);

    //余额增加
    Result addByUserId(Long userId, Double money, String type, String msg);

    Result addByUserName(String userName, Double money, String type, String msg);

    Result addByXUID(String xuid, Double money, String type, String msg);

    //余额减少
    Result reduceByUserId(Long userId, Double money, String type, String msg);

    Result reduceByUserName(String userName, Double money, String type, String msg);

    Result reduceByXUID(String xuid, Double money, String type, String msg);

    //账本查询
    Result<List<BookEntity>> queryBookByUserId(Long userId,int currentPage,int size);

    Result<List<BookEntity>> queryBookByUserName(String userName,int currentPage,int size);

    Result<List<BookEntity>> queryBookByXUID(String xuid,int currentPage,int size);
}
