package com.mindata.blockchain.socket.body;


import com.mindata.blockchain.core.entity.BC.Block.BlockBody;

/**
 * 生成Block时传参
 * @author aiya wrote on 2010/1/8.
 */
public class RequestBlockBody {
    private String publicKey;
    private BlockBody blockBody;

    @Override
    public String toString() {
        return "RequestBlockBody{" +
                "publicKey='" + publicKey + '\'' +
                ", blockBody=" + blockBody +
                '}';
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public BlockBody getBlockBody() {
        return blockBody;
    }

    public void setBlockBody(BlockBody blockBody) {
        this.blockBody = blockBody;
    }
}
