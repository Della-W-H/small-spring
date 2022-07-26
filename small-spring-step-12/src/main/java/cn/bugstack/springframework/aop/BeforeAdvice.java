package cn.bugstack.springframework.aop;

import org.aopalliance.aop.Advice;

/**
 * Common marker interface for before advice, such as {@link MethodBeforeAdvice}.
 *
 * 定义advice拦截器链 中之一
 */
public interface BeforeAdvice extends Advice {

}
