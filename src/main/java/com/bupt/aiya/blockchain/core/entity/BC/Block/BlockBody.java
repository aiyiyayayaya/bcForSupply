package com.bupt.aiya.blockchain.core.entity.BC.Block;

import com.bupt.aiya.blockchain.Util.MessageSignature;
import com.bupt.aiya.blockchain.Util.SerializeUtils;

import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.interfaces.ECPrivateKey;
import java.util.Base64;
import java.util.List;

/**
 * Created by aiya on 2019/1/9 下午4:09
 */
public class BlockBody {
    private List<BlockDetail> blockDetails;

    private byte[] sign;

    public String toString(){
        return "BlockBody{"+
                "BlockDetails = "+blockDetails.toString()+
                "sign = " + Base64.getEncoder().encodeToString(sign) +
                "}";
    }

    public List<BlockDetail> getBlockDetails() {
        return blockDetails;
    }

    public void setBlockDetails(List<BlockDetail> blockDetails) {
        this.blockDetails = blockDetails;
    }

    public byte[] getSign() {
        return sign;
    }

    public void setSign(byte[] sign) {
        this.sign = sign;
    }

    public void generateSign(List<BlockDetail> transactions, PrivateKey privateKey) throws SignatureException {//BlockBody blockBody
        MessageSignature messageSignature = new MessageSignature();
        //String content = transactions.toString();
        sign = messageSignature.ECSign(SerializeUtils.serialize(transactions), (ECPrivateKey) privateKey);
    }
}
