package com.mindata.blockchain.Util;

import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by aiya on 2018/12/3.
 */
public class MacAndIP {
    //可使用内存
    private long totalMemory;
    //剩余内存
    private long freeMemory;

    public static String getMacAddress(){
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            byte[] mac = null;
            while(allNetInterfaces.hasMoreElements()){
                NetworkInterface networkInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if(networkInterface.isLoopback() || networkInterface.isVirtual() ||networkInterface.isPointToPoint()|| !networkInterface.isUp()){
                    continue;
                }
                else{
                    mac = networkInterface.getHardwareAddress();
                    if (mac != null) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for(int i = 0;i<mac.length;i++){
                            stringBuilder.append(String.format("%02X%S", mac[i], (i<mac.length-1) ? "-":""));
                        }
                        if(stringBuilder.length()>0)
                            return stringBuilder.toString();
                    }
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException("Mac地址获取失败",e);
        }
        return "";
    }

    public static String getIpAddress(){
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress candidateIp = null;
            //遍历所有网络接口
            while(allNetInterfaces.hasMoreElements()){
                NetworkInterface networkInterface = (NetworkInterface)allNetInterfaces.nextElement();
                //在接口下遍历IP
                for (Enumeration inetAddresses = networkInterface.getInetAddresses(); inetAddresses.hasMoreElements();){
                    InetAddress ipAddress = (InetAddress) inetAddresses.nextElement();
                    if (!ipAddress.isLoopbackAddress() && ipAddress instanceof Inet4Address){
                        if (ipAddress.isSiteLocalAddress())
                            return ipAddress.getHostAddress();
                        else if (candidateIp == null)
                            candidateIp = ipAddress;
                    }
                }
                if (candidateIp !=null)
                    return candidateIp.getHostAddress();
                return Inet4Address.getLocalHost().getHostAddress();
            }
        } catch (Exception e) {
            throw new RuntimeException("IP地址获取失败~",e);
        }
        return "";
    }

    public void getCPU() throws SigarException {
        Sigar sigar = SigarUtil.sigar;
        double cpu = sigar.getCpuPerc().getIdle();
        System.out.println("cpu：" + cpu);

        Mem mem = sigar.getMem();
        float memper = (float) mem.getFree()/mem.getTotal();
        System.out.println("内存："+ memper);

        File[] roots = File.listRoots();
        long totalDiskSpace = 0, freeDiskSpace = 0;
        for (File file : roots){
            totalDiskSpace += file.getTotalSpace();
            freeDiskSpace += file.getFreeSpace();
        }
        float diskPer = (float) freeDiskSpace / totalDiskSpace;
        System.out.println("磁盘："+ diskPer);
        float ans = (float) (0.6*diskPer + 0.4*(0.5*cpu + 0.5*memper));
        System.out.println("ans = " + ans);
    }

    public static void main(String[] args) {
        System.out.println(getIpAddress());
    }
}
