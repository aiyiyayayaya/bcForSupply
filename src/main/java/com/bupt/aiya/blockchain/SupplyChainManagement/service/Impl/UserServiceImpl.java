package com.bupt.aiya.blockchain.SupplyChainManagement.service.Impl;


import com.bupt.aiya.blockchain.SupplyChainManagement.service.UserService;

import com.bupt.aiya.blockchain.core.dao.SC.UserDao;
import com.bupt.aiya.blockchain.core.entity.SC.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tio.client.AioClient;
import org.tio.core.Node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aiya on 2019/3/20 上午9:54
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(String userId) {
        return userDao.queryUserById(userId);
    }

    @Override
    public boolean login(String usrid, String psw) {
        String res = userDao.isExist(usrid, psw);
        if(res != null) {
            System.out.println("res is"+res);
            return true;
        } else {
            System.out.println("not exist!");
            return false;
        }
    }

    @Override
    public boolean addUser(User user) {
        if(user.getUsrId() != null && !"".equals(user.getUsrId())){
            try {
                int effectedNum = userDao.insertUser(user);
                if (effectedNum > 0)
                    return true;
                else
                    throw new RuntimeException("fail to insert!");
            }catch (Exception e){
                throw new RuntimeException("fail message:"+e.getMessage());
            }
        }else
            throw new RuntimeException("Id为空");
    }

}
