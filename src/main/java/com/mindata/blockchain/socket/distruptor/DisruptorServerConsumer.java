package com.mindata.blockchain.socket.distruptor;

import com.mindata.blockchain.socket.distruptor.base.BaseEvent;
import com.mindata.blockchain.socket.distruptor.base.MessageConsumer;
import com.mindata.blockchain.socket.base.AbstractBlockHandler;
import com.mindata.blockchain.socket.handler.server.*;
import com.mindata.blockchain.socket.packet.HelloPacket;
import com.mindata.blockchain.socket.packet.PacketType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 所有client发来的消息都在这里处理
 * @author wuweifeng wrote on 2018/4/20.
 */
@Component
public class DisruptorServerConsumer implements MessageConsumer {

    private static Map<Byte, AbstractBlockHandler<?>> handlerMap = new HashMap<>();

    static {
        handlerMap.put(PacketType.GENERATE_COMPLETE_REQUEST, new GenerateCompleteRequestHandler());
        handlerMap.put(PacketType.GENERATE_BLOCK_REQUEST, new GenerateBlockRequestHandler());//ok
        handlerMap.put(PacketType.TOTAL_BLOCK_INFO_REQUEST, new TotalBlockInfoRequestHandler());
        handlerMap.put(PacketType.FETCH_BLOCK_INFO_REQUEST, new FetchBlockRequestHandler());
        handlerMap.put(PacketType.HEART_BEAT, new HeartBeatHandler());
        handlerMap.put(PacketType.NEXT_BLOCK_INFO_REQUEST, new NextBlockRequestHandler());
        handlerMap.put(PacketType.PBFT_VOTE, new PbftVoteHandler());
        handlerMap.put(PacketType.DELETE_BLOCKBODY_INFO_REQUEST, new DeleteBlockRequstHandler());
    }

    @Override
    public void receive(BaseEvent baseEvent) throws Exception {
        HelloPacket helloPacket = baseEvent.getHelloPacket();
        Byte type = helloPacket.getType();
        AbstractBlockHandler<?> handler = handlerMap.get(type);
        if (handler == null) {
            return;
        }
        handler.handler(helloPacket, baseEvent.getChannelContext());
    }
}
