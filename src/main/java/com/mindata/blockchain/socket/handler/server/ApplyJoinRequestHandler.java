package com.mindata.blockchain.socket.handler.server;

import com.mindata.blockchain.Util.SpringUtil;
import com.mindata.blockchain.common.AppId;
import com.mindata.blockchain.core.dao.BC.BCUserDao;
import com.mindata.blockchain.core.entity.BC.BCUser;
import com.mindata.blockchain.socket.base.AbstractBlockHandler;
import com.mindata.blockchain.socket.body.JoinGroupReqBody;
import com.mindata.blockchain.socket.packet.HelloPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.tio.core.ChannelContext;

/**
 * Created by aiya on 2019/11/29 上午1:39
 */
public class ApplyJoinRequestHandler extends AbstractBlockHandler<JoinGroupReqBody> {
    private Logger logger = LoggerFactory.getLogger(ApplyJoinRequestHandler.class);

    private BCUserDao bcUserDao = SpringUtil.getBean(BCUserDao.class);

    @Override
    public Class<JoinGroupReqBody> bodyClass() {
        return JoinGroupReqBody.class;
    }

    @Override
    public Object handler(HelloPacket packet, JoinGroupReqBody bsBody, ChannelContext channelContext) throws Exception {
        BCUser bcUser = bsBody.getBcUser();
        if (AppId.value.equals("aiya")){
            if (bcUser != null) {
                if (bcUserDao.selectUserByMac(bcUser.getUserMac()) == null) {
                    int res = bcUserDao.insertWaiting(bcUser);
                    logger.info("节点:{}加入申请结果:{}", bcUser.getUserIp(), res);
                }
            }
        }
        return null;
    }
}
