package com.bupt.aiya.blockchain.socket.body;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aiya on 2019/5/20 下午4:16
 */
public class DeleteRespBody extends BaseBody{
    //返回区块i的IpList
    private int blockNum;
    private List<String> ipList;

    public DeleteRespBody() {
    }

    public DeleteRespBody(int blockNum, ArrayList<String> ipList) {
        this.blockNum = blockNum;
        this.ipList = ipList;
    }

    public int getBlockNum() {
        return blockNum;
    }

    public void setBlockNum(int blockNum) {
        this.blockNum = blockNum;
    }

    public List<String> getIpList() {
        return ipList;
    }

    public void setIpList(List<String> ipList) {
        this.ipList = ipList;
    }
}
