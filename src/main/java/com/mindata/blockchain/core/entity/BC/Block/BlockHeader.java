package com.mindata.blockchain.core.entity.BC.Block;

import java.util.List;

/**
 * Created by aiya on 2018/11/14.
 */
public class BlockHeader {

    //private long version;

    //这个当成是区块体的hash值好了
    private String myHash;

    private String preHash;
    //区块序号
    private int blockNumber;

    //todo 这个不应该有啊
    private String nextHash;

    //创建区块节点的身份
    private String pubKey;

    private String merkleRoot;
    private String bodySign;
    private String table;
    private long timeStamp;
    private long maxPeriod;

    private boolean flag;

    private List<String> hashList;

    public String getMyHash() {
        return myHash;
    }

    public void setMyHash(String myHash) {

        this.myHash = myHash;

    }

    public String getBodySign() {
        return bodySign;
    }

    public void setBodySign(String bodySign) {
        this.bodySign = bodySign;
    }

    public String getPreHash() {
        return preHash;
    }

    public void setPreHash(String preHash) {
        this.preHash = preHash;
    }

    public int getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getNextHash() {
        return nextHash;
    }

    public void setNextHash(String nextHash) {
        this.nextHash = nextHash;
    }

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getMaxPeriod() {
        return maxPeriod;
    }

    public void setValidityPeriod(long maxPeriod) {
        this.maxPeriod = maxPeriod;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    //    public long getVersion() {
//        return version;
//    }
//
//    public void setVersion(long version) {
//        this.version = version;
//    }


    public String getMerkleRoot() {
        return merkleRoot;
    }

    public void setMerkleRoot(String merkleRoot) {
        this.merkleRoot = merkleRoot;
    }

    public List<String> getHashList() {
        return hashList;
    }

    public void setHashList(List<String> hashList) {
        this.hashList = hashList;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }


    public void setMaxPeriod(long maxPeriod) {
        this.maxPeriod = maxPeriod;
    }

    public String toString(){
        return "BlockHead{"+
                "\npreBlockHash = "+preHash+
                "\nblockNumber = "+blockNumber+
                "\npublicKey = "+pubKey+
                "\nmerkleRoot = "+merkleRoot+
                "\ntimeStamp = "+timeStamp+
                "\nbodySign = "+bodySign+
                "\n}";
    }
}