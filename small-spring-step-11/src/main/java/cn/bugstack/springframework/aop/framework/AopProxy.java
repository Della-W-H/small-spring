package cn.bugstack.springframework.aop.framework;

/**
 * Delegate interface for a configured AOP proxy, allowing for the creation
 * of actual proxy objects.
 *
 * <p>Out-of-the-box implementations are available for JDK dynamic proxies
 * and for CGLIB proxies, as applied by DefaultAopProxyFactory
 *
 * AOP 代理的抽象
 *
 * <p>
 * 本质而言 Cglib的动态代理和JDK的动态代理 除了 生成代理对象的方式不同以外 执行方法层面上都是相同的即 调用refection反射方法 通常而言就是invoke
 * jdk创建对象的速度远大于cglib，这是由于cglib创建对象时需要操作字节码。cglib执行速度略大于jdk，所以比较适合单例模式。
 * 另外由于CGLIB的大部分类是直接对Java字节码进行操作，这样生成的类会在Java的永久堆中。
 * 如果动态代理操作过多，容易造成永久堆满，触发OutOfMemory异常。spring默认使用jdk动态代理，如果类没有接口，则使用cglib。
 *
 */
public interface AopProxy {

    Object getProxy();

}
