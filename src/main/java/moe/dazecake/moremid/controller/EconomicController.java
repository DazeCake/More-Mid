package moe.dazecake.moremid.controller;

import io.github.yedaxia.apidocs.ApiDoc;
import moe.dazecake.moremid.annotation.Login;
import moe.dazecake.moremid.entity.BankEntity;
import moe.dazecake.moremid.entity.BookEntity;
import moe.dazecake.moremid.service.Impl.EconomicServiceImpl;
import moe.dazecake.moremid.util.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 经济通用接口
 */
@ApiDoc
@ResponseBody
@RestController
public class EconomicController {

    @Resource
    private EconomicServiceImpl economicService;

    /**
     * 通过UserId查询账户
     *
     * @param userId 用户ID
     * @return moe.dazecake.moremid.util.Result<moe.dazecake.moremid.entity.BankEntity>
     * @author DazeCake
     * @date 2022/1/27 11:45
     */
    @Login
    @GetMapping("/queryByUserId")
    public Result<BankEntity> queryByUserId(Long userId) {
        return economicService.queryByUserId(userId);
    }

    /**
     * 通过UserName查询账户
     *
     * @param userName 用户名
     * @return moe.dazecake.moremid.util.Result<moe.dazecake.moremid.entity.BankEntity>
     * @author DazeCake
     * @date 2022/1/27 11:46
     */
    @Login
    @GetMapping("/queryByUserName")
    public Result<BankEntity> queryByUserName(String userName) {
        return economicService.queryByUserName(userName);
    }

    /**
     * 通过XUID查询账户
     *
     * @param xuid XUID
     * @return moe.dazecake.moremid.util.Result<moe.dazecake.moremid.entity.BankEntity>
     * @author DazeCake
     * @date 2022/1/27 11:46
     */
    @Login
    @GetMapping("/queryByXUID")
    public Result<BankEntity> queryByXUID(String xuid) {
        return economicService.queryByXUID(xuid);
    }

    /**
     * 通过UserId转账
     *
     * @param userId   用户ID
     * @param money    金额
     * @param getterId 收款人ID
     * @return moe.dazecake.moremid.util.Result
     * @author DazeCake
     * @date 2022/1/27 11:46
     */
    @Login
    @PostMapping("/payByUserId")
    public Result payByUserId(Long userId, Double money, Long getterId) {
        return economicService.payByUserId(userId, money, getterId);
    }

    /**
     * 通过UserName转账
     *
     * @param userName   用户名
     * @param money      金额
     * @param getterName 收款人用户名
     * @return moe.dazecake.moremid.util.Result
     * @author DazeCake
     * @date 2022/1/27 11:50
     */
    @Login
    @PostMapping("/payByUserName")
    public Result payByUserName(String userName, Double money, Long getterName) {
        return economicService.payByUserName(userName, money, getterName);
    }

    /**
     * 通过XUID转账
     *
     * @param xuid       XUID
     * @param money      金额
     * @param getterXuid 收款人XUID
     * @return moe.dazecake.moremid.util.Result
     * @author DazeCake
     * @date 2022/1/27 11:50
     */
    @Login
    @PostMapping("/payByXUID")
    public Result payByXUID(String xuid, Double money, Long getterXuid) {
        return economicService.payByXUID(xuid, money, getterXuid);
    }

    /**
     * 通过UserId设置余额
     *
     * @param userId 用户ID
     * @param money  金额
     * @return moe.dazecake.moremid.util.Result
     * @author DazeCake
     * @date 2022/1/27 11:50
     */
    @Login
    @PostMapping("/setByUserId")
    public Result setByUserId(Long userId, Double money) {
        return economicService.setByUserId(userId, money);
    }

    /**
     * 通过UserName设置余额
     *
     * @param userName 用户名
     * @param money    金额
     * @return moe.dazecake.moremid.util.Result
     * @author DazeCake
     * @date 2022/1/27 11:51
     */
    @Login
    @PostMapping("/setByUserName")
    public Result setByUserName(String userName, Double money) {
        return economicService.setByUserName(userName, money);
    }

    /**
     * 通过XUID设置余额
     *
     * @param xuid  XUID
     * @param money 金额
     * @return moe.dazecake.moremid.util.Result
     * @author DazeCake
     * @date 2022/1/27 11:51
     */
    @Login
    @PostMapping("/setByXUID")
    public Result setByXUID(String xuid, Double money) {
        return economicService.setByXUID(xuid, money);
    }

