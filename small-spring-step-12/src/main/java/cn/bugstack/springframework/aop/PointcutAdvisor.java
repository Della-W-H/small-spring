package cn.bugstack.springframework.aop;

/**
 * Superinterface for all Advisors that are driven by a pointcut.
 * This covers nearly all advisors except introduction advisors,
 * for which method-level matching doesn't apply.
 *
 * Advisor承担了Pointcut和Advice的组合 Pointcut用于获取JoinPoint，而Advice决定于JoinPoint执行什么操作
 */
public interface PointcutAdvisor extends Advisor {

    /**
     * Get the Pointcut that drives this advisor.
     */
    Pointcut getPointcut();

}
