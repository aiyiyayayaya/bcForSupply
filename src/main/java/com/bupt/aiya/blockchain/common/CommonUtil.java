package com.bupt.aiya.blockchain.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.util.Enumeration;
import java.util.UUID;

/**
 * @author wuweifeng wrote on 2018/3/8.
 */
public class CommonUtil {
    private static Logger logger = LoggerFactory.getLogger(CommonUtil.class);
    public static Long getNow() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        InetAddress inetAddress = getLocalHostLANAddress();
        System.out.println(inetAddress.getHostName());
    }

    public static String getLocalIp() {
        InetAddress inetAddress = getLocalHostLANAddress();
        if (inetAddress != null) {
            return inetAddress.getHostAddress();
        }
        return null;
    }

    public static String generateUuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取本机ip地址
     */
    private static InetAddress getLocalHostLANAddress() {
        try {
            Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()){
                NetworkInterface networkInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if(networkInterface.getName().equals("en0")){
                    Enumeration addresses = networkInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = (InetAddress) addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address) {
                            return ip;
                        }
                    }
                }
                System.out.println("-------");
            }
        } catch (SocketException e) {
            logger.error("没获取到本机Ip啊");
        }
        //原
//        try {
//            InetAddress candidateAddress = null;
//            // 遍历所有的网络接口
//            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements(); ) {
//                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
//                // 在所有的接口下再遍历IP
//                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements(); ) {
//                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
//                    // 排除loopback类型地址
//                    if (!inetAddr.isLoopbackAddress()) {
//                        if (inetAddr.isSiteLocalAddress()) {
//                            // 如果是site-local地址，就是它了
//                            return inetAddr;
//                        } else if (candidateAddress == null) {
//                            // site-local类型的地址未被发现，先记录候选地址
//                            candidateAddress = inetAddr;
//                        }
//                    }
//                }
//            }
//            if (candidateAddress != null) {
//                return candidateAddress;
//            }
//            // 如果没有发现 non-loopback地址.只能用最次选的方案
//            return InetAddress.getLocalHost();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return null;
    }
}
