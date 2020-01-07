package com.bupt.aiya.blockchain.socket.handler.server;

import com.bupt.aiya.blockchain.ApplicationContextProvider;
import com.bupt.aiya.blockchain.socket.base.AbstractBlockHandler;
import com.bupt.aiya.blockchain.socket.body.PbftVoteBody;
import com.bupt.aiya.blockchain.socket.packet.HelloPacket;
import com.bupt.aiya.blockchain.socket.pbft.msg.VoteMsg;
import com.bupt.aiya.blockchain.socket.pbft.queue.MsgQueueManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;

/**
 * pbft投票处理
 *
 * @author wuweifeng wrote on 2018/3/12.
 */
public class PbftVoteHandler extends AbstractBlockHandler<PbftVoteBody> {
    private Logger logger = LoggerFactory.getLogger(PbftVoteHandler.class);


    @Override
    public Class<PbftVoteBody> bodyClass() {
        return PbftVoteBody.class;
    }

    @Override
    public Object handler(HelloPacket packet, PbftVoteBody pbftVoteBody, ChannelContext channelContext) {
        VoteMsg voteMsg = pbftVoteBody.getVoteMsg();
        logger.info("收到来自于<" + voteMsg.getSendId() + "><投票>消息，投票信息为[" + voteMsg + "]");

        ApplicationContextProvider.getBean(MsgQueueManager.class).pushMsg(voteMsg);
        return null;
    }
}
