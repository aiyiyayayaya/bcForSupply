package com.bupt.aiya.blockchain.core.entity.BC.Block.Check;


import com.bupt.aiya.blockchain.core.entity.BC.Block.Block;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by aiya on 2019/1/9 下午3:14
 */

//@Repository
public interface BlockChecker {

    String checkBlock(Block block);
    /**
     * 比较目标区块和自己本地区块的number
     * @return 本地与目标区块的差值
     * */
    int checkNum(Block block);

    /**
     * 校验签名
     * */
    int checkSignAndHash(Block block);
    /**
     * 校验生成时间
     * */
    int checkTime(Block block);

}
