package cn.bugstack.springframework.test.bean;

/**
 */
public class UserService {

    private String name;

    private String code;

    public UserService() {
    }
    
    public UserService(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public void queryUserInfo() {
        System.out.println("查询用户信息, name:" + name + " code:" + code);
    }

    @Override
    public String toString() {
        return "" + name +" " + code;
    }
}
