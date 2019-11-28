package com.mindata.blockchain.Util;

import com.google.common.io.Resources;
import org.hyperic.sigar.Sigar;

import java.io.File;
import java.io.IOException;

/**
 * Created by aiya on 2019/5/21 上午11:23
 */
public class SigarUtil {
    public final static Sigar sigar = initSigar();
    private static Sigar initSigar(){
        try{
            String file = Resources.getResource("sigar/.sigar_shellrc").getFile();
            File classPath = new File(file).getParentFile();

            String path = System.getProperty("java.labrary.path");
            String sigarLibPath = classPath.getCanonicalPath();
            //
            //if (!path.contains(sigarLibPath)){
                if (isOSWin())
                    path += ";"+sigarLibPath;
                else
                    path += ":"+sigarLibPath;
                System.setProperty("java.library.path", path);
            //}
            return new Sigar();
        } catch (IOException e) {
            return null;
        }
    }
    public static boolean isOSWin(){//OS 版本判断
        String OS = System.getProperty("os.name").toLowerCase();
        if (OS.indexOf("win") >= 0) {
            return true;
        } else return false;
    }
}
