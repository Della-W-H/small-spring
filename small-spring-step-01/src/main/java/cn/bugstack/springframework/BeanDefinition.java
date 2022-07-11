package cn.bugstack.springframework;

/**
 * 目前 Bean定义中 只有一个Object用于存放Bean对象。
 * 在spring源码中 这个类，确切的说这个接口中还有其他信息
 * 如 SCOPE_SINGLETON, SCOPE_PROTOTYPE, ROLE_APPLICATION, ROLE_SUPPORT, ROLE_INFRASTRUCTURE 以及bean class等信息
 */
public class BeanDefinition {

    private Object bean;

    public BeanDefinition(Object bean) {
        this.bean = bean;
    }

    public Object getBean() {
        return bean;
    }

}
