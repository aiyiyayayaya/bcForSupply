package com.bupt.aiya.blockchain.socket.distruptor;

import cn.hutool.core.util.StrUtil;
import com.bupt.aiya.blockchain.common.AppId;
import com.bupt.aiya.blockchain.socket.base.AbstractBlockHandler;
import com.bupt.aiya.blockchain.socket.body.BaseBody;
import com.bupt.aiya.blockchain.socket.distruptor.base.BaseEvent;
import com.bupt.aiya.blockchain.socket.distruptor.base.MessageConsumer;
import com.bupt.aiya.blockchain.socket.handler.client.DeleteBlockResponseHandler;
import com.bupt.aiya.blockchain.socket.handler.client.FetchBlockResponseHandler;
import com.bupt.aiya.blockchain.socket.handler.client.NextBlockResponseHandler;
import com.bupt.aiya.blockchain.socket.handler.client.TotalBlockInfoResponseHandler;
import com.bupt.aiya.blockchain.socket.packet.HelloPacket;
import com.bupt.aiya.blockchain.socket.packet.PacketType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tio.utils.json.Json;

import java.util.HashMap;
import java.util.Map;

/**
 * 所有server发来的消息都在这里处理
 * @author wuweifeng wrote on 2018/4/20.
 */
@Component
public class DisruptorClientConsumer implements MessageConsumer {
    private static Map<Byte, AbstractBlockHandler<?>> handlerMap = new HashMap<>();
    private Logger logger = LoggerFactory.getLogger(getClass());

    static {
        handlerMap.put(PacketType.TOTAL_BLOCK_INFO_RESPONSE, new TotalBlockInfoResponseHandler());
        handlerMap.put(PacketType.NEXT_BLOCK_INFO_RESPONSE, new NextBlockResponseHandler());
        handlerMap.put(PacketType.FETCH_BLOCK_INFO_RESPONSE, new FetchBlockResponseHandler());
        handlerMap.put(PacketType.DELETE_BLOCKBODY_INFO_RESPONSE, new DeleteBlockResponseHandler());
    }

    @Override
    public void receive(BaseEvent baseEvent) throws Exception {
        HelloPacket helloPacket = baseEvent.getHelloPacket();
        Byte type = helloPacket.getType();
        AbstractBlockHandler<?> blockHandler = handlerMap.get(type);
        if (blockHandler == null) {
            return;
        }

        //消费消息
        BaseBody baseBody = Json.toBean(new String(helloPacket.getBody()), BaseBody.class);
        //logger.info("收到来自于<" + baseBody.getAppId() + ">针对msg<" + baseBody.getResponseMsgId() + ">的回应");

        String appId = baseBody.getAppId();
        if (StrUtil.equals(AppId.value, appId)) {
            //是本机
            //return;
        }

        blockHandler.handler(helloPacket, baseEvent.getChannelContext());
    }
}
