package cn.bugstack.springframework.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * Base class for AOP proxy configuration managers.
 * These are not themselves AOP proxies, but subclasses of this class are
 * normally factories from which AOP proxy instances are obtained directly.
 * <p>
 *     包装切面通知信息类
 *     主要是用于将 代理 拦截 匹配 的各项功能属性包装到一个类中 方便Proxy实现类进行使用 这和业务中包装入参是一个道理
 *
 */
public class AdvisedSupport {

    // ProxyConfig false选择JDK创建代理类 true选择Cglib创建代理类

    private boolean proxyTargetClass = false;

    // 被代理的目标对象

    private TargetSource targetSource;
    // 方法拦截器 （用户可自定义实现）

    private MethodInterceptor methodInterceptor;
    // 方法匹配器(检查目标方法是否符合通知条件)

    private MethodMatcher methodMatcher;

    public boolean isProxyTargetClass() {
        return proxyTargetClass;
    }

    public void setProxyTargetClass(boolean proxyTargetClass) {
        this.proxyTargetClass = proxyTargetClass;
    }

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public MethodInterceptor getMethodInterceptor() {
        return methodInterceptor;
    }

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }

    public void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }
}
