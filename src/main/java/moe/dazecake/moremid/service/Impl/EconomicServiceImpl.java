package moe.dazecake.moremid.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import moe.dazecake.moremid.entity.BankEntity;
import moe.dazecake.moremid.entity.BookEntity;
import moe.dazecake.moremid.mapper.EconomicBankMapper;
import moe.dazecake.moremid.mapper.EconomicBookMapper;
import moe.dazecake.moremid.service.EconomicService;
import moe.dazecake.moremid.util.Result;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EconomicServiceImpl implements EconomicService {

    @Resource
    EconomicBankMapper bankMapper;

    @Resource
    EconomicBookMapper bookMapper;

    @Override
    public Result<BankEntity> queryByUserId(Long userId) {
        BankEntity result = bankMapper.selectOne(
                Wrappers.<BankEntity>lambdaQuery()
                        .eq(BankEntity::getUserId, userId)
        );

        if (ObjectUtils.isEmpty(result)) {
            return Result.failed(404, "未能找到对应用户");
        } else {
            return Result.success(result, "查询成功");
        }
    }

    @Override
    public Result<BankEntity> queryByUserName(String userName) {
        BankEntity result = bankMapper.selectOne(
                Wrappers.<BankEntity>lambdaQuery()
                        .eq(BankEntity::getUserName, userName)
        );

        if (ObjectUtils.isEmpty(result)) {
            return Result.failed(404, "未能找到对应用户");
        } else {
            return Result.success(result, "查询成功");
        }
    }

    @Override
    public Result<BankEntity> queryByXUID(String xuid) {
        BankEntity result = bankMapper.selectOne(
                Wrappers.<BankEntity>lambdaQuery()
                        .eq(BankEntity::getXuid, xuid)
        );

        if (ObjectUtils.isEmpty(result)) {
            return Result.failed(404, "未能找到对应用户");
        } else {
            return Result.success(result, "查询成功");
        }
    }

    @Override
    public Result payByUserId(Long userId, Double money, Long getterId) {
        //负数金额判断
        if (money < 0) {
            return Result.failed(403, "禁止负数转账");
        }

        //余额判断
        BankEntity userBank = bankMapper.selectOne(
                Wrappers.<BankEntity>lambdaQuery()
                        .eq(BankEntity::getUserId, userId)
        );
        if (userBank.getBalance() < money) {
            return Result.failed(403, "余额不足");
        }

        //查询转账对象是否存在
        BankEntity getterBank = bankMapper.selectOne(
                Wrappers.<BankEntity>lambdaQuery()
                        .eq(BankEntity::getUserId, getterId)
        );
        if (ObjectUtils.isEmpty(getterBank)) {
            return Result.failed(404, "找不到转账对象");
        }

        //余额调整
        userBank.setBalance(userBank.getBalance() - money);
        getterBank.setBalance(getterBank.getBalance() + money);
        int userStatus = 0;
        int getterStatue = 0;
        userStatus = bankMapper.update(userBank,
                Wrappers.<BankEntity>lambdaQuery()
                        .eq(BankEntity::getUserId, userId)
        );
        if (userStatus != 1) {
            return Result.failed("支出更新写入失败,请检查配置");
        } else {
            getterStatue = bankMapper.update(getterBank,
                    Wrappers.<BankEntity>lambdaQuery()
                            .eq(BankEntity::getUserId, getterId)
            );
        }
        if (getterStatue != 1) {
            return Result.failed("收入更新写入失败,请检查配置");
        }

        //账本更新
        bookMapper.insert(BookEntity.builder()
                .userId(userId)
                .spend(money)
                .balance(userBank.getBalance())
                .type("transfer")
                .msg("转账给 " + getterBank.getUserName() + " " + money)
                .build()
        );
        bookMapper.insert(BookEntity.builder()
                .userId(getterId)
                .income(money)
                .balance(getterBank.getBalance())
                .type("collection")
                .msg("收款自 " + getterBank.getUserName() + " " + money)
                .build()
        );

        return Result.success("转账成功");
    }

    @Override
    public Result payByUserName(String userName, Double money, Long getterName) {
        //负数金额判断
        if (money < 0) {
            return Result.failed(403, "禁止负数转账");
        }

        //余额判断
        BankEntity userBank = bankMapper.selectOne(
                Wrappers.<BankEntity>lambdaQuery()
                        .eq(BankEntity::getUserName, userName)
        );
        if (userBank.getBalance() < money) {
            return Result.failed(403, "余额不足");
        }

        //查询转账对象是否存在
        BankEntity getterBank = bankMapper.selectOne(
                Wrappers.<BankEntity>lambdaQuery()
                        .eq(BankEntity::getUserName, getterName)
        );
        if (ObjectUtils.isEmpty(getterBank)) {
            return Result.failed(404, "找不到转账对象");
        }

        //余额调整
        userBank.setBalance(userBank.getBalance() - money);
        getterBank.setBalance(getterBank.getBalance() + money);
        int userStatus = 0;
        int getterStatue = 0;
        userStatus = bankMapper.update(userBank,
                Wrappers.<BankEntity>lambdaQuery()
                        .eq(BankEntity::getUserName, userName)
        );
        if (userStatus != 1) {
            return Result.failed("支出更新写入失败,请检查配置");
        } else {
            getterStatue = bankMapper.update(getterBank,
                    Wrappers.<BankEntity>lambdaQuery()
                            .eq(BankEntity::getUserName, getterName)
            );
        }
        if (getterStatue != 1) {
            return Result.failed("收入更新写入失败,请检查配置");
        }

        //账本更新
        bookMapper.insert(BookEntity.builder()
                .userId(userBank.getUserId())
                .spend(money)
                .balance(userBank.getBalance())
                .type("transfer")
                .msg("转账给 " + getterBank.getUserName() + " " + money)
                .build()
        );
        bookMapper.insert(BookEntity.builder()
                .userId(getterBank.getUserId())
                .income(money)
                .balance(getterBank.getBalance())
                .type("collection")
                .msg("收款自 " + getterBank.getUserName() + " " + money)
                .build()
        );

        return Result.success("转账成功");
    }

    @Override
    public Result payByXUID(String xuid, Double money, Long getterXuid) {
        //负数金额判断
        if (money < 0) {
            return Result.failed(403, "禁止负数转账");
        }

        //余额判断
        BankEntity userBank = bankMapper.selectOne(
                Wrappers.<BankEntity>lambdaQuery()
                        .eq(BankEntity::getXuid, xuid)
        );
        if (userBank.getBalance() < money) {
            return Result.failed(403, "余额不足");
        }

        //查询转账对象是否存在
        BankEntity getterBank = bankMapper.selectOne(
                Wrappers.<BankEntity>lambdaQuery()
                        .eq(BankEntity::getXuid, getterXuid)
        );
        if (ObjectUtils.isEmpty(getterBank)) {
            return Result.failed(404, "找不到转账对象");
        }

        //余额调整
        userBank.setBalance(userBank.getBalance() - money);
        getterBank.setBalance(getterBank.getBalance() + money);
        int userStatus = 0;
        int getterStatue = 0;
        userStatus = bankMapper.update(userBank,
                Wrappers.<BankEntity>lambdaQuery()
                        .eq(BankEntity::getXuid, xuid)
        );
        if (userStatus != 1) {
            return Result.failed("支出更新写入失败,请检查配置");
        } else {
            getterStatue = bankMapper.update(getterBank,
                    Wrappers.<BankEntity>lambdaQuery()
                            .eq(BankEntity::getXuid, getterXuid)
            );
        }
        if (getterStatue != 1) {
            return Result.failed("收入更新写入失败,请检查配置");
        }

        //账本更新
        bookMapper.insert(BookEntity.builder()
                .userId(userBank.getUserId())
                .spend(money)
                .balance(userBank.getBalance())
                .type("transfer")
                .msg("转账给 " + getterBank.getUserName() + " " + money)
                .build()
        );
        bookMapper.insert(BookEntity.builder()
                .userId(getterBank.getUserId())
                .income(money)
                .balance(getterBank.getBalance())
                .type("collection")
                .msg("收款自 " + getterBank.getUserName() + " " + money)
                .build()
        );

        return Result.success("转账成功");
    }

    @Override
    public Result setByUserId(Long userId, Double money) {

//        尚未定夺是否允许管理员将用户余额设为负债状态
//        if (money < 0) {
//            return Result.failed("403", "不允许设置为一个负数金额");
//        }

        //获取原有userBank记录
        BankEntity userBank = bankMapper.selectOne(
                Wrappers.<BankEntity>lambdaQuery()
                        .eq(BankEntity::getUserId, userId)
        );

        //更新记录
        int status = bankMapper.update(BankEntity.builder()
                        .userId(userId)
                        .balance(money)
                        .build(),
                Wrappers.<BankEntity>lambdaQuery()
                        .eq(BankEntity::getUserId, userId)
        );

        //写入账本
        if (status == 1) {
            if (money >= userBank.getBalance()) {
                bookMapper.insert(BookEntity.builder()
                        .userId(userId)
                        .income(money - userBank.getBalance())
                        .balance(money)
                        .type("admin_set")
                        .msg("由管理员强制设置余额为 " + money)
                        .build()
                );
            } else {
                bookMapper.insert(BookEntity.builder()
                        .userId(userId)
                        .spend(userBank.getBalance() - money)
                        .balance(money)
                        .type("admin_set")
                        .msg("由管理员强制设置余额为 " + money)
                        .build()
                );
            }

            return Result.success("设置余额成功");
        } else {
            return Result.failed("未知错误,请截图报错信息上报issue");
        }
    }

    @Override
    public Result setByUserName(String userName, Double money) {
        //获取原有userBank记录
        BankEntity userBank = bankMapper.selectOne(
                Wrappers.<BankEntity>lambdaQuery()
                        .eq(BankEntity::getUserName, userName)
        );

        //更新记录
        int status = bankMapper.update(BankEntity.builder()
                        .userName(userName)
                        .balance(money)
                        .build(),
                Wrappers.<BankEntity>lambdaQuery()
                        .eq(BankEntity::getUserId, userName)
        );

        //写入账本
        if (status == 1) {
            if (money >= userBank.getBalance()) {
                bookMapper.insert(BookEntity.builder()
                        .userId(userBank.getUserId())
                        .income(money - userBank.getBalance())
                        .balance(money)
                        .type("admin_set")
                        .msg("由管理员强制设置余额为 " + money)
                        .build()
                );
            } else {
                bookMapper.insert(BookEntity.builder()
                        .userId(userBank.getUserId())
                        .spend(userBank.getBalance() - money)
                        .balance(money)
                        .type("admin_set")
                        .msg("由管理员强制设置余额为 " + money)
                        .build()
                );
            }

            return Result.success("设置余额成功");
        } else {
            return Result.failed("未知错误,请截图报错信息上报issue");
        }
    }

    @Override
    public Result setByXUID(String xuid, Double money) {
        //获取原有userBank记录
        BankEntity userBank = bankMapper.selectOne(
                Wrappers.<BankEntity>lambdaQuery()
                        .eq(BankEntity::getXuid, xuid)
        );

        //更新记录
        int status = bankMapper.update(BankEntity.builder()
                        .xuid(xuid)
                        .balance(money)
                        .build(),
                Wrappers.<BankEntity>lambdaQuery()
                        .eq(BankEntity::getXuid, xuid)
        );

        //写入账本
        if (status == 1) {
            if (money >= userBank.getBalance()) {
                bookMapper.insert(BookEntity.builder()
                        .userId(userBank.getUserId())
                        .income(money - userBank.getBalance())
                        .balance(money)
                        .type("admin_set")
                        .msg("由管理员强制设置余额为 " + money)
                        .build()
                );
            } else {
                bookMapper.insert(BookEntity.builder()
                        .userId(userBank.getUserId())
                        .spend(userBank.getBalance() - money)
                        .balance(money)
                        .type("admin_set")
                        .msg("由管理员强制设置余额为 " + money)
                        .build()
                );
            }

            return Result.success("设置余额成功");
        } else {
            return Result.failed("未知错误,请截图报错信息上报issue");
        }
    }

    @Override
    public Result addByUserId(Long userId, Double money, String type, String msg) {
        //负数金额判断
        if (money < 0) {
            return Result.failed("不允许增加负数金额");
        }

        BankEntity userBank = bankMapper.selectOne(
                Wrappers.<BankEntity>lambdaQuery()
                        .eq(BankEntity::getUserId, userId)
        );

        userBank.setBalance(userBank.getBalance() + money);

        int status = bankMapper.update(userBank,
                Wrappers.<BankEntity>lambdaQuery()
                        .eq(BankEntity::getUserId, userId)
        );

        if (status == 1) {
            bookMapper.insert(BookEntity.builder()
                    .userId(userId)
                    .income(money)
                    .balance(userBank.getBalance())
                    .type(type)
                    .msg(msg)
                    .build()
            );
            return Result.success("增加余额成功");
        } else {
            return Result.failed(500, "出现未知错误,请截图上报issue");
        }
    }

    @Override
    public Result addByUserName(String userName, Double money, String type, String msg) {
        if (money < 0) {
            return Result.failed("不允许增加负数金额");
        }

        BankEntity userBank = bankMapper.selectOne(
                Wrappers.<BankEntity>lambdaQuery()
                        .eq(BankEntity::getUserName, userName)
        );

        userBank.setBalance(userBank.getBalance() + money);

        int status = bankMapper.update(userBank,
                Wrappers.<BankEntity>lambdaQuery()
                        .eq(BankEntity::getUserName, userName)
        );

        if (status == 1) {
            bookMapper.insert(BookEntity.builder()
                    .userId(userBank.getUserId())
                    .income(money)
                    .balance(userBank.getBalance())
                    .type(type)
                    .msg(msg)
                    .build()
            );
            return Result.success("增加余额成功");
        } else {
            return Result.failed(500, "出现未知错误,请截图上报issue");
        }
    }

    @Override
    public Result addByXUID(String xuid, Double money, String type, String msg) {
        if (money < 0) {
            return Result.failed("不允许增加负数金额");
        }

        BankEntity userBank = bankMapper.selectOne(
                Wrappers.<BankEntity>lambdaQuery()
                        .eq(BankEntity::getXuid, xuid)
        );

        userBank.setBalance(userBank.getBalance() + money);

        int status = bankMapper.update(userBank,
                Wrappers.<BankEntity>lambdaQuery()
                        .eq(BankEntity::getXuid, xuid)
        );

        if (status == 1) {
            bookMapper.insert(BookEntity.builder()
                    .userId(userBank.getUserId())
                    .income(money)
                    .balance(userBank.getBalance())
                    .type(type)
                    .msg(msg)
                    .build()
            );
            return Result.success("增加余额成功");
        } else {
            return Result.failed(500, "出现未知错误,请截图上报issue");
        }
    }

    @Override
    public Result reduceByUserId(Long userId, Double money, String type, String msg) {
        if (money < 0) {
            return Result.failed("不允许减少负数金额");
        }

        BankEntity userBank = bankMapper.selectOne(
                Wrappers.<BankEntity>lambdaQuery()
                        .eq(BankEntity::getUserId, userId)
        );

        userBank.setBalance(userBank.getBalance() - money);

        int status = bankMapper.update(userBank,
                Wrappers.<BankEntity>lambdaQuery()
                        .eq(BankEntity::getUserId, userId)
        );

        if (status == 1) {
            bookMapper.insert(BookEntity.builder()
                    .userId(userId)
                    .spend(money)
                    .balance(userBank.getBalance())
                    .type(type)
                    .msg(msg)
                    .build()
            );
            return Result.success("减少余额成功");
        } else {
            return Result.failed(500, "出现未知错误,请截图上报issue");
        }
    }

    @Override
    public Result reduceByUserName(String userName, Double money, String type, String msg) {
        if (money < 0) {
            return Result.failed("不允许减少负数金额");
        }

        BankEntity userBank = bankMapper.selectOne(
                Wrappers.<BankEntity>lambdaQuery()
                        .eq(BankEntity::getUserName, userName)
        );

        userBank.setBalance(userBank.getBalance() - money);

        int status = bankMapper.update(userBank,
                Wrappers.<BankEntity>lambdaQuery()
                        .eq(BankEntity::getUserName, userName)
        );

        if (status == 1) {
            bookMapper.insert(BookEntity.builder()
                    .userId(userBank.getUserId())
                    .spend(money)
                    .balance(userBank.getBalance())
                    .type(type)
                    .msg(msg)
                    .build()
            );
            return Result.success("减少余额成功");
        } else {
            return Result.failed(500, "出现未知错误,请截图上报issue");
        }
    }

    @Override
    public Result reduceByXUID(String xuid, Double money, String type, String msg) {
        if (money < 0) {
            return Result.failed("不允许减少负数金额");
        }

        BankEntity userBank = bankMapper.selectOne(
                Wrappers.<BankEntity>lambdaQuery()
                        .eq(BankEntity::getXuid, xuid)
        );

        userBank.setBalance(userBank.getBalance() - money);

        int status = bankMapper.update(userBank,
                Wrappers.<BankEntity>lambdaQuery()
                        .eq(BankEntity::getXuid, xuid)
        );

        if (status == 1) {
            bookMapper.insert(BookEntity.builder()
                    .userId(userBank.getUserId())
                    .spend(money)
                    .balance(userBank.getBalance())
                    .type(type)
                    .msg(msg)
                    .build()
            );
            return Result.success("减少余额成功");
        } else {
            return Result.failed(500, "出现未知错误,请截图上报issue");
        }
    }

    @Override
    public Result<List<BookEntity>> queryBookByUserId(Long userId, int currentPage, int size) {
        IPage<BookEntity> page = new Page<>(currentPage, size);
        List<BookEntity> list = bookMapper.selectPage(page,
                Wrappers.<BookEntity>lambdaQuery().
                        eq(BookEntity::getUserId, userId)
        ).getRecords();
        return Result.success(list, "查询成功");
    }

    @Override
    public Result<List<BookEntity>> queryBookByUserName(String userName, int currentPage, int size) {
        BankEntity userBank = bankMapper.selectOne(
                Wrappers.<BankEntity>lambdaQuery()
                        .eq(BankEntity::getUserName, userName)
        );

        if (ObjectUtils.isEmpty(userBank)) {
            return Result.failed(404, "未能找到该用户");
        }

        IPage<BookEntity> page = new Page<>(currentPage, size);
        List<BookEntity> list = bookMapper.selectPage(page,
                Wrappers.<BookEntity>lambdaQuery().
                        eq(BookEntity::getUserId, userBank.getUserId())
        ).getRecords();

        return Result.success(list, "查询成功");
    }

    @Override
    public Result<List<BookEntity>> queryBookByXUID(String xuid, int currentPage, int size) {
        BankEntity userBank = bankMapper.selectOne(
                Wrappers.<BankEntity>lambdaQuery()
                        .eq(BankEntity::getXuid, xuid)
        );

        if (ObjectUtils.isEmpty(userBank)) {
            return Result.failed(404, "未能找到该用户");
        }

        IPage<BookEntity> page = new Page<>(currentPage, size);
        List<BookEntity> list = bookMapper.selectPage(page,
                Wrappers.<BookEntity>lambdaQuery().
                        eq(BookEntity::getUserId, userBank.getUserId())
        ).getRecords();

        return Result.success(list, "查询成功");
    }
}
