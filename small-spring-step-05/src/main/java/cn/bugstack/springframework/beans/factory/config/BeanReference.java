package cn.bugstack.springframework.beans.factory.config;

/**
 * Bean 的引用  具体暂用beanName代替
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
