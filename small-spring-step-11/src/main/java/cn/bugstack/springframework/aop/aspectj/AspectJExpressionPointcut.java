package cn.bugstack.springframework.aop.aspectj;

import cn.bugstack.springframework.aop.ClassFilter;
import cn.bugstack.springframework.aop.MethodMatcher;
import cn.bugstack.springframework.aop.Pointcut;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * Spring {@link cn.bugstack.springframework.aop.Pointcut} implementation
 * that uses the AspectJ weaver to evaluate a pointcut expression.
 * <p>
 * 切点表达式 还是通过spring AspectJ 实现AOP 没有进一步 做 自己的底层实现捏
 * <p>
 */
public class AspectJExpressionPointcut implements Pointcut, ClassFilter, MethodMatcher {

    /**
     * An set of the different kinds of pointcut primitives
     * supported by AspectJ.
     * 注意哦 这边 看源码 也可以 了解 可以定义不同的 切面支持类型 哦 毕竟 这个类的大项就是一个tools工具类
     */
    private static final Set<PointcutPrimitive> SUPPORTED_PRIMITIVES = new HashSet<PointcutPrimitive>();

    static {
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
    }

    private final PointcutExpression pointcutExpression;

    public AspectJExpressionPointcut(String expression) {
        //获取 切面表达式的 解析类
        PointcutParser pointcutParser = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(SUPPORTED_PRIMITIVES, this.getClass().getClassLoader());
        //解析 String类型的切面表达式
        // execution(* cn.bugstack.springframework.test.bean.UserService.*(..))
        // 切面结果 PointcutExpressionImpl
        pointcutExpression = pointcutParser.parsePointcutExpression(expression);
    }

    /**
     * ClassFilter
     * 调用AspectJ 对候选class字节码文件进行判断 其是否 是可满足切入的目标对象
     * @param clazz the candidate target class
     * @return boolean
     */
    @Override
    public boolean matches(Class<?> clazz) {
        return pointcutExpression.couldMatchJoinPointsInType(clazz);
    }

    /**
     * MethodMatcher
     * 这一部分即 对 类中的 方法进行判断 看 类中 方法是否存在可以 被切入的点 alwaysMatches()定义判断模式 具体模式可以敲进去看一下
     * @param method
     * @param targetClass
     * @return
     */
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return pointcutExpression.matchesMethodExecution(method).alwaysMatches();
    }

    /**
     *  Pointcut
     * @return
     */
    @Override
    public ClassFilter getClassFilter() {
        return this;
    }

    /**
     *  Pointcut
     * @return
     */
    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }

}
