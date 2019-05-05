package com.ljj.crazyandbox.cnp.anontation;


import com.ljj.crazyandbox.cnp.provider.registry.DefinitionCheckerRegistrar;
import com.ljj.crazyandbox.cnp.provider.registry.DefinitionManageRegister;
import com.ljj.crazyandbox.cnp.provider.registry.DefinitionProcessorRegistrar;
import org.springframework.context.annotation.Import;

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
@Import({DefinitionProcessorRegistrar.class,DefinitionCheckerRegistrar.class,DefinitionManageRegister.class})
public @interface EnableCnp {
    String[] value() default {}; //扫描路径
    boolean enableDefaultConfig() default false;
}
