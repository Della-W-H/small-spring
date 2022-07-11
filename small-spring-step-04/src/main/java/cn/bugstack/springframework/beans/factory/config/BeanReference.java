package cn.bugstack.springframework.beans.factory.config;

/**
 * Bean 的引用
 */
public class BeanReference {

    //实际不会是 String

    private final String beanName;

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }

}
