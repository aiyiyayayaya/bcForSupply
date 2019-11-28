package com.mindata.blockchain.socket.body;

/**
 * Created by aiya on 2019/5/20 下午4:14
 */
public class DeleteReqBody extends BaseBody{
    private int blockNum;

    private String blockHash;

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public int getBlockNum() {
        return blockNum;
    }

    public void setBlockNum(int blockNum) {
        this.blockNum = blockNum;
    }
}
