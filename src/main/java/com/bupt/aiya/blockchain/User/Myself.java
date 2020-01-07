package com.bupt.aiya.blockchain.User;

import com.bupt.aiya.blockchain.Util.MacAndIP;
import com.bupt.aiya.blockchain.Util.SpringUtil;
import com.bupt.aiya.blockchain.core.dao.BC.BCUserDao;
import com.bupt.aiya.blockchain.core.entity.BC.BCUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by aiya on 2019/1/10 下午1:58
 */

public class Myself {
    private Logger logger = LoggerFactory.getLogger(Myself.class);

    @Autowired
    public static BCUserDao bcUserDao;

    public static String usr_Mac;
    public static String usr_Ip;
    public static String usr_Id;
    public static String usr_Socket = "6789";
    public static String usr_Name;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private static Myself myself = null;

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public static Myself getMyself(){
        if (myself == null){
            synchronized (Myself.class){
                if (myself == null){
                    myself = new Myself();
                    return myself;
                }
            }
        }
        return myself;
    }
    private Myself()  {
        usr_Mac = MacAndIP.getMacAddress();
        usr_Ip= MacAndIP.getIpAddress();
        BCUser exist = null;
        try {
            if (bcUserDao == null){
                bcUserDao = SpringUtil.getBean(BCUserDao.class);
            }
            exist = bcUserDao.selectUserByMac(usr_Mac);
        }catch (Exception e){
            exist = null;
        }
        try {
            if (exist == null){
                User user = new User();
                usr_Id = user.getUsr_Id();
                usr_Name = user.getUsr_Name();
                publicKey = user.getPublicKey();
                privateKey = user.getPrivateKey();
            }else {
                usr_Id = "" + exist.getUserId();
                usr_Name = exist.getUserName();
                publicKey = User.string2PublicKey(exist.getPublicKey());
                privateKey = User.string2PrivateKey(exist.getPrivateKey());
            }
        } catch (Exception e) {
            logger.error("new User() 发生错误！", e);
        }
    }

}
