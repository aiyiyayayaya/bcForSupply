package com.bupt.aiya.blockchain.socket.handler.client;

import com.bupt.aiya.blockchain.Util.MacAndIP;
import com.bupt.aiya.blockchain.socket.base.AbstractBlockHandler;
import com.bupt.aiya.blockchain.socket.body.DeleteRespBody;
import com.bupt.aiya.blockchain.socket.packet.HelloPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;

import java.util.List;

/**
 * Created by aiya on 2019/11/28 上午8:40
 */
public class DeleteBlockResponseHandler extends AbstractBlockHandler<DeleteRespBody> {
    private Logger logger = LoggerFactory.getLogger(DeleteBlockResponseHandler.class);
    @Override
    public Class<DeleteRespBody> bodyClass() {
        return DeleteRespBody.class;
    }

    @Override
    public Object handler(HelloPacket packet, DeleteRespBody bsBody, ChannelContext channelContext) throws Exception {
        List<String> list = bsBody.getIpList();
        int blockNum = bsBody.getBlockNum();
        String myIp = MacAndIP.getIpAddress();
        if(list.contains(myIp)){
            logger.info("你不能删除区块:{}哦！", blockNum);
        }else {

        }
        return null;
    }
}
