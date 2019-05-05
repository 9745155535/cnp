package com.ljj.crazyandbox.cnp.provider.registry;


import com.ljj.crazyandbox.cnp.ReflectionUtility;
import com.ljj.crazyandbox.cnp.StringUtility;
import com.ljj.crazyandbox.cnp.anontation.EnableCnp;
import com.ljj.crazyandbox.cnp.droit.GrantedAuthority;
import com.ljj.crazyandbox.cnp.provider.checker.annotation.CnpChecker;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.Set;
/**
 * @author ljj
 * create time by 2019.4.2
 */
public class DefinitionCheckerRegistrar implements ImportBeanDefinitionRegistrar {

    private static final Logger log = LoggerFactory.getLogger(DefinitionCheckerRegistrar.class);
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(EnableCnp.class.getName()));
        String[] scan = annoAttrs.getStringArray("value");

        try {
            log.info("cnp start add checker");
            Set<Class<?>> classes = ReflectionUtility.loadClassesByAnnotationClass(CnpChecker.class, scan);
            classes.forEach(aClass->{
                CnpChecker annotation = aClass.getAnnotation(CnpChecker.class);
                String name =  annotation.grantedAuthority()==null||!StringUtility.allNotBlank(annotation.grantedAuthority())?aClass.getSimpleName():annotation.grantedAuthority();


                BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(aClass);
                beanDefinitionBuilder.addConstructorArgValue(new GrantedAuthority() {
                    @Override
                    public String getValue() {
                        return name;
                    }
                });
                beanDefinitionRegistry.registerBeanDefinition(name,beanDefinitionBuilder.getBeanDefinition());
                log.info("Cnp"+" add checker :"+name );

            });

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
