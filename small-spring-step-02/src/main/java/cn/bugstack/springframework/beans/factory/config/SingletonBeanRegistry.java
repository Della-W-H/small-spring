package cn.bugstack.springframework.beans.factory.config;

/**
 * 单例注册表
 */
public interface SingletonBeanRegistry {

    //获取单例

    Object getSingleton(String beanName);

}
