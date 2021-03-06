package com.bupt.aiya.blockchain.core.dao.BC;


import com.bupt.aiya.blockchain.core.entity.BC.Block.BlockHeader;
import com.bupt.aiya.blockchain.core.entity.BC.BlockIndexPojo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by aiya on 2019/3/20 上午9:16
 */
@Mapper
@Repository
public interface BlockDao {
    void addBlock(BlockHeader blockHeader);
    //String
    List<BlockHeader> getAll();
    BlockHeader getByHash(String key);
    BlockHeader getByNum(int number);
    String getFirstBlock();
    BlockIndexPojo getLastBlock();
    String getPreBlock(String myhash);
    String getNextBlock(String myhash);
    void updateFirstBlock(BlockIndexPojo bp);
    void updateLastBlock(BlockIndexPojo bp);
    void updatePreBlock(BlockHeader blockHead);
    void remove(String key);
}
