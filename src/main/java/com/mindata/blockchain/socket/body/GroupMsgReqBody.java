package com.mindata.blockchain.socket.body;

/**
 * Created by aiya on 2018/12/14 下午4:27
 */
public class GroupMsgReqBody extends BaseBody {
    private String text;
    //消息发送到哪个组
    private String toGroup;
}
