package cn.bugstack.springframework.beans.factory.config;

import cn.bugstack.springframework.beans.BeansException;
import cn.bugstack.springframework.beans.PropertyValues;

/**
 * Subinterface of {@link BeanPostProcessor} that adds a before-instantiation callback,
 * and a callback after instantiation but before explicit properties are set or
 * autowiring occurs. This interface is a special purpose interface, mainly for internal use within the framework.
 * 看上面的 英文解释 仔细看 他所继承的 方法和原接口的不同
 * TODO 这就引出来了一个 在泛型的类文件Class<?>和 Object 对象在java中是一样的嘛？为啥 这样的继承实现方法没有报错呢？
 *
 * 再者 可以看出 此时传递的参数为字节码文件 意味 此类的实现类 亦可 在bean创建的途中修改字节码文件 这没有问题吗？不是和BeanPostFactoryProcessor 的职责混淆了吗？
 * 同时这个真的是在实例化和初始化之前的起作用的类啊 而不像beanPostProcessor单纯地实例化后再初始化前后起作用 亦或是像是BeanFactoryPostProcessor单纯地实例化之前起作用
 * 所以即并没有职责混淆啊
 * 那就再spring的真正源码中 去 判断吧？
 * 哇哦哇哦 实际spring中同样时如此处理的
 *
 * 这个spring提供的 接口 真心十分强大 但是一般为框架内部使用 而且感觉 这种类型的BeanPostProcessor并不会 放入到 相关容器总单例保存.....至于为啥？可能是 相关类的每一次创建其属性 会进行变动..... 并不最好还是会放进容器中的
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    /**
     * Apply this BeanPostProcessor <i>before the target bean gets instantiated</i>.
     * The returned bean object may be a proxy to use instead of the target bean,
     * effectively suppressing default instantiation of the target bean.
     * <p>
     * 在 Bean 对象执行初始化方法之前，执行此方法 确切的说应该是在实例化之前 即可以修改字节码文件
     *
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

    /**
     * Perform operations after the bean has been instantiated, via a constructor or factory method,
     * but before Spring property population (from explicit properties or autowiring) occurs.
     * <p>This is the ideal callback for performing field injection on the given bean instance.
     * See Spring's own {@link cn.bugstack.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor}
     * for a typical example.
     * <p>
     * 在 Bean 对象执行初始化方法之后，执行此方法
     * todo 但是 此方法返回值 为boolean类型 意味着 我可以 处理完后或者 不处理 返回 特定的boolean类型值
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException;

    /**
     * Post-process the given property values before the factory applies them
     * to the given bean. Allows for checking whether all dependencies have been
     * satisfied, for example based on a "Required" annotation on bean property setters.
     * <p>
     * 在 Bean 对象实例化完成后，设置属性操作之前执行此方法 可以自定义修改属性
     *
     * @param pvs
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException;

    /**
     * 在 Spring 中由 SmartInstantiationAwareBeanPostProcessor#getEarlyBeanReference 提供
     * @param bean
     * @param beanName
     * @return
     */
    default Object getEarlyBeanReference(Object bean, String beanName) {
        return bean;
    }

}
