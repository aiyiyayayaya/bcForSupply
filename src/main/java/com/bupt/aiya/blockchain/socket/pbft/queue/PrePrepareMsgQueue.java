package com.bupt.aiya.blockchain.socket.pbft.queue;

import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import com.bupt.aiya.blockchain.common.AppId;
import com.bupt.aiya.blockchain.common.timer.TimerManager;
import com.bupt.aiya.blockchain.core.entity.BC.Block.Block;
import com.bupt.aiya.blockchain.core.event.AddBlockEvent;
import com.bupt.aiya.blockchain.socket.pbft.VoteType;
import com.bupt.aiya.blockchain.socket.pbft.event.MsgPrepareEvent;
import com.bupt.aiya.blockchain.socket.pbft.msg.PrePrepareMsg;
import com.bupt.aiya.blockchain.socket.pbft.msg.VoteMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


import cn.hutool.core.bean.BeanUtil;

/**
 * Created by aiya on 2019/1/8 上午11:19
 * 凡收到的是PRE_PREPARE消息，都在这里处理
 */
@Component
public class PrePrepareMsgQueue extends BaseMsgQueue {
    @Resource
    private PrepareMsgQueue prepareMsgQueue;
    @Resource
    private ApplicationEventPublisher eventPublisher;

    private ConcurrentHashMap<String, PrePrepareMsg> blockConcurrentHashMap = new ConcurrentHashMap<>();

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void push(VoteMsg voteMsg) {
        //该队列里的是votePreMsg
        PrePrepareMsg prePrepareMsg = (PrePrepareMsg) voteMsg;
        String hash = prePrepareMsg.getHash();
        //避免收到重复消息
        if (blockConcurrentHashMap.get(hash) != null) {
            return;
        }
        //但凡是能进到该push方法的，都是通过基本校验的，但在并发情况下可能会相同number的block都进到投票队列中
        //需要对新进来的Vote信息的number进行校验，如果在比prepre阶段靠后的阶段中，已经出现了认证OK的同number的vote，则拒绝进入该队列
        if (prepareMsgQueue.otherConfirm(hash, voteMsg.getSequence_no())) {
            logger.info("拒绝进入Prepare阶段，hash为" + hash);
            return;
        }
        
        //存入Pre集合中
        blockConcurrentHashMap.put(hash, prePrepareMsg);

        //加入Prepare行列，推送给所有人
        VoteMsg prepareMsg = new VoteMsg();
        BeanUtil.copyProperties(voteMsg, prepareMsg);
        prepareMsg.setType(VoteType.PREPARE);
        prepareMsg.setSendId(AppId.value);
        eventPublisher.publishEvent(new MsgPrepareEvent(prepareMsg));
    }

    /**
     * 根据hash，得到内存中的Block信息
     *
     * @param hash
     *         hash
     * @return Block
     */
    public Block findByHash(String hash) {
        PrePrepareMsg prePrepareMsg = blockConcurrentHashMap.get(hash);
        if (prePrepareMsg != null) {
            return prePrepareMsg.getBlock();
        }
        return null;
    }

    /**
     * 新区块生成后，clear掉map中number比区块小的所有数据
     */
    @Order(3)
    @EventListener(AddBlockEvent.class)
    public void blockGenerated(AddBlockEvent addBlockEvent) {
        Block block = (Block) addBlockEvent.getSource();
        int number = block.getBlockHeader().getBlockNumber();
        TimerManager.schedule(() -> {
            for (String key : blockConcurrentHashMap.keySet()) {
                if (blockConcurrentHashMap.get(key).getSequence_no() <= number) {
                    blockConcurrentHashMap.remove(key);
                }
            }
            return null;
        },2000);
    }
}
