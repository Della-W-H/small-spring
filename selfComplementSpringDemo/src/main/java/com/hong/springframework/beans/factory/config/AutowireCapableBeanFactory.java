package com.hong.springframework.beans.factory.config;

import com.hong.springframework.beans.BeansException;
import com.hong.springframework.beans.factory.BeanFactory;

/**
 * @author wanghong
 * @date 2022/7/12
 * @apiNote  这个就是对工厂场中的 类做 自定义操作的 方法规范 定义接口
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    /**
     *  执行BeanPostProcessors 接口 实现类 postProcessBeforeInitialization 方法
     * @param existingBean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String  beanName) throws BeansException;

    /**
     * 执行 BeanPostProcessors 接口 实现类 的 postProcessorsAfterInitialization 方法
     * @param existing
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existing, String beanName) throws BeansException;
}
