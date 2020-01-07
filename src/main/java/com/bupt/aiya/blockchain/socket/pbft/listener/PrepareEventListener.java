package com.bupt.aiya.blockchain.socket.pbft.listener;

import javax.annotation.Resource;

import com.bupt.aiya.blockchain.socket.body.PbftVoteBody;
import com.bupt.aiya.blockchain.socket.client.PacketSender;
import com.bupt.aiya.blockchain.socket.packet.HelloPacket;
import com.bupt.aiya.blockchain.socket.packet.PacketBuilder;
import com.bupt.aiya.blockchain.socket.packet.PacketType;
import com.bupt.aiya.blockchain.socket.pbft.event.MsgPrepareEvent;
import com.bupt.aiya.blockchain.socket.pbft.msg.VoteMsg;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author wuweifeng wrote on 2018/4/25.
 */
@Component
public class PrepareEventListener {
    @Resource
    private PacketSender packetSender;

    /**
     * block已经开始进入Prepare状态
     *
     * @param msgPrepareEvent
     *         msgIsPrepareEvent
     */
    @EventListener
    public void msgIsPrepare(MsgPrepareEvent msgPrepareEvent) {
        VoteMsg voteMsg = (VoteMsg) msgPrepareEvent.getSource();

        //群发消息，通知别的节点，我已对该Block Prepare
        HelloPacket helloPacket = new PacketBuilder<>().setType(PacketType.PBFT_VOTE).setBody(new
                PbftVoteBody(voteMsg)).build();

        //广播给所有人我已Prepare
        packetSender.sendGroup(helloPacket);
    }
}
