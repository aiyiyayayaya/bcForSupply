package com.mindata.blockchain.socket.body;

import com.mindata.blockchain.Util.Result;

import java.util.List;

/**
 * Created by aiya on 2018/12/14 下午5:00
 */
public class JoinGroupRespBody extends BaseBody{
    private Result<List<String>> ipList;

    public Result<List<String>> getIpList() {
        return ipList;
    }

    public void setIpList(Result<List<String>> ipList) {
        this.ipList = ipList;
    }
}
