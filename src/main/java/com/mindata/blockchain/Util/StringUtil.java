package com.mindata.blockchain.Util;

import org.hibernate.boot.jaxb.hbm.internal.CacheAccessTypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

/**
 * Created by aiya on 2018/11/14.
 */
public class StringUtil {
    private static Logger logger = LoggerFactory.getLogger(StringUtil.class);

    /**
     * 定义char数组,16进制对应的基本字符
     */
    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f' };

    /***
     * 生成签名的方法*/
    public static  String applySha256(String input){
        MessageDigest messageDigest = null;
        try{
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(input.getBytes());
            byte[] digest = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest){
                sb.append(HEX_DIGITS[(b & 0xf0) >> 4]).append(HEX_DIGITS[(b & 0x0f)]);
            }
            return sb.toString();
        }catch (Exception e){
            logger.error("获取加密算法出错", e);
            return null;
        }
//        try{
//            MessageDigest digest = MessageDigest.getInstance("SHA-256");
//            byte[] hash = digest.digest(input.getBytes("UTF-8"));
//            StringBuilder hexString = new StringBuilder();
//            for(int i = 0;i<hash.length;i++){
//                String hex = Integer.toHexString(0xff & hash[i]);
//                if (hex.length() == 1)
//                    hexString.append('0');
//                hexString.append(hex);
//            }
//            return hexString.toString();
//        }catch(Exception e){
//            throw new RuntimeException(e);
//        }
    }
    //生成签名
    public static byte[] applyECDSASig(PrivateKey privateKey,String input){
        Signature signature;
        byte[] output = new byte[0];
        try {
            signature = Signature.getInstance("ECDSA","BS");
            signature.initSign(privateKey);
            byte[] strByte = input.getBytes();
            signature.update(strByte);
            byte[] realSig = signature.sign();
            output = realSig;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return output;
    }
    //验证签名
    public static boolean verifyECDSASig(PublicKey publicKey, String data, byte[] signature){
        try{
            Signature ecdsaVerify = Signature.getInstance("ECDSA","BC");
            ecdsaVerify.initVerify(publicKey);
            ecdsaVerify.update(data.getBytes());
            return ecdsaVerify.verify(signature);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


//    public static String getStringFromKey(Key key){
//        return Base64.getEncoder().encodeToString(key.getEncoded());
//    }
}
