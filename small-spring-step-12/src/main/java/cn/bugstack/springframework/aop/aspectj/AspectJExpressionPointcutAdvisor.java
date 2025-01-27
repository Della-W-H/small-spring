package cn.bugstack.springframework.aop.aspectj;

import cn.bugstack.springframework.aop.Pointcut;
import cn.bugstack.springframework.aop.PointcutAdvisor;
import org.aopalliance.aop.Advice;

/**
 * Spring AOP Advisor that can be used for any AspectJ pointcut expression.
 * 此类实现了PointcutAdvisor接口 把切面pointcut，拦截方法advice 和具体切面表达式 包装在了一起
 * 这样就可以在xml的配置定义一个pointcutAdvisor切面拦截器了
 */
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

    // 切面

    private AspectJExpressionPointcut pointcut;
    // 具体的拦截方法

    private Advice advice;
    // 表达式

    private String expression;

    public void setExpression(String expression){
        this.expression = expression;
    }

    @Override
    public Pointcut getPointcut() {
        if (null == pointcut) {
            pointcut = new AspectJExpressionPointcut(expression);
        }
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice){
        this.advice = advice;
    }

}
