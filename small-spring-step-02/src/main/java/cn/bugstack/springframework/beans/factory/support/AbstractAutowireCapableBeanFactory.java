package cn.bugstack.springframework.beans.factory.support;

import cn.bugstack.springframework.beans.BeansException;
import cn.bugstack.springframework.beans.factory.config.BeanDefinition;

/**
 * 抽象实例化注入单例容器工厂类
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        Object bean;
        try {
            //todo 此处有一个坑：无参实例化可以 这样 有参的呢？如何入参呢？
            bean = beanDefinition.getBeanClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        //这个是父类（AbstractBeanFactory）的父类（DefaultSingletonBeanFactory） 的方法
        //即将 实例化后的对象添加到单例容器中

        addSingleton(beanName, bean);
        return bean;
    }


}
