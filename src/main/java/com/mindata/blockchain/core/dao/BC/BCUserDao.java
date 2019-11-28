package com.mindata.blockchain.core.dao.BC;

import com.mindata.blockchain.core.entity.BC.BCUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by aiya on 2019/3/18 下午4:23
 */
@Mapper
@Repository
public interface BCUserDao {
    List<String> selectUserIpList();
    List<BCUser> selectAll();
    BCUser selectUserByMac(String mac);
    int newUser(BCUser usr);
    int updateUser(BCUser usr);
    int deleteUser();
}