    /**
     * 通过UserId增加余额
     *
     * @param userId 用户ID
     * @param money  金额
     * @param type   类型
     * @param msg    消息摘要
     * @return moe.dazecake.moremid.util.Result
     * @author DazeCake
     * @date 2022/1/27 11:52
     */
    @Login
    @PostMapping("/addByUserId")
    public Result addByUserId(Long userId, Double money, String type, String msg) {
        return economicService.addByUserId(userId, money, type, msg);
    }

    /**
     * 通过UserName增加余额
     *
     * @param userName 用户名
     * @param money    金额
     * @param type     类型
     * @param msg      消息摘要
     * @return moe.dazecake.moremid.util.Result
     * @author DazeCake
     * @date 2022/1/27 11:52
     */
    @Login
    @PostMapping("/addByUserName")
    public Result addByUserName(String userName, Double money, String type, String msg) {
        return economicService.addByUserName(userName, money, type, msg);
    }

    /**
     * 通过XUID增加余额
     *
     * @param xuid  XUID
     * @param money 金额
     * @param type  类型
     * @param msg   消息摘要
     * @return moe.dazecake.moremid.util.Result
     * @author DazeCake
     * @date 2022/1/27 11:53
     */
    @Login
    @PostMapping("/addByXUID")
    public Result addByXUID(String xuid, Double money, String type, String msg) {
        return economicService.addByXUID(xuid, money, type, msg);
    }

    /**
     * 通过UserId减少余额
     *
     * @param userId 用户ID
     * @param money  金额
     * @param type   类型
     * @param msg    消息摘要
     * @return moe.dazecake.moremid.util.Result
     * @author DazeCake
     * @date 2022/1/27 11:53
     */
    @Login
    @PostMapping("/reduceByUserId")
    public Result reduceByUserId(Long userId, Double money, String type, String msg) {
        return economicService.reduceByUserId(userId, money, type, msg);
    }

    /**
     * 通过UserName减少余额
     *
     * @param userName 用户名
     * @param money    金额
     * @param type     类型
     * @param msg      消息摘要
     * @return moe.dazecake.moremid.util.Result
     * @author DazeCake
     * @date 2022/1/27 11:53
     */
    @Login
    @PostMapping("/reduceByUserName")
    public Result reduceByUserName(String userName, Double money, String type, String msg) {
        return economicService.reduceByUserName(userName, money, type, msg);
    }

    /**
     * 通过XUID减少余额
     *
     * @param xuid  XUID
     * @param money 金额
     * @param type  类型
     * @param msg   消息摘要
     * @return moe.dazecake.moremid.util.Result
     * @author DazeCake
     * @date 2022/1/27 11:54
     */
    @Login
    @PostMapping("/reduceByXUID")
    public Result reduceByXUID(String xuid, Double money, String type, String msg) {
        return economicService.reduceByXUID(xuid, money, type, msg);
    }

    /**
     * 通过UserId查询账本
     *
     * @param userId      用户ID
     * @param currentPage 当前页
     * @param size        每页条目数
     * @return moe.dazecake.moremid.util.Result<java.util.List < moe.dazecake.moremid.entity.BookEntity>>
     * @author DazeCake
     * @date 2022/1/27 11:55
     */
    @Login
    @GetMapping("/queryBookByUserId")
    public Result<List<BookEntity>> queryBookByUserId(Long userId, int currentPage, int size) {
        return economicService.queryBookByUserId(userId, currentPage, size);
    }

    /**
     * 通过UserName查询账本
     *
     * @param userName    用户名
     * @param currentPage 当前页
     * @param size        每页条目数
     * @return moe.dazecake.moremid.util.Result<java.util.List < moe.dazecake.moremid.entity.BookEntity>>
     * @author DazeCake
     * @date 2022/1/27 11:55
     */
    @Login
    @GetMapping("/queryBookByUserName")
    public Result<List<BookEntity>> queryBookByUserName(String userName, int currentPage, int size) {
        return economicService.queryBookByUserName(userName, currentPage, size);
    }

    /**
     * 通过XUID查询账本
     *
     * @param xuid        XUID
     * @param currentPage 当前页
     * @param size        每页条目数
     * @return moe.dazecake.moremid.util.Result<java.util.List < moe.dazecake.moremid.entity.BookEntity>>
     * @author DazeCake
     * @date 2022/1/27 11:55
     */
    @Login
    @GetMapping("/queryBookByXUID")
    public Result<List<BookEntity>> queryBookByXUID(String xuid, int currentPage, int size) {
        return economicService.queryBookByXUID(xuid, currentPage, size);
    }

}
