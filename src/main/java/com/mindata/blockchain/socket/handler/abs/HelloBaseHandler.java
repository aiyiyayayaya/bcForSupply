package com.mindata.blockchain.socket.handler.abs;

import com.mindata.blockchain.socket.packet.HelloPacket;
import org.tio.core.ChannelContext;

/**
 * Created by aiya on 2018/12/14 下午4:30
 * 业务处理接口
 */
public interface HelloBaseHandler {
    public Object handler(HelloPacket packet, ChannelContext channelContext) throws Exception;
}
