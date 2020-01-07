package com.bupt.aiya.blockchain.socket.handler.client;

import com.bupt.aiya.blockchain.ApplicationContextProvider;
import com.bupt.aiya.blockchain.core.entity.BC.Block.Block;
import com.bupt.aiya.blockchain.core.entity.BC.Block.Check.CheckManager;
import com.bupt.aiya.blockchain.core.event.AddBlockEvent;
import com.bupt.aiya.blockchain.socket.body.RpcBlockBody;
import com.bupt.aiya.blockchain.socket.body.RpcCheckBlockBody;
import com.bupt.aiya.blockchain.socket.client.PacketSender;
import com.bupt.aiya.blockchain.socket.handler.abs.AbsBlockHandler;
import com.bupt.aiya.blockchain.socket.packet.HelloPacket;
import com.bupt.aiya.blockchain.socket.packet.NextBlockPacketBuilder;
import com.bupt.aiya.blockchain.socket.pbft.queue.NextBlockQueue;
import org.tio.core.ChannelContext;

/**
 * Created by aiya on 2019/2/26 下午3:00
 */
public class GetBlockRespHandler extends AbsBlockHandler<RpcBlockBody> {
    @Override
    public Class<RpcBlockBody> bodyClass() {
        return null;
    }

    @Override
    public Object handler(HelloPacket packet, RpcBlockBody baseBody, ChannelContext channelContext) throws Exception {
        Block block = baseBody.getBlock();
        if (block == null)
            System.out.println("他也没有这个区块诶～");
        else{
            //校验区块的合法性
            if (ApplicationContextProvider.getBean(NextBlockQueue.class).pop(block.getHash()) == null)
                return null;
            CheckManager checkManager = ApplicationContextProvider.getBean(CheckManager.class);
            RpcCheckBlockBody rpcCheckBlockBody = checkManager.check(block);

            //校验通过，就存入本地DB
            if (rpcCheckBlockBody.getCode() == 0){

                ApplicationContextProvider.publishEvent(new AddBlockEvent(block));

                //继续请求下一块
                HelloPacket helloPacket = NextBlockPacketBuilder.build();
                ApplicationContextProvider.getBean(PacketSender.class).send2Group(helloPacket);
            }
        }
        return null;
    }
}
