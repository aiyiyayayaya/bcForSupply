package com.bupt.aiya.blockchain.core.event;

import com.bupt.aiya.blockchain.socket.packet.HelloPacket;
import org.springframework.context.ApplicationEvent;

/**
 * 客户端对外发请求时会触发该Event
 * @author wuweifeng wrote on 2018/3/17.
 */
public class ClientRequestEvent extends ApplicationEvent {
    public ClientRequestEvent(HelloPacket helloPacket) {
        super(helloPacket);
    }
}
