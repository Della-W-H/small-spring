package cn.bugstack.springframework.beans.factory.support;

import cn.bugstack.springframework.beans.BeansException;
import cn.bugstack.springframework.beans.factory.config.BeanDefinition;
import cn.bugstack.springframework.beans.factory.config.BeanDefinitionRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认bean工厂实现类
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {

    //此处为bean的class文件数据集合 有别于DefaultSingletonBeanRegistry中的singletonObjects的具体实现类集合对象

    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) throw new BeansException("No bean named '" + beanName + "' is defined");
        return beanDefinition;
    }

    //这个工厂类已经包括 AbstractAutowireCapableBeanFactory.createBean()即创建 bean对象实例并保存的方法了

    /*@Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        return super.createBean(beanName, beanDefinition);
    }*/

    //这个工厂类中也包括了 添加 实例化后的bean对象到bean工厂中的方法

    /*@Override
    protected void addSingleton(String beanName, Object singletonObject) {
        super.addSingleton(beanName, singletonObject);
    }*/

}
