package com.framework.tool.spring.processor;

import com.framework.tool.spring.annonation.Service;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

public class ServiceAnnotationBeanPostProcessor implements BeanDefinitionRegistryPostProcessor {


    private AnnotationBeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

    private String packagePath;

    public ServiceAnnotationBeanPostProcessor(String packagePath) {
        this.packagePath = packagePath;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
        scanner.resetFilters(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Service.class));
        scanner.scan(packagePath);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        return;
    }
}
