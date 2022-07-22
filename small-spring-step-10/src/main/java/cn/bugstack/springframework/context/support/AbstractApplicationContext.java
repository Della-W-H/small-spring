package cn.bugstack.springframework.context.support;

import cn.bugstack.springframework.beans.BeansException;
import cn.bugstack.springframework.beans.factory.ConfigurableListableBeanFactory;
import cn.bugstack.springframework.beans.factory.config.BeanFactoryPostProcessor;
import cn.bugstack.springframework.beans.factory.config.BeanPostProcessor;
import cn.bugstack.springframework.context.ApplicationEvent;
import cn.bugstack.springframework.context.ApplicationListener;
import cn.bugstack.springframework.context.ConfigurableApplicationContext;
import cn.bugstack.springframework.context.event.ApplicationEventMulticaster;
import cn.bugstack.springframework.context.event.ContextClosedEvent;
import cn.bugstack.springframework.context.event.ContextRefreshedEvent;
import cn.bugstack.springframework.context.event.SimpleApplicationEventMulticaster;
import cn.bugstack.springframework.core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

/**
 * Abstract implementation of the {@link cn.bugstack.springframework.context.ApplicationContext}
 * interface. Doesn't mandate the type of storage used for configuration; simply
 * implements common context functionality. Uses the Template Method design pattern,
 * requiring concrete subclasses to implement abstract methods.
 * <p>
 * 抽象应用上下文
 * <p>
 *
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    //todo 还记得 前面有一个 笔记吗？为啥 static修饰的类变量赋值 会比子类构造方法new执行的优先级还要高 而这边 不会直接一步过来呢？ 一个合理的解释就是 这个字节码文件在加载进内存的时候 static属性已经被提前加载进了 堆内存中了 而 new 出来的static属性变量则不会 而是会在new被调用时 才会被优先加载
    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    //引用上下文中 顶级抽象类中 放入了 事件广播

    private ApplicationEventMulticaster applicationEventMulticaster;

    @Override
    @SuppressWarnings("all")
    public void refresh() throws BeansException {
        // 1. 创建 BeanFactory，并加载 BeanDefinition
        refreshBeanFactory();

        // 2. 获取 BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 3. 添加 ApplicationContextAwareProcessor，让继承自 ApplicationContextAware 的 Bean 对象都能感知所属的 ApplicationContext
        //todo 注意一下 这个 继承了BeanPostProcessor的Aware感知类并没有被放入 SingletonBean容器中 而其他的 都会放入其中 但是都会统一放入 AbstractBeanFactory类中的名为beanPostProcessors的list形式的容器中 只是其他的processor还会同时存在于SingletonObjects容器中
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        // 4. 在 Bean 实例化之前，执行 BeanFactoryPostProcessor (Invoke factory processors registered as beans in the context.)
        invokeBeanFactoryPostProcessors(beanFactory);

        // 5. BeanPostProcessor 需要提前于其他 Bean 对象实例化之前执行注册操作
        registerBeanPostProcessors(beanFactory);

        // 6. 初始化事件发布者
        initApplicationEventMulticaster();

        // 7. 注册事件监听器
        registerListeners();

        // 8. 提前实例化单例Bean对象
        //这里的提前实例化单例bean操作 一如既往 没有 考察判断 beanDefinition数据中的scope参数 一股脑全部实例化了
        beanFactory.preInstantiateSingletons();

        // 9. 发布容器刷新完成事件
        finishRefresh();
    }

    protected abstract void refreshBeanFactory() throws BeansException;

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    private void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        //这里意味着将beanFactory工厂全部即相关beanDefinition信息 注入到了发布者对象中了
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);

        //顺便将 发布者 对象交给了 spring容器管理 所以说 这个发布者可以有 有 很多 spring 可以默认替你执行的方法 比如可以实现你自己的销毁方法
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }

    private void registerListeners() {
        //从beanDefinition中取出 Listener定义信息对象并注册成实例对象 放入到spring的单例容器中
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        for (ApplicationListener listener : applicationListeners) {
            //此时将 listeners对象们 注入到了 发布者对象中了
            applicationEventMulticaster.addApplicationListener(listener);
        }
    }

    private void finishRefresh() {
        //todo 这里的this指的并不是 AbstractApplicationContext类 而是实际调用链条上的发起调用类 此处正真指的就是ClassPathXmlApplicationContext对象 基础中基础
        publishEvent(new ContextRefreshedEvent(this));
    }

    //发布事件 广播着广播事件

    @Override
    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        // 发布容器关闭事件
        publishEvent(new ContextClosedEvent(this));

        // 执行销毁单例bean的销毁方法
        getBeanFactory().destroySingletons();
    }

}
