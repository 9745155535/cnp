package com.ljj.crazyandbox.cnp.provider.registry;


import com.ljj.crazyandbox.cnp.DefaultOperateService;
import com.ljj.crazyandbox.cnp.RelationResolver;
import com.ljj.crazyandbox.cnp.anontation.EnableCnp;
import com.ljj.crazyandbox.cnp.provider.checker.DefaultCheckerManage;
import com.ljj.crazyandbox.cnp.provider.processor.DefaultProcessorManage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
/**
 * @author ljj
 * create time by 2019.4.2
 */
public class DefinitionManageRegister implements ImportBeanDefinitionRegistrar {
    private static final Logger log = LoggerFactory.getLogger(DefinitionCheckerRegistrar.class);


    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(EnableCnp.class.getName()));
        boolean enableDefaultConfig = annoAttrs.getBoolean("enableDefaultConfig");
        if(enableDefaultConfig) {
            log.info("Cnp:开启默认管理配置");

            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(DefaultOperateService.class);
            beanDefinitionBuilder.addConstructorArgValue(new RelationResolver());
            BeanDefinitionBuilder definitionProcessorManage = BeanDefinitionBuilder.genericBeanDefinition(DefaultProcessorManage.class);
            BeanDefinitionBuilder definitionCheckerManage = BeanDefinitionBuilder.genericBeanDefinition(DefaultCheckerManage.class);

            beanDefinitionRegistry.registerBeanDefinition(DefaultOperateService.class.getSimpleName(),beanDefinitionBuilder.getBeanDefinition());
            beanDefinitionRegistry.registerBeanDefinition(DefaultProcessorManage.class.getSimpleName(),definitionProcessorManage.getBeanDefinition());
            beanDefinitionRegistry.registerBeanDefinition(DefaultCheckerManage.class.getSimpleName(),definitionCheckerManage.getBeanDefinition());

        }

    }
}
