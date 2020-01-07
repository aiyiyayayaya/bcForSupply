package com.bupt.aiya.blockchain.socket.body;

/**
 * @author aiya wrote on 2018/4/26.
 */
public class BlockHash {
    private String hash;
    private String prevHash;

    public BlockHash() {
    }

    public BlockHash(String hash, String prevHash, String appId) {
        this.hash = hash;
        this.prevHash = prevHash;
    }

    public String getPrevHash() {
        return prevHash;
    }

    public void setPrevHash(String prevHash) {
        this.prevHash = prevHash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
