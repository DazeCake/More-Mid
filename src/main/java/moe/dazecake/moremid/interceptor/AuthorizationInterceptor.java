package moe.dazecake.moremid.interceptor;

import io.jsonwebtoken.Claims;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import moe.dazecake.moremid.annotation.Login;
import moe.dazecake.moremid.util.JwtUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Data
@Component
@Slf4j
public class AuthorizationInterceptor implements HandlerInterceptor {
    public static final String USER_KEY = "userId";
    private final JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        Login annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(Login.class);
        } else {
            return true;
        }

        if (annotation == null) {
            return true;
        }

        //获取用户凭证
        String token = request.getHeader(jwtUtils.getHeader());
        if (token.isBlank()) {
            token = request.getParameter(jwtUtils.getHeader());
        }

        //凭证为空
        if (token.isBlank()) {
            log.warn(jwtUtils.getHeader() + "不能为空");
            return false;
        }

        Claims claims = jwtUtils.getClaimByToken(token);
        if (claims == null || jwtUtils.isTokenExpired(claims.getExpiration())) {
            log.warn(jwtUtils.getHeader() + "失效，请重新登录");
            return false;
        }

        //设置userId到request里，后续根据userId，获取用户信息
        request.setAttribute(USER_KEY, Long.parseLong(claims.getSubject()));

        return true;
    }
}

