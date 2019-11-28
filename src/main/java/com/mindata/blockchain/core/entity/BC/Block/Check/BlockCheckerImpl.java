package com.mindata.blockchain.core.entity.BC.Block.Check;

import cn.hutool.crypto.digest.DigestUtil;
import com.mindata.blockchain.User.Myself;
import com.mindata.blockchain.User.User;
import com.mindata.blockchain.Util.MessageSignature;
import com.mindata.blockchain.Util.SerializeUtils;
import com.mindata.blockchain.Util.StringUtil;
import com.mindata.blockchain.core.entity.BC.Block.Block;
import com.mindata.blockchain.core.entity.BC.Block.BlockDetail;
import com.mindata.blockchain.core.manager.DbBlockManager;
import com.mindata.blockchain.socket.body.RequestBlockBody;
import com.mindata.blockchain.core.service.bc.BlockService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.security.PublicKey;
import java.security.interfaces.ECPublicKey;
import java.util.Base64;
import java.util.List;

/**
 * Created by aiya on 2019/1/9 下午3:19
 */
@Component
public class BlockCheckerImpl implements BlockChecker {

    private Logger logger = LoggerFactory.getLogger(BlockChecker.class);

    @Resource
    private DbBlockManager dbBlockManager;

    @Resource
    private BlockService blockService;

    @Override
    public String checkBlock(Block block) {
    	if(checkSignAndHash(block) == -1) {
    	    return block.getHash();
    	}

    	String preHash = block.getBlockHeader().getPreHash();
    	if(preHash.equals("Null")) {
    	    return null;
    	}

    	//前一个区块确实存在
    	Block preBlock = dbBlockManager.getBlockByHash(preHash);
    	if(preBlock == null) return block.getHash();

		int localNum = preBlock.getBlockHeader().getBlockNumber();
        //当前区块+1等于下一个区块时才同意
        if (localNum + 1 != block.getBlockHeader().getBlockNumber()) {
            return block.getHash();
        }
        if(block.getBlockHeader().getTimeStamp() <= preBlock.getBlockHeader().getTimeStamp()) {
        	return block.getHash();
        }

    	return null;
    }

    @Override
    public int checkNum(Block block) {
        try {
            Block localBlock = dbBlockManager.getLastBlock();
            int localNum = 0;
            if (localBlock != null){
                localNum = localBlock.getBlockHeader().getBlockNumber();
            }
            //
            //本地区块+1等于新来的区块时才同意
            if (localNum + 1 == block.getBlockHeader().getBlockNumber()) {
                //同意生成区块
                return 0;
            }
            //拒绝
            return -1;
        } catch (Exception e) {
            logger.error("检查区块异常, block = {}", block, e);
            return -1;
        }
    }

    @Override
    public int checkSignAndHash(Block block) {

        RequestBlockBody requestBlockBody = new RequestBlockBody();
        requestBlockBody.setBlockBody(block.getBlockBody());
        requestBlockBody.setPublicKey(block.getBlockHeader().getPubKey());
        //1. 检查签名是否正确
        MessageSignature messageSignature = new MessageSignature();
        //string -> 秘钥
        try {
            PublicKey tmpPk = User.string2PublicKey(block.getBlockHeader().getPubKey());
            //区块头中的nextBlock重新设置为空
            block.getBlockHeader().setNextHash(null);
            block.getBlockHeader().setMyHash(null);
            List<BlockDetail> list = block.getBlockBody().getBlockDetails();
            int blockid = block.getBlockHeader().getBlockNumber();
            for (BlockDetail bd : list){
                bd.setId(null);
                bd.setBlockId(null);
            }

            boolean res = messageSignature.verify(SerializeUtils.serialize(block.getBlockBody().getBlockDetails()),
                    block.getBlockBody().getSign(),
                    (ECPublicKey) tmpPk);
            System.out.println("after:");
//            System.out.println("1. " + Base64.getEncoder().encodeToString(SerializeUtils.serialize(block.getBlockBody().getBlockDetails())));
//            System.out.println("2. " + Base64.getEncoder().encodeToString(block.getBlockBody().getSign()));
//            System.out.println("3. " + tmpPk);
            if (!res){
                return -1;
            }
            try {
                if(blockService.check(requestBlockBody) != null) {
                    return -1;
                }
            } catch (Exception e) {
                return -1;
            }

            //2. 检查hash是否正确
            for (BlockDetail bd : list){
                bd.setBlockId(blockid);
            }
            String hash = StringUtil.applySha256(block.getBlockHeader().toString() + block.getBlockBody().toString());//DigestUtil.sha256Hex(block.getBlockHeader().toString()+block.getBlockBody().toString());
            System.out.println(block.getBlockHeader().toString());
            System.out.println(block.getBlockBody().toString());
            System.out.println(hash);

            //todo hash 复现不对
            if(!StringUtils.equals(block.getHash(), hash)){
                return -1;
            }
            return 0;
        }catch (Exception e){
            logger.error("秘钥转换失败", e);
            return -1;
        }
    }


    @Override
    public int checkTime(Block block) {
        try {
            Block localBlock = dbBlockManager.getLastBlock();

            //新区块的生成时间比本地的还小
            if (localBlock != null && block.getBlockHeader().getTimeStamp() <= localBlock.getBlockHeader().getTimeStamp()) {
                //拒绝
                return -1;
            }
            return 0;
        } catch (Exception e) {
            logger.error("检查区块异常, block = {}", block, e);
            return -1;
        }
    }
}
