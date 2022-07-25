package cn.bugstack.springframework.aop.framework;

import cn.bugstack.springframework.aop.AdvisedSupport;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CGLIB2-based {@link AopProxy} implementation for the Spring AOP framework.
 *
 * <p><i>Requires CGLIB 2.1+ on the classpath.</i>.
 * As of Spring 2.0, earlier CGLIB versions are not supported anymore.
 *
 * <p>Objects of this type should be obtained through proxy factories,
 * configured by an AdvisedSupport object. This class is internal
 * to Spring's AOP framework and need not be used directly by client code.
 */
public class Cglib2AopProxy implements AopProxy {

    private final AdvisedSupport advised;

    public Cglib2AopProxy(AdvisedSupport advised) {
        this.advised = advised;
    }

    @Override
    @SuppressWarnings("all")
    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        //读说cglib 代理实现继承代理 这就是 实际啊 将被代理对象置为父类
        enhancer.setSuperclass(advised.getTargetSource().getTarget().getClass());
        //将被代理的对象 实现的 所有 父类接口 置为 代理对象实现
        enhancer.setInterfaces(advised.getTargetSource().getTargetClass());
        //todo 注意比较 此处和 实例化策略中 所使用的cglib 实例化的不同
        //这说明 这个 可以 直接setCallback一个必要的方法即 实现hash的重运算 毕竟代理生成的类是一个 新的类 当然你也可以不重运算但是会有些 问题
        enhancer.setCallback(new DynamicAdvisedInterceptor(advised));
        return enhancer.create();
    }

    /**
     * 这里 之所以是会有一个内部类 就是因为 setCallback()方法参数就是一个类对象 而 这个类对象 一般也只有这一个地方会有
     * 那为啥不定义成一个唯一的天生的单例内部类呢？你说是不是呢？boy!
     * besides! MethodInterceptor 本身就已经继承了Callback接口
     */
    private static class DynamicAdvisedInterceptor implements MethodInterceptor {

        private final AdvisedSupport advised;

        public DynamicAdvisedInterceptor(AdvisedSupport advised) {
            this.advised = advised;
        }

        /**
         *
         * 如果说 JDK代理的话 任何被代理的方法都会通过 invoke方法被执行。 那么对于 cglib代理而言那就是 通过intercept并不 他只是会执行Callback类中的方法 一如 之前实例化策略中的demo只是执行了hashCode()方法
         * 额 上面话语有误 从源码中可以看出来 callback接口中并没有定义任何 方法 只是类似于 标记类一样 他在概念上而言是一个 没有定义任何方法 的 顶级抽象接口 (⊙﹏⊙) 这样好像和 标记接口也没啥区别啊 滑稽
         * todo   通过IDEA工具可以看见 栈帧中intercept方法的 入参全被 赋值成功了 你要思考一下 他是如何赋值成功的
         */
        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            CglibMethodInvocation methodInvocation = new CglibMethodInvocation(advised.getTargetSource().getTarget(), method, objects, methodProxy);
            if (advised.getMethodMatcher().matches(method, advised.getTargetSource().getTarget().getClass())) {
                //这个 方法拦截器的 invoke方法是我们 重定义的 invoke方法 进去看 实现逻辑其实也很有趣
                return advised.getMethodInterceptor().invoke(methodInvocation);
            }
            //这里还是老老实实用了JDK的放射包的invoke处理 没有再做任何花里胡哨到的东西了
            return methodInvocation.proceed();
        }
    }

    private static class CglibMethodInvocation extends ReflectiveMethodInvocation {

        private final MethodProxy methodProxy;

        public CglibMethodInvocation(Object target, Method method, Object[] arguments, MethodProxy methodProxy) {
            super(target, method, arguments);
            this.methodProxy = methodProxy;
        }

        @Override
        public Object proceed() throws Throwable {
            return this.methodProxy.invoke(this.target, this.arguments);
        }

    }

}
