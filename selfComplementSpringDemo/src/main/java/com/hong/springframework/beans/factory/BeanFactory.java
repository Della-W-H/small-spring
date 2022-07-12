package com.hong.springframework.beans.factory;

import com.hong.springframework.beans.BeansException;

/**
 * @author wanghong
 * @date 2022/7/12
 * @apiNote 定义bean工厂的顶级接口
 */
public interface BeanFactory {

    Object getBean(String name) throws BeansException;

    Object getBean(String name, Object[] args) throws BeansException;

    <T> T getBean(String name, Class<T> requiredType) throws BeansException;

    //注意 这边 不要将 T 泛型定义在 类名称中 这样意味 这个工厂类只能处理一类 对象 但是实际的情况是 这个工厂类是要处理 多个不同的bean对象
    //所以 最好他 处理的具体类型 的泛型数据 应该声明在 具体的处理方法中
}
