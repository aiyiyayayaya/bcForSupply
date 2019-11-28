package com.mindata.blockchain.core.manager;

import com.mindata.blockchain.ApplicationContextProvider;

import com.mindata.blockchain.Util.StringUtil;
import com.mindata.blockchain.common.Constants;
import com.mindata.blockchain.core.dao.BC.BlockDao;
import com.mindata.blockchain.core.dao.BC.NewBlockDetailDao;
import com.mindata.blockchain.core.entity.BC.Block.Block;
import com.mindata.blockchain.core.entity.BC.Block.BlockDetail;
import com.mindata.blockchain.core.entity.BC.Block.BlockHeader;
import com.mindata.blockchain.core.entity.BC.BlockIndexPojo;
import com.mindata.blockchain.core.event.AddBlockEvent;
import com.mindata.blockchain.core.event.DbSyncEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tio.utils.json.Json;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Base64;

/**
 * block的本地存储
 * @author wuweifeng wrote on 2018/4/25.
 */
@Service
public class DbBlockGenerator {
//    @Resource
//    private DbStore dbStore;
//    @Resource
//    private CheckerManager checkerManager;

    @Autowired
    BlockDao blockDao;

    @Autowired
    NewBlockDetailDao newBlockDetailDao;
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 数据库里添加一个新的区块
     *
     * @param addBlockEvent
     *         addBlockEvent
     */
    @Order(1)
    @EventListener(AddBlockEvent.class)
    @Transactional
    public synchronized void addBlock(AddBlockEvent addBlockEvent) {
        logger.info("开始生成本地block");
        Block block = (Block) addBlockEvent.getSource();
        String hash = block.getHash();

        BlockHeader blockHeader = block.getBlockHeader();
        String encoded = Base64.getEncoder().encodeToString(block.getBlockBody().getSign());
        blockHeader.setBodySign(encoded);

        ArrayList<BlockDetail> list = new ArrayList<BlockDetail>(block.getBlockBody().getBlockDetails());
        Long maxPeriod = Long.MIN_VALUE;
        for(BlockDetail tran : list){
            maxPeriod = Math.max(tran.getValidPeriod(), maxPeriod);
        }
        blockHeader.setMaxPeriod(maxPeriod);

        //更新hash值-myhash = null
        blockHeader.setMyHash(null);
        blockHeader.setMyHash(StringUtil.applySha256(block.getBlockHeader().toString() + block.getBlockBody().toString()));
        block.setHash(blockHeader.getMyHash());

        System.out.println("插入时");
        System.out.println(block.getBlockHeader().toString());
        System.out.println(block.getBlockBody().toString());
        System.out.println(block.getHash());


        //1. add BlockHead
        blockDao.addBlock(blockHeader);

        //2. update BlockIndex
        int blocknum = block.getBlockHeader().getBlockNumber();
        String blockHash = block.getHash();
        //True
        //System.out.println(blockHeader.getPreHash().equals("Null"));
        if (blockHeader.getPreHash().equals("Null")) {
            BlockIndexPojo bp = new BlockIndexPojo(blocknum, blockHash);
            blockDao.updateFirstBlock(bp);
        }
        BlockIndexPojo bp = new BlockIndexPojo(blocknum,blockHash);
        blockDao.updateLastBlock(bp);

        //3. update last block'next hash
        if(!blockHeader.getPreHash().equals("Null")) {
            BlockHeader preBlock = blockDao.getByHash(blockHeader.getPreHash());
            preBlock.setNextHash(blockHash);
            blockDao.updatePreBlock(preBlock);
        }
        //4. insert blockBody
        for(BlockDetail blockDetail : list){
            int flag = newBlockDetailDao.insertBlockDetail(blockDetail);
            if (flag < 0){
                logger.error("blockDetail表插入失败");
                //ResultUtil.error(ResultEnums.INSERT_FAIL.getCode(), ResultEnums.INSERT_FAIL.getMsg());
            }
//                BlockDetailPojo blockPojo = new BlockDetailPojo(blockDetail.getProductType(),blockDetail.getProductId(),blockDetail.getOperation(),
//                        blockDetail.getResponsible(),blockDetail.getState(),blockDetail.getTimeStamp(),blockDetail.getValidPeriod(),blocknum,blockHash);
//                blockDetailDao.addDetail(blockPojo);
        }
        logger.info("success to update mysql");

//        //校验区块
//        if (checkerManager.check(block).getCode() != 0) {
//            return;
//        }
//
//        //如果没有上一区块，说明该块就是创世块
//        if (block.getBlockHeader().getHashPreviousBlock() == null) {
//            dbStore.put(Constants.KEY_FIRST_BLOCK, hash);
//        } else {
//            //保存上一区块对该区块的key value映射
//            dbStore.put(Constants.KEY_BLOCK_NEXT_PREFIX + block.getBlockHeader().getHashPreviousBlock(), hash);
//        }
//        //存入rocksDB
//        dbStore.put(hash, Json.toJson(block));
//        //设置最后一个block的key value
//        dbStore.put(Constants.KEY_LAST_BLOCK, hash);

        logger.info("本地已生成新的Block");

        //同步到sqlite
        sqliteSync();
    }

    /**
     * sqlite根据block信息，执行sql
     */
    @Async
    public void sqliteSync() {
        //开始同步到sqlite
        ApplicationContextProvider.publishEvent(new DbSyncEvent(""));
    }
}
