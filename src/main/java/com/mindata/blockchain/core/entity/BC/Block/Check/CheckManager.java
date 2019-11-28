package com.mindata.blockchain.core.entity.BC.Block.Check;

import com.mindata.blockchain.core.entity.BC.Block.Block;
import com.mindata.blockchain.socket.body.RpcCheckBlockBody;

/**
 * Created by aiya on 2019/1/9 下午3:27
 */
public class CheckManager {
    private BlockCheckerImpl blockChecker;

    /**
     * basic check
     * @return check result
     * */
    public RpcCheckBlockBody check(Block block){
        int code = blockChecker.checkSignAndHash(block);
        if (code != 0)
            return new RpcCheckBlockBody(-1,"block signature is illegal");
        int number = blockChecker.checkNum(block);
        if (number!=0)
            return new RpcCheckBlockBody(-1,"");
        int time = blockChecker.checkTime(block);
        if (time !=0)
            return new RpcCheckBlockBody(-4,"");
        return new RpcCheckBlockBody(0, "legal");
    }
}
