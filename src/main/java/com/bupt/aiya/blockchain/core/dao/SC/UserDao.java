package com.bupt.aiya.blockchain.core.dao.SC;

import com.bupt.aiya.blockchain.core.entity.SC.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Created by aiya on 2019/3/17 下午9:52
 */
@Mapper
@Repository
public interface UserDao {
    User queryUserById(String userId);
    int insertUser(User user);
    int updateUser(User user);
    int deleteUser(int userId);
    String isExist(String usrid, String psw);
}
