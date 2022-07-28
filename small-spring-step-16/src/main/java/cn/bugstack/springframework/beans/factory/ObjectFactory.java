package cn.bugstack.springframework.beans.factory;

import cn.bugstack.springframework.beans.BeansException;

/**
 * Defines a factory which can return an Object instance
 * (possibly shared or independent) when invoked.
 *
 * 代理对象 标识接口
 */
public interface ObjectFactory<T> {

    T getObject() throws BeansException;

}
