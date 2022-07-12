package cn.bugstack.springframework.core.io;

import cn.hutool.core.lang.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class UrlResource implements Resource{

    private final URL url;

    //此类不存在 无参构造函数

    public UrlResource(URL url) {
        Assert.notNull(url,"URL must not be null");
        this.url = url;
    }

    @SuppressWarnings("all")
    @Override
    public InputStream getInputStream() throws IOException {

        URLConnection con = this.url.openConnection();

        try {
            return con.getInputStream();
        }
        catch (IOException ex){
            //如果是 一种 获取 云端或者 远程配置的 方式 就得 手动关闭 远端连接

            if (con instanceof HttpURLConnection){
                ((HttpURLConnection) con).disconnect();
            }
            throw ex;
        }
    }

}
