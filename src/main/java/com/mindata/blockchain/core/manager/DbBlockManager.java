package com.mindata.blockchain.core.manager;

import cn.hutool.core.util.StrUtil;
import com.mindata.blockchain.common.Constants;
import com.mindata.blockchain.common.FastJsonUtil;
import com.mindata.blockchain.core.dao.BC.BlockDao;
import com.mindata.blockchain.core.dao.BC.NewBlockDetailDao;
import com.mindata.blockchain.core.entity.BC.Block.Block;
import com.mindata.blockchain.core.entity.BC.Block.BlockBody;
import com.mindata.blockchain.core.entity.BC.Block.BlockDetail;
import com.mindata.blockchain.core.entity.BC.Block.BlockHeader;
import com.mindata.blockchain.core.entity.BC.BlockIndexPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Created by aiya on 2019/1/20 下午2:24
 * 如果一个类带了@SERVICE注解，将自动注册到SprinG容器，不需要再在applicationContext.xml文件定义bean了
 */
@Service
public class DbBlockManager {

    @Autowired
    private BlockDao blockDao;

    @Autowired
    private NewBlockDetailDao newBlockDetailDao;
    private Logger logger = LoggerFactory.getLogger(DbBlockManager.class);


    /**
     * 获取某一个block的下一个Block
     * */
    public Block getNextBlock(Block block) throws Exception {
        if (block == null)
            return getFirstBlock();
        String nextHash = blockDao.getNextBlock(block.getHash());
        if (nextHash == null)
            return null;
        return getBlockByHash(nextHash);
    }

    /**
     * 根据hash值查区块
     * */
    public Block getBlockByHash(String hash) {
        BlockHeader blockHeader = null;
        try{
            blockHeader = blockDao.getByHash(hash);
        }catch (Exception e){
            logger.error("查询出错，hash:{}", hash, e);
            return null;
        }
        if (blockHeader == null){
            return null;
        }
        int blockNum = blockHeader.getBlockNumber();
        List<BlockDetail> lists = new ArrayList<BlockDetail>(newBlockDetailDao.selectBlockDetailList(blockNum));
        BlockBody blockBody = new BlockBody();
        blockBody.setBlockDetails(lists);
        System.out.println("null???"+blockHeader.getBodySign());
        blockBody.setSign(Base64.getDecoder().decode(blockHeader.getBodySign()));
        Block block = new Block();
        block.setHash(hash);
        block.setBlockHeader(blockHeader);
        block.setBlockBody(blockBody);
        return block;
    }

    /**
     * 查找第一个区块
     * @return 第一个Block
     */
    public Block getFirstBlock() throws SignatureException {
        String firstBlockHash = blockDao.getFirstBlock();
        if (StrUtil.isEmpty(firstBlockHash))
            return null;
        return getBlockByHash(firstBlockHash);
    }

    /**
     * 获取最后一个区块
     * @return 最后一个区块
     */
    public Block getLastBlock(){
        BlockIndexPojo lastBlock = blockDao.getLastBlock();//Hbase.getCellData("Block", Constants.LAST_BLOCK_HASH, "Head","Hash");
        if (StrUtil.isEmpty(lastBlock.getBlockHash()))
            return null;
        return getBlockByHash(lastBlock.getBlockHash());
    }

    /**
     * 获取最后一个block的number
     * @return number
     */
    public int getLastBlockNumber() {
        BlockIndexPojo lastBlock = null;
        try {
            lastBlock = blockDao.getLastBlock();
        }catch (Exception e){
            logger.info("当前没有区块", e);
        }
        if (lastBlock == null)
            return 0;
        return lastBlock.getBlockNumber();
    }

    /**
     * 获取最后一个区块的hash
     * @return hash
     */
    public String getLastBlockHash() {
        BlockIndexPojo lastBlock = null;
        try {
            lastBlock = blockDao.getLastBlock();
        }catch (Exception e){
            logger.info("当前没有区块", e);
        }
        if (lastBlock == null)
            return "Null";
        return lastBlock.getBlockHash();
    }

}
