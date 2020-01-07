package com.bupt.aiya.blockchain.common;

/**
 * Created by aiya on 2019/11/27 上午11:10
 */
public enum  LeaderConstant {
    LEADER_IP("172.23.18.29", 6789);

    private String leaderIp;
    private int socket;

    LeaderConstant(String leaderIp, int socket) {
        this.leaderIp = leaderIp;
        this.socket = socket;
    }

    public String getLeaderIp() {
        return leaderIp;
    }

    public void setLeaderIp(String leaderIp) {
        this.leaderIp = leaderIp;
    }

    public int getSocket() {
        return socket;
    }

    public void setSocket(int socket) {
        this.socket = socket;
    }
}
