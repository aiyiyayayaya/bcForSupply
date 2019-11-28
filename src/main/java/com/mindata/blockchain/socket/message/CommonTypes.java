package com.mindata.blockchain.socket.message;

/**
 * Created by aiya on 2019/1/4 下午1:56
 */
public class CommonTypes {
    public static final int LEADER_CHANGE = -2;
    //request the view
    public static final int VIEW = -1;
    //request the data
    public static final int REQ = 0;

    public static final int PRE_PREPARE = 1;
    public static final int PREPARE = 2;
    public static final int COMMIT = 3;
    public static final int REPLY = 4;
}
