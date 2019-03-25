import com.beans.UserPojo;
import com.dao.UserDao;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;


public class TestUserDao {
    @Test
    public void testFindByName() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                "conf/spring-mybatis.xml");
        UserDao dao = ctx.getBean("userDao", UserDao.class);
        UserPojo user = dao.queryUser("周科达");
        System.out.println(user.toString());
    }

    @Test
    public void findAll() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                "conf/spring-mybatis.xml");
        UserDao dao = ctx.getBean("userDao", UserDao.class);
        List<UserPojo> user = dao.queryAllUser();
        for (UserPojo userPojo : user) {
            System.out.println(userPojo.toString());
        }
    }

    @Test
    public void testAddUser() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext(
                "conf/spring-mybatis.xml");
        UserDao dao = ctx.getBean("userDao", UserDao.class);
        UserPojo user = new UserPojo();
        user.setAge(5);
        user.setOldPassword("1212");
        user.setPassword("1212");
        user.setUserName("dada");
        dao.insertUserBean(user);
    }
}







