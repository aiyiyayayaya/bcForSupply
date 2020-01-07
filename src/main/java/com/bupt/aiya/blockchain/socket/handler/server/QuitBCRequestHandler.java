package com.bupt.aiya.blockchain.socket.handler.server;

import com.bupt.aiya.blockchain.Util.MacAndIP;
import com.bupt.aiya.blockchain.Util.SpringUtil;
import com.bupt.aiya.blockchain.common.AppId;
import com.bupt.aiya.blockchain.core.dao.BC.BCUserDao;
import com.bupt.aiya.blockchain.core.dao.BC.MemberDao;
import com.bupt.aiya.blockchain.core.entity.BC.BCUser;
import com.bupt.aiya.blockchain.socket.base.AbstractBlockHandler;
import com.bupt.aiya.blockchain.socket.body.JoinGroupReqBody;
import com.bupt.aiya.blockchain.socket.packet.HelloPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.tio.core.ChannelContext;

/**
 * Created by aiya on 2019/11/29 上午10:10
 */
public class QuitBCRequestHandler extends AbstractBlockHandler<JoinGroupReqBody> {
    private Logger logger = LoggerFactory.getLogger(QuitBCRequestHandler.class);

    private BCUserDao bcUserDao = SpringUtil.getBean(BCUserDao.class);

    private MemberDao memberDao = SpringUtil.getBean(MemberDao.class);

    @Override
    public Class<JoinGroupReqBody> bodyClass() {
        return JoinGroupReqBody.class;
    }

    @Override
    public Object handler(HelloPacket packet, JoinGroupReqBody bsBody, ChannelContext channelContext) throws Exception {
        BCUser bcUser = bsBody.getBcUser();
        if (bcUser != null) {
            if (!bcUser.getUserMac().equals(MacAndIP.getMacAddress())) {
                //1. 删本地
                int res = bcUserDao.deleteUser(bcUser.getUserId());
                logger.info("节点:{}, 退出结果:{}", bcUser.getUserId(), res);
            }
            //2.
            if (AppId.value.equals("aiya")){
                int ans = memberDao.deleteMember(bcUser.getUserId() + "");
                logger.info("leader节点处理节点:{}, 退出结果:{}", bcUser.getUserId(), ans);
            }
        }
        return null;
    }
}
