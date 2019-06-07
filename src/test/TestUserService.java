import com.beans.UserPojo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class TestUserService {
    private UserServiceInface service;

    @Before
    public void init() {
        String[] conf = {"conf/spring-mvc.xml", "conf/spring-mybatis.xml"};
        ApplicationContext ctx = new ClassPathXmlApplicationContext(conf);
        //getBean里面第二个参数是接口类
        service = ctx.getBean("UserServiceInface", UserServiceInface.class);
    }

    @Test //用例-3,预期:登录成功
    public void test3() {
        service.toLogin("可可", "123");
        System.out.println("请求情况：" + service.toLogin("周科达", "123456"));
    }

    @Test //用例-4,预期插入数据成功
    public void test4() {
        UserPojo user = new UserPojo();
        user.setAge(5);
        user.setOldPassword("121212");
        user.setPassword("121212");
        user.setUserName("dada2");
        System.out.println("插入数据：" + service.toRegister(user));
    }
}















