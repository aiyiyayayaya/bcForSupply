package com.mindata.blockchain.User; /**
 * Created by aiya on 2018/11/15.
 */

import com.mindata.blockchain.Util.MacAndIP;
import com.mindata.blockchain.core.dao.BC.BCUserDao;
import com.mindata.blockchain.core.entity.BC.BCUser;
import org.springframework.beans.factory.annotation.Autowired;
import sun.misc.BASE64Decoder;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
//import java.util.Base64;

public class User {
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private String usr_Mac;
    private String usr_Ip;
    private String usr_Socket = "6789";
    private String usr_Name;
    private String usr_Id;


/**
 * new User()生成公私钥
 * 加判断，MAC地址为唯一身份，避免重复生成
 * **/
    public User() {
        usr_Mac = MacAndIP.getMacAddress();
        usr_Ip= MacAndIP.getIpAddress();

        generateKeyPair();
        String rowkey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        String priv = Base64.getEncoder().encodeToString(privateKey.getEncoded());

        /**
         * test is right*/
        System.out.println("after decode publicKey:" + rowkey);
        System.out.println("after decode privateKey:" + priv);
        System.out.println("嗯，你可以注册一个区块链账户了");
//            PublicKey tmpPk = string2PublicKey(rowkey);
//            BCUser bcUser = new BCUser(rowkey, priv, usr_Mac, usr_Ip, usr_Socket, usr_Name, usr_Id);
//            System.out.println("rowKey = "+rowkey);
//            System.out.println("i = "+ );



            //System.out.println("----成功转换？-----\n"+tmpPk.toString());
//            Hbase.insertData("Partner",rowkey,"connectionMessage","usr_Socket",usr_Socket);
//            Hbase.insertData("Partner",rowkey,"myPrivKey","myPrivKey",Base64.getEncoder().encodeToString(privateKey.getEncoded()));
//            System.out.println("you have successed to create a new user!");
//            String test = hbase.getResultWanted("Partner","basicMessage", "usr_Mac",usr_Mac);
//            System.out.println("and your decode publicKey is :"+rowkey);
//            PublicKey testPk = string2PublicKey(test);
//            //System.out.println("----TEST成功转换？-----\n"+testPk.toString());


//            PublicKey testPk = string2PublicKey(ans);
//            setPublicKey(testPk);
//            String testSp = Hbase.getCellData("Partner", ans, "myPrivKey", "myPrivKey");
//            PrivateKey testPriv = string2PrivateKey(testSp);
//            setPrivateKey(testPriv);
//            usr_Name = Hbase.getCellData("Partner",ans,"basicMessage","usr_Name");
//            usr_Id = Hbase.getCellData("Partner",ans,"basicMessage","usr_Id");
//            System.out.println("----your publicKey is:----\n"+publicKey.toString());
//            System.out.println("----your privateKey is:----\n"+privateKey.toString());
//            System.out.println("----your ip is:"+Hbase.getCellData("Partner", ans, "connectionMessage", "usr_Ip"));
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public String getUsr_Mac() {
        return usr_Mac;
    }

    public void setUsr_Mac(String usr_Mac) {
        this.usr_Mac = usr_Mac;
    }

    public String getUsr_Ip() {
        return usr_Ip;
    }

    public void setUsr_Ip(String usr_Ip) {
        this.usr_Ip = usr_Ip;
    }

    public String getUsr_Name() {
        return usr_Name;
    }

    public void setUsr_Name(String usr_Name) {
        this.usr_Name = usr_Name;
    }

    public String getUsr_Id() {
        return usr_Id;
    }

    public void setUsr_Id(String usr_Id) {
        this.usr_Id = usr_Id;
    }

    public String getUsr_Socket() {
        return usr_Socket;
    }

    public void setUsr_Socket(String usr_Socket) {
        this.usr_Socket = usr_Socket;
    }

    private void generateKeyPair() {
        try{
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
            //PRNG伪随机数生成，使用SHA-1作为PRNG的基础
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
            keyGen.initialize(ecSpec, random);
            KeyPair keyPair = keyGen.generateKeyPair();

            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
//            String encodeKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
//            System.out.println(encodeKey);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public static PublicKey string2PublicKey(String key) throws Exception {
        byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(key);
//        ASN1Primitive asn1Primitive = new ASN1InputStream(keyBytes).readObject();
//        ECDSAPublicKey ecdsaPublicKey = (ECDSAPublicKey) ECDSAPublicKey.getInstance(keyBytes);
//        byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("EC");//ECDSA
        PublicKey pk = keyFactory.generatePublic(keySpec);
        return pk;
    }
    public static PrivateKey string2PrivateKey(String key) throws Exception{
        byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        PrivateKey privKey = keyFactory.generatePrivate(keySpec);
        return privKey;
    }

    public static String Pub2String(PublicKey publicKey){
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    public static String Priv2String(PrivateKey privateKey){
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }



}