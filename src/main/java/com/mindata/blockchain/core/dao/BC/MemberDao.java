package com.mindata.blockchain.core.dao.BC;

import com.mindata.blockchain.core.entity.BC.Member;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Created by aiya on 2019/11/29 上午9:19
 */
@Mapper
@Repository
public interface MemberDao {
    int insertMember(Member member);
    int deleteMember(String appId);
}
