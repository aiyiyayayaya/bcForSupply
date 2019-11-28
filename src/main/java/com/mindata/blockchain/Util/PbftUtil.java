package com.mindata.blockchain.Util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by aiya on 2019/1/7 上午10:04
 */
public class PbftUtil {
    public static boolean reachMajority(int f, int n){
        return f == 2 * getMaxTorelentNum(n) +1;
    }
    public static int getMaxTorelentNum(int n){
        if(n/3 * 3 == n)
            return n/3 -1;
        return n/3;
    }

    public static int[][] flipMatrix(int[][] matrix){
        int m = matrix.length, n = matrix[0].length;
        int[][] flipMa = new int[n][m];
        for (int i =0; i<n; i++){
            for (int j = 0;j<m; j++)
                flipMa[i][j] = matrix[j][i];
        }
        return flipMa;
    }
    /**
     * function:get the digest of message
     * @param content
     *
     * @return digest
     * */
    public static String getMD5Digest(String content){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("sha-256");
            messageDigest.update(content.getBytes("utf-8"));
            return bytesToHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * function: turn the byte matrix to 16_String
     * @param bytes
     * @return 16_String
     */
    private static String bytesToHexString(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer(bytes.length);
        String tmp = null;
        for (int i =0;i<bytes.length;i++){
            tmp = Integer.toHexString(0xFF & bytes[i]);
            if (tmp.length() < 2)
                stringBuffer.append(0);
            stringBuffer.append(tmp);
        }
        return stringBuffer.toString();
    }
}
