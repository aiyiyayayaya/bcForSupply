package com.bupt.aiya.blockchain.socket.handler.server;

import com.bupt.aiya.blockchain.ApplicationContextProvider;
import com.bupt.aiya.blockchain.core.dao.BC.BCUserDao;
import com.bupt.aiya.blockchain.core.dao.BC.BlockDao;
import com.bupt.aiya.blockchain.core.entity.BC.BCUser;
import com.bupt.aiya.blockchain.core.entity.BC.Block.BlockHeader;
import com.bupt.aiya.blockchain.socket.base.AbstractBlockHandler;
import com.bupt.aiya.blockchain.socket.body.DeleteReqBody;
import com.bupt.aiya.blockchain.socket.body.DeleteRespBody;
import com.bupt.aiya.blockchain.socket.client.PacketSender;
import com.bupt.aiya.blockchain.socket.packet.HelloPacket;
import com.bupt.aiya.blockchain.socket.packet.PacketBuilder;
import com.bupt.aiya.blockchain.socket.packet.PacketType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.tio.core.ChannelContext;

import java.util.*;

/**
 * Created by aiya on 2019/11/27 上午11:55
 */
public class DeleteBlockRequstHandler extends AbstractBlockHandler<DeleteReqBody> {
    private Logger logger = LoggerFactory.getLogger(DeleteBlockRequstHandler.class);

    @Autowired
    private BCUserDao bcUserDao;

    @Autowired
    private BlockDao blockDao;

    @Override
    public Class<DeleteReqBody> bodyClass() {
        return DeleteReqBody.class;
    }

    @Override
    public Object handler(HelloPacket packet, DeleteReqBody bsBody, ChannelContext channelContext) throws Exception {
        int blockNum = bsBody.getBlockNum();
        String hash = bsBody.getBlockHash();
        logger.info("收到来自于<" + bsBody.getAppId() + "><请求删除区块的请求信息>, blockNum = [" + blockNum + "], blockHash = [" + hash + "]");
        BlockHeader tmp = blockDao.getByNum(blockNum);
        //1. 计算有效率
        float per = (tmp.getMaxPeriod() - tmp.getTimeStamp()) / (System.currentTimeMillis() - tmp.getTimeStamp());

        // 计算出存储信息的节点 -- 按score降序
        List<BCUser> list = bcUserDao.selectAll();
        int totalIp = list.size();

        //2. 计算出信息副本数量
        List<String> res = new ArrayList<>();
        int should = (int)(totalIp * per) % 1 == 1 ? (int)(totalIp * per) : (int)(totalIp * per) - 1;
        if (should > list.size()){
            logger.info("目前网络中的节点数量不足，不允许清除区块详情");
            for (BCUser usr : list){
                res.add(usr.getUserIp());
            }
        }else {
            //取出总list中2*should的数量
            int sub = 2 * should > list.size() ? list.size() : 2 * should;
            list = list.subList(0, sub);
            //随机选出should个IP
            Set<String> ipSet = new HashSet<>();
            Random random = new Random(list.size());
            while (ipSet.size() < should) {
                int ran = random.nextInt();
                ipSet.add(list.get(ran).getUserIp());
            }
            for(String ip : ipSet){
                res.add(ip);
            }
        }
        DeleteRespBody body = new DeleteRespBody();
        body.setBlockNum(blockNum);
        body.setIpList(res);
        HelloPacket helloPacket = new PacketBuilder<>().setType(PacketType.DELETE_BLOCKBODY_INFO_RESPONSE).setBody(body).build();
        ApplicationContextProvider.getBean(PacketSender.class).sendGroup(helloPacket);
        //可以清除：
        return null;
    }
}
