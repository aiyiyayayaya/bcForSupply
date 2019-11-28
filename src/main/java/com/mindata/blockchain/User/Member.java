package com.mindata.blockchain.User;

import java.util.Date;

/**
 * Created by aiya on 2019/1/11 下午4:49
 */
public class Member {
    private String id;
    private String name;
    private String ip;
    private String publicKey;
    private Date createTime;
    private Date updateTime;

    public String toString(){
        return "Member {\n"+
                "user_id = "+id+
                ",user_name = "+name+
                ",user_ip = "+ip+
                "\n}";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
