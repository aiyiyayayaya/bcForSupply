package com.bupt.aiya.blockchain.core.entity.BC;

/**
 * Created by aiya on 2019/4/11 下午3:34
 */
public class BlockIndexPojo {
    private int blockNumber;
    private String blockHash;

    public BlockIndexPojo(int blockNumber, String blockHash) {
        this.blockNumber = blockNumber;
        this.blockHash = blockHash;
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
