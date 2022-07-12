package cn.bugstack.springframework.beans.factory;

import cn.bugstack.springframework.beans.BeansException;

/** 
 * bean 工厂的 顶级 父类定义接口
 */
public interface BeanFactory {

    Object getBean(String name) throws BeansException;

    Object getBean(String name, Object... args) throws BeansException;

    <T> T getBean(String name, Class<T> requiredType) throws BeansException;

}
