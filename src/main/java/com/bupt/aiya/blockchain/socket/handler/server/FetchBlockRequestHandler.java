package com.bupt.aiya.blockchain.socket.handler.server;

import com.bupt.aiya.blockchain.ApplicationContextProvider;
import com.bupt.aiya.blockchain.common.AppId;
import com.bupt.aiya.blockchain.core.entity.BC.Block.Block;
import com.bupt.aiya.blockchain.core.manager.DbBlockManager;
import com.bupt.aiya.blockchain.socket.base.AbstractBlockHandler;
import com.bupt.aiya.blockchain.socket.body.RpcBlockBody;
import com.bupt.aiya.blockchain.socket.body.RpcSimpleBlockBody;
import com.bupt.aiya.blockchain.socket.packet.HelloPacket;
import com.bupt.aiya.blockchain.socket.packet.PacketBuilder;
import com.bupt.aiya.blockchain.socket.packet.PacketType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;

/**
 * 请求别人某个区块的信息
 * @author wuweifeng wrote on 2018/3/12.
 */
public class FetchBlockRequestHandler extends AbstractBlockHandler<RpcSimpleBlockBody> {
    private Logger logger = LoggerFactory.getLogger(FetchBlockRequestHandler.class);

    @Override
    public Class<RpcSimpleBlockBody> bodyClass() {
        return RpcSimpleBlockBody.class;
    }

    @Override
    public Object handler(HelloPacket packet, RpcSimpleBlockBody rpcBlockBody, ChannelContext channelContext) {
        logger.info("收到来自于<" + rpcBlockBody.getAppId() + "><请求该Block>消息，block hash为[" + rpcBlockBody.getHash() + "]");
        if (!rpcBlockBody.getAppId().equals(AppId.value)) {
            Block block = ApplicationContextProvider.getBean(DbBlockManager.class).getBlockByHash(rpcBlockBody.getHash());

            HelloPacket helloPacket = new PacketBuilder<>().setType(PacketType.FETCH_BLOCK_INFO_RESPONSE).setBody(new
                    RpcBlockBody(block)).build();
            Aio.send(channelContext, helloPacket);
        }

        return null;
    }
}
