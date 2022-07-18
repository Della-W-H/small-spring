package cn.bugstack.springframework.beans.factory;

/**
 * Marker superinterface indicating that a bean is eligible to be
 * notified by the Spring container of a particular framework object
 * through a callback-style method.  Actual method signature is
 * determined by individual subinterfaces, but should typically
 * consist of just one void-returning method that accepts a single
 * argument.
 *
 * 标记类接口，实现该接口可以被Spring容器感知
 *
 * todo instanceof 一般会搭配接口 做这种 标志类的灵活判断 顺便做转型执行 判断与否成功后的 方法
 *
 */
public interface Aware {
}
                             