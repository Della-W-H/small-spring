package cn.bugstack.springframework.beans.factory.support;

import cn.bugstack.springframework.beans.BeansException;
import cn.bugstack.springframework.beans.factory.DisposableBean;
import cn.bugstack.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * Internal marker for a null singleton object:
     * used as marker value for concurrent Maps (which don't support null values).
     *
     * todo 注意了 这里也有一个 基础知识点的 即static类变量 的 初始化 可以说是在类初始化之前  如此demo中的AbstractRefreshableApplicationContext类中的操作 new DefaultListableBeanFactory()操作的时候 第一步是new 被调用还没执行时  第二步就直接 跳到了这个顶级抽象父类中 没有经过中间抽象继承类的往返调用
     * 若这个 是一个字符串变量 那么他已经被 提前加载进内存中了 不会在子类的new操作时 被 优先 加载 因为他已经更优先的被加载了 不信你可以看
     * AbstractApplicationContext类中的APPLICATION_EVENT_MULTICASTER_BEAN_NAME变量的加载过程 这其中的细节你可以看一下浏览器收藏的资料 我记得看到过 即实例变量只会在当前类被new或者以其他方式被使用时
     * 才会被加载赋值 但是对于static final修饰的变量而言 未被使用 但是字节码文件被加载进jvm中时 就已经被赋予了默认值了 此例子中一般而言为 null
     */
    protected static final Object NULL_OBJECT = new Object();

    private Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

    private final Map<String, DisposableBean> disposableBeans = new LinkedHashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    public void registerSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }

    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeans.put(beanName, bean);
    }

    @SuppressWarnings("ALL")
    public void destroySingletons() {
        Set<String> keySet = this.disposableBeans.keySet();
        Object[] disposableBeanNames = keySet.toArray();
        
        for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
            Object beanName = disposableBeanNames[i];
            DisposableBean disposableBean = disposableBeans.remove(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }
        }
    }

}
