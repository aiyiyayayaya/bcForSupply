package com.bupt.aiya.blockchain.socket.handler.client;

import com.bupt.aiya.blockchain.ApplicationContextProvider;
import com.bupt.aiya.blockchain.socket.base.AbstractBlockHandler;
import com.bupt.aiya.blockchain.socket.body.BlockHash;
import com.bupt.aiya.blockchain.socket.body.RpcNextBlockBody;
import com.bupt.aiya.blockchain.socket.packet.HelloPacket;
import com.bupt.aiya.blockchain.socket.pbft.queue.NextBlockQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;

/**
 * 对方根据我们传的hash，给我们返回的next block
 *
 * @author aiya wrote on 2019/3/16.
 */
public class NextBlockResponseHandler extends AbstractBlockHandler<RpcNextBlockBody> {
    private Logger logger = LoggerFactory.getLogger(TotalBlockInfoResponseHandler.class);

    @Override
    public Class<RpcNextBlockBody> bodyClass() {
        return RpcNextBlockBody.class;
    }

    @Override
    public Object handler(HelloPacket packet, RpcNextBlockBody rpcBlockBody, ChannelContext channelContext) {
        logger.info("收到来自于<" + rpcBlockBody.getAppId() + ">的回复，下一个Block hash为：" + rpcBlockBody.getHash());

        String hash = rpcBlockBody.getHash();
        //如果为null，说明对方根据我们传过去的hash，找不到next block。说明要么已经是最新了，要么对方的block比自己的少
        if (hash == null) {
            logger.info("和<" + rpcBlockBody.getAppId() + ">相比，本地已是最新块了");
        } else {
            BlockHash blockHash = new BlockHash(hash, rpcBlockBody.getPrevHash(), rpcBlockBody.getAppId());
            //此处进行搜集next block的hash，相同的hash过2f+1时可以确认
            ApplicationContextProvider.getBean(NextBlockQueue.class).push(blockHash);
        }

        return null;
    }
}
