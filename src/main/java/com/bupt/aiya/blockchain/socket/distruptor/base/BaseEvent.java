package com.bupt.aiya.blockchain.socket.distruptor.base;

import com.bupt.aiya.blockchain.socket.packet.HelloPacket;

import org.tio.core.ChannelContext;

import java.io.Serializable;

/**
 * 生产、消费者之间传递消息用的event
 *
 * @author wuweifeng wrote on 2018/4/20.
 */
public class BaseEvent implements Serializable {
    private HelloPacket helloPacket;
    private ChannelContext channelContext;

    public BaseEvent(HelloPacket helloPacket, ChannelContext channelContext) {
        this.helloPacket = helloPacket;
        this.channelContext = channelContext;
    }

    public BaseEvent(HelloPacket helloPacket) {
        this.helloPacket = helloPacket;
    }

    public BaseEvent() {
    }

    public ChannelContext getChannelContext() {
        return channelContext;
    }

    public void setChannelContext(ChannelContext channelContext) {
        this.channelContext = channelContext;
    }

    public HelloPacket getHelloPacket() {
        return helloPacket;
    }

    public void setHelloPacket(HelloPacket helloPacket) {
        this.helloPacket = helloPacket;
    }
}
