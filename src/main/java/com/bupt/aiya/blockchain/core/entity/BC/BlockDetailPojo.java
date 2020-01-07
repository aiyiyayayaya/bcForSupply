package com.bupt.aiya.blockchain.core.entity.BC;

/**
 * Created by aiya on 2019/3/26 下午1:35
 */
public class BlockDetailPojo {
    private String productType;
    private String productId;

    private String operation;

    private String responsible;

    private String state;
    private long timeStamp = System.currentTimeMillis();
    private long validPeriod;

    //blockhead mapping
    private int blockNumber;
    private String blockHash;

    public BlockDetailPojo(String productType, String productId, String operation, String responsible, String state, long timeStamp, long validPeriod, int blockNum, String blockHash) {
        this.productType = productType;
        this.productId = productId;
        this.operation = operation;
        this.responsible = responsible;
        this.state = state;
        this.timeStamp = timeStamp;
        this.validPeriod = validPeriod;
        this.blockNumber = blockNum;
        this.blockHash = blockHash;
    }


    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getValidPeriod() {
        return validPeriod;
    }

    public void setValidPeriod(long validPeriod) {
        this.validPeriod = validPeriod;
    }

    public int getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }
}
