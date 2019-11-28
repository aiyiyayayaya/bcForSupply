package com.mindata.blockchain.core.dao.BC;

import com.mindata.blockchain.core.entity.BC.Block.BlockDetail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by aiya on 2019/8/28 上午8:48
 * 新的blockDetail表
 */
@Mapper
@Repository
public interface NewBlockDetailDao {
    //1. 根据区块号&物资ID查，唯一
    BlockDetail queryBlockDetail(Integer blockId, Integer transId);
    //2. 插入
    int insertBlockDetail(BlockDetail blockDetail);
    //3. 根据区块号查，会返回区块中的所有blockDetail
    List<BlockDetail> selectBlockDetailList(Integer blockId);
    //4. 根据物资id查
    List<BlockDetail> selectByTransId(String transId);

    //5. 物资id & opType
    List<BlockDetail> selectByOpTypeAndBid(String type, String bid);
}
