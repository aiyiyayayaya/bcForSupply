package com.bupt.aiya.blockchain.socket.pbft.msg;

import com.bupt.aiya.blockchain.core.entity.BC.Block.Block;

/**
 * @author wuweifeng wrote on 2018/4/25.
 */
public class PrePrepareMsg extends VoteMsg {
    /**
     * Create the basic type of message
     *
     * 消息结构：
     * <PREPREPARE,v,sequence_no,message,id>；
     * v：视图编号
     * message：request消息
     * ID：节点ID
     */

    private Block block;

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }
}
