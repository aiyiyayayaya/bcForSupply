package com.bupt.aiya.blockchain.socket.handler.server;

import com.bupt.aiya.blockchain.socket.base.AbstractBlockHandler;
import com.bupt.aiya.blockchain.socket.body.HeartBeatBody;
import com.bupt.aiya.blockchain.socket.packet.HelloPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;

/**
 * 客户端心跳包
 * @author wuweifeng wrote on 2018/3/12.
 */
@Deprecated
public class HeartBeatHandler extends AbstractBlockHandler<HeartBeatBody> {
    private Logger logger = LoggerFactory.getLogger(HeartBeatHandler.class);

    @Override
    public Class<HeartBeatBody> bodyClass() {
        return HeartBeatBody.class;
    }

    @Override
    public Object handler(HelloPacket packet, HeartBeatBody heartBeatBody, ChannelContext channelContext) throws Exception {
        logger.info("收到<心跳包>消息", heartBeatBody.getText());

        return null;
    }
}
