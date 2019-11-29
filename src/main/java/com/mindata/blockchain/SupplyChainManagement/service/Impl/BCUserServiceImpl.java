package com.mindata.blockchain.SupplyChainManagement.service.Impl;


import com.mindata.blockchain.SupplyChainManagement.service.BCUserService;
import com.mindata.blockchain.User.User;
import com.mindata.blockchain.Util.MacAndIP;
import com.mindata.blockchain.Util.Result;
import com.mindata.blockchain.Util.ResultUtil;
import com.mindata.blockchain.common.AppId;
import com.mindata.blockchain.common.CommonUtil;
import com.mindata.blockchain.core.dao.BC.BCUserDao;
import com.mindata.blockchain.core.dao.BC.MemberDao;
import com.mindata.blockchain.core.entity.BC.BCUser;
import com.mindata.blockchain.socket.body.JoinGroupReqBody;
import com.mindata.blockchain.socket.client.ClientStarter;
import com.mindata.blockchain.socket.client.PacketSender;
import com.mindata.blockchain.socket.message.MessageType;
import com.mindata.blockchain.socket.packet.HelloPacket;
import com.mindata.blockchain.socket.packet.PacketBuilder;
import com.mindata.blockchain.socket.packet.PacketType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tio.client.AioClient;
import org.tio.client.ClientGroupContext;
import org.tio.core.Node;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aiya on 2019/3/22 上午11:20
 */
@Service
public class BCUserServiceImpl implements BCUserService {
    @Resource
    private ClientGroupContext clientGroupContext;

    @Autowired
    private ClientStarter helloClientStarter;

    @Autowired
    private BCUserDao bcUserDao;

    @Autowired
    private MemberDao memberDao;

    @Resource
    private PacketSender packetSender;
    private static Logger logger = LoggerFactory.getLogger(BCUserServiceImpl.class);

    @Override
    public List<BCUser> getIpList() {
        ArrayList<BCUser> list;
        list = new ArrayList<BCUser>(bcUserDao.selectAll());//new ArrayList<String>(bcUserDao.selectUserIpList());
        return list;
    }

    //注册新节点
    @Override
    public Result addBCUser(int id, String name) {
        BCUser bcUser;
        String mac = MacAndIP.getMacAddress();
        String ip = MacAndIP.getIpAddress();
        String ans;
        try{
            System.out.println("mac = " + mac);
            bcUser = bcUserDao.selectUserByMac(mac);
            ans = bcUser.getPublicKey();
            System.out.println("ans = " + ans);
            //return "already in the blockchain!";
        }catch (NullPointerException e) {
            bcUser = null;
        }
        if (bcUser == null) {
            User myself = new User();
            bcUser = new BCUser(id, name, mac, ip, "6789",
                    User.Pub2String(myself.getPublicKey()), User.Priv2String(myself.getPrivateKey()), (float)0.8);
            bcUserDao.newUser(bcUser);
            return ResultUtil.success(User.Pub2String(myself.getPublicKey()));
        }
        return ResultUtil.error(-1, "already in the blockchain!");
    }


    @Override
    public BCUser getUserByMac(String mac) {
        BCUser bcUser;
        try {
            bcUser = bcUserDao.selectUserByMac(mac);
        }catch (NullPointerException e){
            bcUser = null;
        }
        return bcUser;
    }


    /***
     * 申请逻辑
     */
    @Override
    public String doApply() {
        String mymac = MacAndIP.getMacAddress();
        BCUser user = getUserByMac(mymac);
        //去掉私钥
        user.setPrivateKey(null);
        HelloPacket helloPacket = new PacketBuilder<>().setType(PacketType.APPLY_JOIN_REQUEST).setBody(new JoinGroupReqBody(user)).build();
        packetSender.send2Group(helloPacket);
        return "success";
    }

    /**
     * 新节点加入区块链
     * 1. 查出来节点信息
     * 2. 广播joinrequest
     * 3. 把这条记录删掉
     * */
    @Override
    public String joinToBlockChain(int bcId) {
        BCUser bcUser = bcUserDao.selectWaitById(bcId);

        HelloPacket helloPacket = new PacketBuilder<>().setType(PacketType.JOIN_BLOCKCHAIN_REQUEST).setBody(new JoinGroupReqBody(bcUser)).build();
        if (bcUser != null) {
            logger.info("发送给节点广播" + helloPacket.toString());
            //2. 发送给区块链成员帮我广播，加入区块链
            try {
                packetSender.sendGroup(helloPacket);
            } catch (Exception e) {
                logger.error("发送消息出错，主键:{}, 包:{}", bcId, helloPacket.toString(), e);
            }

            return "success";
        }
        return "error...there is no user...";
    }

    /**
     * 获取等待加入区块链的节点列表进行审批
     * */
    @Override
    public List<BCUser> getWaitingList() {
        ArrayList<BCUser> list;
        list = new ArrayList<BCUser>(bcUserDao.getWaitingNode());
        return list;
    }

    @Override
    public String deleteBCUser(String mac) {
        BCUser bcUser= bcUserDao.selectUserByMac(mac);
        if (bcUser != null) {
            HelloPacket helloPacket = new PacketBuilder<>().setType(PacketType.QUIT_BLOCKCHAIN_REQUEST).setBody(new JoinGroupReqBody(bcUser)).build();
            packetSender.sendGroup(helloPacket);
        }
        return "请等待管理节点处理";
    }
}
