package com.bupt.aiya.blockchain.socket.handler.server;

import com.bupt.aiya.blockchain.Util.SpringUtil;
import com.bupt.aiya.blockchain.common.AppId;
import com.bupt.aiya.blockchain.core.dao.BC.BCUserDao;
import com.bupt.aiya.blockchain.core.dao.BC.MemberDao;
import com.bupt.aiya.blockchain.core.entity.BC.BCUser;
import com.bupt.aiya.blockchain.core.entity.BC.Member;
import com.bupt.aiya.blockchain.socket.base.AbstractBlockHandler;
import com.bupt.aiya.blockchain.socket.body.JoinGroupReqBody;
import com.bupt.aiya.blockchain.socket.packet.HelloPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.tio.core.ChannelContext;

import java.text.SimpleDateFormat;

/**
 * Created by aiya on 2019/11/29 上午1:48
 */
public class JoinBCRequestHandler extends AbstractBlockHandler<JoinGroupReqBody> {
    private Logger logger = LoggerFactory.getLogger(JoinBCRequestHandler.class);

    private BCUserDao bcUserDao = SpringUtil.getBean(BCUserDao.class);

    private MemberDao memberDao = SpringUtil.getBean(MemberDao.class);
    @Override
    public Class<JoinGroupReqBody> bodyClass() {
        return JoinGroupReqBody.class;
    }

    @Override
    public Object handler(HelloPacket packet, JoinGroupReqBody bsBody, ChannelContext channelContext) throws Exception {
        BCUser bcUser = bsBody.getBcUser();


        bcUserDao.newUser(bcUser);
        if (AppId.value.equals("aiya")) {
            //3. waiting列表里删掉
            int res = bcUserDao.deleteWaitingNode(bcUser.getUserId());
            //4. 加到member表中
            Member member = new Member();
            member.setAppId((bcUser.getUserId() + ""));
            member.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
            member.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
            member.setGroupId("1");
            member.setName(bcUser.getUserName());
            member.setIp(bcUser.getUserIp());
            memberDao.insertMember(member);
        }

        return null;
    }
}
