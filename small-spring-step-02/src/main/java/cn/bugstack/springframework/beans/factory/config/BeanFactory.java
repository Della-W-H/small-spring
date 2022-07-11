package cn.bugstack.springframework.beans.factory.config;

import cn.bugstack.springframework.beans.BeansException;

/**
 * 抽象bean工厂只有一个抽象方法即获取bean
 */
public interface BeanFactory {

    Object getBean(String name) throws BeansException;

}
