package com.mindata.blockchain.socket.client;

import com.mindata.blockchain.ApplicationContextProvider;
import com.mindata.blockchain.core.event.ClientRequestEvent;
import com.mindata.blockchain.socket.packet.HelloPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tio.client.ClientGroupContext;
import org.tio.core.Aio;

import javax.annotation.Resource;

import static com.mindata.blockchain.socket.common.Const.GROUP_NAME;

/**
 * 发送消息的工具类
 * @author aiya wrote on 2018/11/14.
 */
@Component
public class PacketSender {
    private static Logger logger = LoggerFactory.getLogger(PacketSender.class);
    @Resource
    private ClientGroupContext clientGroupContext;

    public void send2Group(HelloPacket helloPacket){
        //对外发出client请求事件
        ApplicationContextProvider.publishEvent(new ClientRequestEvent(helloPacket));
        //发送给group成员
        Aio.sendToAll(clientGroupContext,helloPacket);
        System.out.println("send2all");
    }

    public void send2Someone(HelloPacket helloPacket, String ip, int port){
        //对外发出client请求事件
        logger.info("sent to "+ip+":"+port);
        ApplicationContextProvider.publishEvent(new ClientRequestEvent(helloPacket));
        Aio.sendToIp(clientGroupContext, ip, helloPacket);
//        boolean flag = Tio.send(clientGroupContext, ip, port,helloPacket);
//        logger.info("flag = " + flag);
    }

    public void sendGroup(HelloPacket helloPacket) {
        //对外发出client请求事件
        ApplicationContextProvider.publishEvent(new ClientRequestEvent(helloPacket));
        //发送到一个group
        Aio.sendToGroup(clientGroupContext, GROUP_NAME, helloPacket);
    }

}
