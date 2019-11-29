package com.mindata.blockchain.socket.packet;

/**
 * packetType大于0时是请求类型，小于0时为响应类型
 * @author wuweifeng wrote on 2018/3/9.
 */
public interface PacketType {
    /**
     * 心跳包
     */
    byte HEART_BEAT = 0;
    /**
     * 已生成新的区块请求/回应
     */
    byte GENERATE_COMPLETE_REQUEST = 1;
    byte GENERATE_COMPLETE_RESPONSE = -1;

    /**
     * 请求生成block
     * 同意、拒绝生成
     */
    byte GENERATE_BLOCK_REQUEST = 2;
    byte GENERATE_BLOCK_RESPONSE = -2;

    /**
     * 获取所有block信息
     * 我的所有块信息
     */
    byte TOTAL_BLOCK_INFO_REQUEST = 3;
    byte TOTAL_BLOCK_INFO_RESPONSE = -3;
    /**
     * 获取一个block信息
     * 获取一块信息响应
     */
    byte FETCH_BLOCK_INFO_REQUEST = 4;
    byte FETCH_BLOCK_INFO_RESPONSE = -4;
    /**
     * 获取下一个区块的信息
     */
    byte NEXT_BLOCK_INFO_REQUEST = 5;
    byte NEXT_BLOCK_INFO_RESPONSE = -5;


    /**
     * delete detail message info
     * */
    byte DELETE_BLOCKBODY_INFO_REQUEST = 6;
    byte DELETE_BLOCKBODY_INFO_RESPONSE = -6;

    /**
     * 新节点申请加入区块链
     * */
    byte APPLY_JOIN_REQUEST = 7;

    /**
     * quit
     * */
    byte QUIT_BLOCKCHAIN_REQUEST = -7;

    /**
     * new node join in the block chain
     * */
    byte JOIN_BLOCKCHAIN_REQUEST = 8;
    byte JOIN_BLOCKCHAIN_RESPONSE = -8;

    /**
     * pbft投票
     */
    byte PBFT_VOTE = 10;
}
