package moe.dazecake.moremid.controller;

import moe.dazecake.moremid.service.Impl.UserServiceImpl;
import moe.dazecake.moremid.util.Result;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Resource
    private UserServiceImpl userService;


    public Result register(String userName,
                           String password,
                           HttpServletRequest request) {
        return userService.register(userName, password,request);
    }
}
