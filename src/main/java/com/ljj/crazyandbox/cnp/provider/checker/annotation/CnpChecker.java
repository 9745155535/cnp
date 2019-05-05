package com.ljj.crazyandbox.cnp.provider.checker.annotation;



import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @author ljj
 * create time by 2019.4.2
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CnpChecker {
    String grantedAuthority() default "";
}
