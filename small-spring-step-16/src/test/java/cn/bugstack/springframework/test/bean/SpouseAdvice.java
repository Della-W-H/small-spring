package cn.bugstack.springframework.test.bean;

import cn.bugstack.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * AOP 的实现
 */
public class SpouseAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("关怀小两口(切面)：" + method);
    }

}
