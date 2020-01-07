package com.bupt.aiya.blockchain.core.entity.BC.Block;

import cn.hutool.crypto.digest.DigestUtil;

/**
 * Created by aiya on 2018/11/14.
 */

public class Block {
    private BlockHeader blockHeader;
    private BlockBody blockBody;
    //block_Hash
    private String hash;

    private String calculateHash(){
        String ans = DigestUtil.sha256Hex(blockHeader.toString() + blockBody.toString());
        System.out.println("hash = "+ans);
        return DigestUtil.sha256Hex(blockHeader.toString() + blockBody.toString());
    }

    public String toString(){
        return "Block{"+
                "blockHeader = "+blockHeader+
                "blockBody = "+blockBody+
                "hash = "+hash+
                "}";
    }

    public BlockHeader getBlockHeader() {
        return blockHeader;
    }

    public void setBlockHeader(BlockHeader blockHeader) {
        this.blockHeader = blockHeader;
    }

    public BlockBody getBlockBody() {
        return blockBody;
    }

    public void setBlockBody(BlockBody blockBody) {
        this.blockBody = blockBody;
    }

    public String getHash() {
        return hash;
    }

    public void setHash() {
        this.hash = calculateHash();
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
