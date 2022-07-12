package cn.bugstack.springframework.util;

public class ClassUtils {

    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            //上下文加载器 具体加载器对象视 实际而定 但是一般都会是 appClassLoader 即system class loader 为什么下面的方法拿到的应该是同一套类加载器啊？
            //对啊 就是同一套加载器 但是为什么要这么做呢？
            cl = Thread.currentThread().getContextClassLoader();
            System.out.println(cl+"1");
        }
        catch (Throwable ex) {
            // Cannot access thread context ClassLoader - falling back to system class loader...
        }
        if (cl == null) {
            // No thread context class loader -> use class loader of this class.
            cl = ClassUtils.class.getClassLoader();
            System.out.println(ClassUtils.class.getClassLoader()+"2");
        }
        return cl;
    }

}
