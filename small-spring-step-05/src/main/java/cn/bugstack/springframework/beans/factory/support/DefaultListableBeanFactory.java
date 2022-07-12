package cn.bugstack.springframework.beans.factory.support;

import cn.bugstack.springframework.beans.BeansException;
import cn.bugstack.springframework.beans.factory.ConfigurableListableBeanFactory;
import cn.bugstack.springframework.beans.factory.config.BeanDefinition;
import cn.bugstack.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {

    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    // TODO: 2022/7/12  这个方法是干啥的啊？通过 类名获得 相应的类及其所有的子类？

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        Map<String, T> result = new HashMap<>();
        beanDefinitionMap.forEach((beanName, beanDefinition) -> {
            Class beanClass = beanDefinition.getBeanClass();

            //这个 方法判断的是 当前类文件是否为 给定类的超类 意味着 此处 如果为true 则type为 beanClass的父类
            // 结果好像是 将当前类文件的类及其子类 都加载出来了 放进了这个result的map容器中

            if (type.isAssignableFrom(beanClass)) {

                //此处的 getBean()方法源自于AbstractAutowireCapableBeanFactory 继承的AbstractBeanFactory中
                // 这个方法 在beanDefinition容器中 无相应的beanDefinition数据时 会 返回来 直接调用子类 AbstractAutowireCapableBeanFactory的 createBean方法
                //todo 是不是很神奇 ？ 这就是模板方法的好处啊 一个类只负责一块功能职责 同时 你要理解调用主体是谁 你就会明白 他为什么会返回去调用了
                //Lock 的AQS部分设计 也是如此 建议 多加复习

                //此处的 (T) 类型的强转 意味者 子类向父类转型 这本身就是一种 多态的形式 啊
                result.put(beanName, (T) getBean(beanName));
            }
        });
        return result;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) throw new BeansException("No bean named '" + beanName + "' is defined");
        return beanDefinition;
    }

}
