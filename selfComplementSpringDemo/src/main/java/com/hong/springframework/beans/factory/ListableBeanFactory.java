package com.hong.springframework.beans.factory;

import com.hong.springframework.beans.BeansException;

import java.util.Map;

/**
 * @author wanghong
 * @date 2022/7/12
 * @apiNote 定义实例处理后的 bean对象 及 beanDefinition 相应的存储数据 抽象接口 具体实现由子类处理
 */
public interface ListableBeanFactory {

    /**
     * 按照类型返回bean实例
     * @param type 类型
     * @param <T> 泛型 随 调用情况而定
     * @return 返回 给定 class文件 所对应的 具体的bean对象和 其所有的 继承或者实现类
     * @throws BeansException 自定义 runtime异常
     */
   <T> Map<String, T> getBeanOfType(Class<T> type) throws BeansException;

    /**
     * 返回注册表中所有 beanDefinition的名字
     * @return
     */
   String[] getBeanDefinitionNames();

}
