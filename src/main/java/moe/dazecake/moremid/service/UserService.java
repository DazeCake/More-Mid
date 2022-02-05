package moe.dazecake.moremid.service;

import moe.dazecake.moremid.util.Result;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    Result register(String userName,
                    String password,
                    HttpServletRequest request);

    Result login();

    Result resetPassword();

    Result op();

    Result deop();

    Result getUserInfo();

    Result updateUserInfo();

    Result ban();

    Result kick();

}
