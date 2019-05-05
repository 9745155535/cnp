package com.ljj.crazyandbox.cnp.provider.registry;


import com.ljj.crazyandbox.cnp.ReflectionUtility;
import com.ljj.crazyandbox.cnp.StringUtility;
import com.ljj.crazyandbox.cnp.anontation.EnableCnp;
import com.ljj.crazyandbox.cnp.provider.processor.annotation.CnpProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.io.IOException;
import java.util.Set;
/**
 * @author ljj
 * create time by 2019.4.2
 */
public class DefinitionProcessorRegistrar implements ImportBeanDefinitionRegistrar {

    private static final Logger log = LoggerFactory.getLogger(DefinitionCheckerRegistrar.class);

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(EnableCnp.class.getName()));
        String[] scan = annoAttrs.getStringArray("value");

        try {
            log.info("Cnp start add processor");
            Set<Class<?>> classes = ReflectionUtility.loadClassesByAnnotationClass(CnpProcessor.class, scan);
            classes.forEach(aClass -> {
                CnpProcessor annotation = aClass.getAnnotation(CnpProcessor.class);
                String name = annotation.name() == null || !StringUtility.allNotBlank(annotation.name()) ? aClass.getSimpleName() : annotation.name();
                String ruleString = annotation.rule();
                BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(aClass);
                beanDefinitionBuilder.addConstructorArgValue(name);
                beanDefinitionBuilder.addConstructorArgValue(ruleString);
                beanDefinitionRegistry.registerBeanDefinition(name, beanDefinitionBuilder.getBeanDefinition());

                log.info("Cnp" + " add processor :" + name +" rule: "+ruleString);

            });

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
