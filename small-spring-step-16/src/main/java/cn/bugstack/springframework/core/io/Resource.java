package cn.bugstack.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * 资源文件的保存信息（如地址等）最高抽象类
 *  对于资源文件本质上 的 抽象 并不会以具体的形式 保存到 内存中 的 而是以地址和io流的形式进行抽象
 *  具体 的 资源内容的 抽象 则 需要等到 具体的io流 转变为 实际的 beanDefinition及其中 的属性class文件中
 *  说人话 即 Resource类 更多的是对于 文件 保存地址和加载方法的抽象 而不是具体的 文件信息本身
 */
public interface Resource {

    InputStream getInputStream() throws IOException;

}
