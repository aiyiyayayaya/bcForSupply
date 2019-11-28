package com.mindata.blockchain.SupplyChainManagement.service;


import com.mindata.blockchain.core.entity.SC.User;

/**
 * Created by aiya on 2019/3/20 上午9:39
 */
public interface UserService {
    User getUserById(String userId);
    boolean login(String usrid, String psw);
    boolean addUser(User user);

}
