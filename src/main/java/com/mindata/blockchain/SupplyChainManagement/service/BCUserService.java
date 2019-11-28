package com.mindata.blockchain.SupplyChainManagement.service;

import com.mindata.blockchain.Util.Result;
import com.mindata.blockchain.core.entity.BC.BCUser;


import java.util.List;

/**
 * Created by aiya on 2019/3/20 上午9:41
 */

public interface BCUserService {
    //获取要连接的用户IP列表
    List<BCUser> getIpList();
    Result addBCUser(int id, String name);
    BCUser getUserByMac(String mac);
    String joinToBlockChain();
}
