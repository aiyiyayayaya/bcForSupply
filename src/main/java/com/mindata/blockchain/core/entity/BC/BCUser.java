package com.mindata.blockchain.core.entity.BC;

/**
 * Created by aiya on 2019/3/25 下午7:32
 */
public class BCUser {
    private int userId;
    private String userName;
    private String userMac;
    private String userIp;
    private String userSocket = "6789";
    private String publicKey;
    private String privateKey;

    private float score;

    public BCUser(){

    }
    public BCUser(int userId, String userName, String userMac, String userIp, String userSocket, String publicKey, String privateKey, float score) {
        this.userId = userId;
        this.userName = userName;
        this.userMac = userMac;
        this.userIp = userIp;
        this.userSocket = userSocket;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.score = score;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMac() {
        return userMac;
    }

    public void setUserMac(String userMac) {
        this.userMac = userMac;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getUserSocket() {
        return userSocket;
    }

    public void setUserSocket(String userSocket) {
        this.userSocket = userSocket;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
