package com.mindata.blockchain.Util;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;

/**
 * Created by aiya on 2018/12/11.
 */
public class MessageSignature {

    /**
     * function:签名
     * @param message 待签名的字符串
     * @param ecPrivateKey 私钥字符串
     * @return 签名结果
     * */
    public byte[] ECSign(byte[] message, ECPrivateKey ecPrivateKey) throws SignatureException {
        try {
            Signature signature = Signature.getInstance("SHA512withECDSA");
            signature.initSign(ecPrivateKey);
            signature.update(message);
            byte[] signed = signature.sign();
            return signed;
        } catch (Exception e) {
            throw new SignatureException("ECAccount = "+ message +";charset = ",e);
        }
    }
    /**
     * function:验证签名
     * @param data 被签名的内容
     * @param sign 签名后的结果
     * @param publicKey
     * @return 验签结果
     * */
    public boolean verify(byte[] data, byte[] sign, ECPublicKey publicKey) throws Exception {
        Signature signature = null;
        try {
            signature = Signature.getInstance("SHA512withECDSA");
            signature.initVerify(publicKey);
            signature.update(data);
            return signature.verify(sign);
        } catch (NoSuchAlgorithmException e) {
            throw new SignatureException("验证签名[content = " + data + "; charset = " + "; signature = " + sign + "]发生异常!", e);
        }
    }
}
