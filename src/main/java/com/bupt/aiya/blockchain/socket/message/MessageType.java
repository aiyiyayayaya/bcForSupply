package com.bupt.aiya.blockchain.socket.message;

/**
 * type>0:request
 * type<0:respond
 * */
public interface MessageType {
    //heart beat
    byte HEART_BEAT = 0;

    //建立连接请求
    byte CONNECTION_REQUEST = 1;
    byte JOIN_TO_NET = 11;
    byte CONNECTION_RESPOND = -1;

    //request generate new block
    byte GENERATE_BLOCK_REQUEST = 2;
    //respond agree or not to generate new block
    byte GENERATE_BLOCK_RESPOND = -2;

    //request to get a block message
    byte GET_BLOCK_INFO_REQUEST = 3;
    byte GET_BLOCK_INFO_RESPOND = -3;

    //get the next blcok info
    byte NEXT_BLOCK_INFO_REQUEST = 4;
    byte NEXT_BLOCK_INFO_RESPONSE = -4;

    //delete detail message info
    byte DELETE_BLOCKBODY_INFO_REQUEST = 5;
    byte DELETE_BLOCKBODY_INFO_RESPONSE = -5;

    //PBFT_VOTE
    byte COMMON = 6;

    //交易信息
    byte TRANSACTION = 8;
    //P2P message request
//    byte P2P_REQ = 4;
//    byte P2P_RESP = 5;

}
