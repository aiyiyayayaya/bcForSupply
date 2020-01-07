package com.bupt.aiya.blockchain.socket.handler.server;

import com.bupt.aiya.blockchain.ApplicationContextProvider;
import com.bupt.aiya.blockchain.User.Myself;
import com.bupt.aiya.blockchain.core.entity.BC.Block.Block;
import com.bupt.aiya.blockchain.socket.base.AbstractBlockHandler;
import com.bupt.aiya.blockchain.socket.body.RpcBlockBody;
import com.bupt.aiya.blockchain.socket.message.CommonTypes;
import com.bupt.aiya.blockchain.socket.packet.HelloPacket;
import com.bupt.aiya.blockchain.socket.pbft.msg.PrePrepareMsg;
import com.bupt.aiya.blockchain.socket.pbft.queue.MsgQueueManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;

/**
 * 收到请求生成区块消息，进入PrePre队列
 *
 * @author wuweifeng wrote on 2018/3/12.
 */
public class GenerateBlockRequestHandler extends AbstractBlockHandler<RpcBlockBody> {
    private Logger logger = LoggerFactory.getLogger(GenerateBlockRequestHandler.class);


    @Override
    public Class<RpcBlockBody> bodyClass() {
        return RpcBlockBody.class;
    }

    @Override
    public Object handler(HelloPacket packet, RpcBlockBody rpcBlockBody, ChannelContext channelContext) {
        Block block = rpcBlockBody.getBlock();
        logger.info("receive message from.., the message is request generate block message,block message is like:\n["+block+"]");
        //应该先检查一下的
//        CheckManager checkManager = ApplicationContextProvider.getContext().getBean(CheckManager.class);
        //对区块的基本信息进行校验，校验通过后进入pbft的Pre队列
//logger.info("校验结果:" + rpcCheckBlockBody.toString());
//        if (rpcCheckBlockBody.getCode() == 0) {
//        //对区块的基本信息进行校验，
//        // 校验通过后进入pbft的Pre队列
//        RpcCheckBlockBody rpcCheckBlockBody = checkManager.check(block);
//        logger.info("the result of check:---"+rpcCheckBlockBody.toString());
//        if (rpcCheckBlockBody.getCode() == 0){
        PrePrepareMsg prePrepareMsg = new PrePrepareMsg();
        prePrepareMsg.setType(CommonTypes.PRE_PREPARE);
        prePrepareMsg.setBlock(block);
        prePrepareMsg.setSequence_no(block.getBlockHeader().getBlockNumber());
        prePrepareMsg.setSendId(Myself.usr_Id);
        prePrepareMsg.setHash(block.getHash());
        prePrepareMsg.setResult(true);
        //将消息推入PrePrepare队列
        ApplicationContextProvider.getBean(MsgQueueManager.class).pushMsg(prePrepareMsg);
        //}
        return null;
    }
}
