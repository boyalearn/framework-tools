package com.framework.tool.spring.annonation;


import com.framework.tool.spring.processor.ScanBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ScanBeanDefinitionRegistrar.class)
public @interface ServiceScan {

    String value();
}
