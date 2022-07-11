package cn.bugstack.springframework.beans.factory.config;

@SuppressWarnings({"rawtypes"})
public class BeanDefinition {

    /**
     * 相比step1中Object定义 此处为啥要用class文件的形式呢？
     * 核心原因还是因为 在bean注册的时候实际是只需要注册一个类的信息 而不会将bean直接实例化 也是一种懒加载的思想
     */
    private Class beanClass;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }
}
