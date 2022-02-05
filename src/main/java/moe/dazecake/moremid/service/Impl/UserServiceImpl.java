package moe.dazecake.moremid.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import moe.dazecake.moremid.entity.UserEntity;
import moe.dazecake.moremid.mapper.UserMapper;
import moe.dazecake.moremid.service.UserService;
import moe.dazecake.moremid.util.Result;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    @Override
    public Result register(String userName,
                           String password,
                           HttpServletRequest request) {
        //实例化注册对象
        UserEntity userEntity = UserEntity.builder()
                .userName(userName)
                .password(password)
                .build();

        //重复注册验证
        if (!userEntity.getUserName().equals("")) {
            UserEntity isRegistered = userMapper.selectOne(
                    Wrappers.<UserEntity>lambdaQuery()
                            .eq(UserEntity::getUserName, userEntity.getUserName())
            );
            if (isRegistered == null) {
                return Result.failed(403, "您已经在本服务器注册过了");
            }
        }

        //信息补全
        userEntity.setCreateTime(LocalDateTime.now());
        userEntity.setLastLoginIp(request.getRemoteAddr());

        var status = userMapper.insert(userEntity);

        if (status == 1) {
            return Result.success("Register success");
        } else {
            return Result.failed("Register fail");
        }
    }

    @Override
    public Result login() {
        return null;
    }

    @Override
    public Result resetPassword() {
        return null;
    }

    @Override
    public Result op() {
        return null;
    }

    @Override
    public Result deop() {
        return null;
    }

    @Override
    public Result getUserInfo() {
        return null;
    }

    @Override
    public Result updateUserInfo() {
        return null;
    }

    @Override
    public Result ban() {
        return null;
    }

    @Override
    public Result kick() {
        return null;
    }
}
