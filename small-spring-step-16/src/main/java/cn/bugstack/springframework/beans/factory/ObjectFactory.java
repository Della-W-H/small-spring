package cn.bugstack.springframework.beans.factory;

import cn.bugstack.springframework.beans.BeansException;

/**
 * Defines a factory which can return an Object instance
 * (possibly shared or independent) when invoked.
 *
 * 生成对象 此接口 职责来看 可以算是一个单纯的FunctionalInterface
 */
public interface ObjectFactory<T> {

    T getObject() throws BeansException;

}
