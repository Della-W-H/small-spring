package cn.bugstack.springframework.core.io;

import cn.hutool.core.lang.Assert;

import java.net.MalformedURLException;
import java.net.URL;

public class DefaultResourceLoader implements ResourceLoader {

    @Override
    @SuppressWarnings("all")
    public Resource getResource(String location) {
        Assert.notNull(location, "Location must not be null");
        if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
        }
        else {
            try {
                URL url = new URL(location);
                return new UrlResource(url);
                //如果 url 检查异常 就当他是 系统文件资源 的方式加载 ？ 实际不会这么武断吧？
            } catch (MalformedURLException e) {
                return new FileSystemResource(location);
            }
        }
    }

}
