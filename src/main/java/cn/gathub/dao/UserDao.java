package cn.gathub.dao;

import cn.gathub.beans.UserPojo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 与数据库交互的类
 */
@Repository
public interface UserDao {
    UserPojo queryUser(String userName);

    List<UserPojo> queryAllUser();

    void insertUserBean(UserPojo userPojo);
}
