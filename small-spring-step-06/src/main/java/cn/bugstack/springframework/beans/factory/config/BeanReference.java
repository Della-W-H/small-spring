package cn.bugstack.springframework.beans.factory.config;

/**
 *
 * Bean 的引用   嘿嘿 此时仍然是 一个String对象
 */
public class BeanReference {

    private final String beanName;

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }

}
