package cn.bugstack.springframework;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 在bean工厂的实现中，包括了bean的注册，注册的即为bean的定义信息即beanDefinition类，同时此类中包含具体的bean获取方式
 * 目前只是最基础的信息，后续会复杂起来
 */
public class BeanFactory {

    //注意此处的并发map的使用 即 模拟实际情况中的类容器

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public Object getBean(String name) {
        return beanDefinitionMap.get(name).getBean();
    }

    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(name, beanDefinition);
    }

}
