package cn.bugstack.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * 顶级 接口 定义规则
 * todo 这种层层设计的思想 一定要 多看多练啊 这何尝不是一种 初步的架构思维呢？ boy
 */
public interface Resource {

    InputStream getInputStream() throws IOException;

}
