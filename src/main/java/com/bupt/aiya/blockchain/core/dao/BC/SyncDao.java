package com.bupt.aiya.blockchain.core.dao.BC;

import com.bupt.aiya.blockchain.core.entity.BC.SyncEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Created by aiya on 2019/3/18 下午2:42
 */
@Mapper
@Repository
public interface SyncDao {
    //find the last synchronized entity
    public SyncEntity findLastOne();

    //save sync entity
    public SyncEntity save(SyncEntity syncEntity);

    //public Object findAll()
}
