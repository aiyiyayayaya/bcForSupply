package com.bupt.aiya.blockchain.socket.packet;

import com.bupt.aiya.blockchain.ApplicationContextProvider;
import com.bupt.aiya.blockchain.core.event.ClientRequestEvent;
import com.bupt.aiya.blockchain.core.manager.DbBlockManager;
import com.bupt.aiya.blockchain.socket.body.RpcSimpleBlockBody;


/**
 * 构建向别的节点请求next block的builder.带着自己最后一个block的hash
 *
 */
public class NextBlockPacketBuilder {
    public static HelloPacket build() {
        return build(null);
    }

    public static HelloPacket build(String responseId) {
        String hash = ApplicationContextProvider.getBean(DbBlockManager.class).getLastBlockHash();

        RpcSimpleBlockBody rpcBlockBody = new RpcSimpleBlockBody(hash);
        rpcBlockBody.setResponseMsgId(responseId);
        HelloPacket helloPacket = new PacketBuilder<>().setType(PacketType.NEXT_BLOCK_INFO_REQUEST).setBody
                (rpcBlockBody).build();
        //发布client请求事件
        ApplicationContextProvider.publishEvent(new ClientRequestEvent(helloPacket));
        return helloPacket;
    }

}
