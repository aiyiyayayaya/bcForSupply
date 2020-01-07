package com.bupt.aiya.blockchain.socket.pbft.listener;

import com.bupt.aiya.blockchain.socket.body.PbftVoteBody;
import com.bupt.aiya.blockchain.socket.client.PacketSender;
import com.bupt.aiya.blockchain.socket.packet.HelloPacket;
import com.bupt.aiya.blockchain.socket.packet.PacketBuilder;
import com.bupt.aiya.blockchain.socket.packet.PacketType;
import com.bupt.aiya.blockchain.socket.pbft.event.MsgCommitEvent;
import com.bupt.aiya.blockchain.socket.pbft.msg.VoteMsg;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 监听block可以commit消息
 * @author wuweifeng wrote on 2018/4/25.
 */
@Component
public class CommitEventListener {
    @Resource
    private PacketSender packetSender;

    /**
     * block已经开始进入commit状态，广播消息
     *
     * @param msgCommitEvent
     *         msgCommitEvent
     */
    @EventListener
    public void msgIsCommit(MsgCommitEvent msgCommitEvent) {
        VoteMsg voteMsg = (VoteMsg) msgCommitEvent.getSource();

        //群发消息，通知所有节点，我已对该Block Prepare
        HelloPacket helloPacket = new PacketBuilder<>().setType(PacketType.PBFT_VOTE).setBody(new
                PbftVoteBody(voteMsg)).build();

        //广播给所有人我已commit
        packetSender.sendGroup(helloPacket);
    }
}
