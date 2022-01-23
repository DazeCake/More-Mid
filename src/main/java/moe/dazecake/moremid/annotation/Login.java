package moe.dazecake.moremid.annotation;

import java.lang.annotation.*;

/**
 * 登陆验证 在需要登录才能调用的接口使用
 *
 * @author DazeCake
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Login {
}
