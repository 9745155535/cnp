package com.ljj.crazyandbox.cnp.provider.processor.annotation;


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
public @interface CnpProcessor {
    String name() default "";
    String rule() default "";
}
