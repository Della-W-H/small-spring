package cn.bugstack.springframework.test.bean;

import java.util.Random;


public class UserService implements IUserService {

    //注意一下 这边并没有 spring.xml配置文件中对应的属性信息 那么这些信息被配置上了吗？应该是有的？但是他们可以被调用出来吗？

    public String queryUserInfo() {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "小傅哥，100001，深圳";
    }

    public String register(String userName) {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "注册用户：" + userName + " success！";
    }

}
