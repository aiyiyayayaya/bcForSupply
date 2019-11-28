package com.mindata.blockchain.Util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by aiya on 2019/11/26 下午3:21
 */
public class StringUtilTest {

    @Test
    public void applySha256() {
        String a = new String("ssssssdfffffffffdsdddddds");
        String b = "234532";
        System.out.println(StringUtil.applySha256(new String(a + b)));
        System.out.println(StringUtil.applySha256(new String(a + b)));
    }
}