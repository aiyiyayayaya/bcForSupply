package com.bupt.aiya.blockchain.SupplyChainManagement.service;

import com.bupt.aiya.blockchain.Util.Result;
import com.bupt.aiya.blockchain.core.entity.BC.BCUser;

import java.util.List;

/**
 * Created by aiya on 2019/3/20 上午9:41
 */

public interface BCUserService {
    //获取要连接的用户IP列表
    List<BCUser> getIpList();
    Result addBCUser(int id, String name);
    String doApply();
    BCUser getUserByMac(String mac);
    String joinToBlockChain(int bcId);
    List<BCUser> getWaitingList();
    String deleteBCUser(String mac);
}
