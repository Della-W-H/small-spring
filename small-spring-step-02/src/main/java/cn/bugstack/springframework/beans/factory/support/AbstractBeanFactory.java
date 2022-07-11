package cn.bugstack.springframework.beans.factory.support;

import cn.bugstack.springframework.beans.BeansException;
import cn.bugstack.springframework.beans.factory.config.BeanFactory;
import cn.bugstack.springframework.beans.factory.config.BeanDefinition;

/**
 * BeanDefinition 注册表接口
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    @Override
    public Object getBean(String name) throws BeansException {
        //先看看 从单例工厂能否拿到 已经实例化的 单例对象
        Object bean = getSingleton(name);
        if (bean != null) {
            return bean;
        }

        //无法拿到 即 创建 对象 这个创建对象的方法由AbstractAutowireCapableBeanFactory对象实现
        //他实现后 会添加到 单例对象 容器中

        BeanDefinition beanDefinition = getBeanDefinition(name);
        return createBean(name, beanDefinition);
    }

    /**
     * 由 DefaultListableBeanFactory实现
     *  即从 beanDefinitionMap中获取 相应的bean字节码class文件信息 准备将class对象实例化
     * @param beanName
     * @return
     * @throws BeansException
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 由AbstractAutowireCapableBeanFactory实现
     * 实力化 拿到的字节码文件 并将 实例化后的bean对象存入 singletonObjects集合中
     * @param beanName
     * @param beanDefinition
     * @return
     * @throws BeansException
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException;

}
