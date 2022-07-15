package cn.bugstack.springframework.util;

public class ClassUtils {

    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            //其实此处生成的 classloader 和下面拿到的 classloader是一样的 都是appClassLoader
            //todo 但是 这不是真正的 作用 而是 因为 当你第一次拿到这个 类加载器的时候 以后的每一次 你拿到的同时同一个类加载器 保证了 类加载器 不会被重复创建然后不用就销毁
            cl = Thread.currentThread().getContextClassLoader();
        }
        catch (Throwable ex) {
            // Cannot access thread context ClassLoader - falling back to system class loader...
        }
        if (cl == null) {
            // No thread context class loader -> use class loader of this class.
            cl = ClassUtils.class.getClassLoader();
        }
        return cl;
    }

}
