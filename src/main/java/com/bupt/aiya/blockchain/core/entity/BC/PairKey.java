package com.bupt.aiya.blockchain.core.entity.BC;

/**
 * Created by aiya on 2019/10/31 下午9:44
 */
public class PairKey {
    private String publicKey;
    private String privateKey;

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
}
