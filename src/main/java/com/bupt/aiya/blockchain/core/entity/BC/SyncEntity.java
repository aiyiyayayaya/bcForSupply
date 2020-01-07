package com.bupt.aiya.blockchain.core.entity.BC;

/**
 * Created by aiya on 2019/3/18 下午2:43
 */
public class SyncEntity {
    private Long id;

    //the hash of block which have been synced
    private String hash;

    //create time
    private Long createTime = System.currentTimeMillis();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
