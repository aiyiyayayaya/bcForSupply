package com.mindata.blockchain.socket.handler.abs;

import com.mindata.blockchain.socket.body.BaseBody;
import com.mindata.blockchain.socket.common.Const;
import com.mindata.blockchain.socket.packet.HelloPacket;
import org.tio.core.ChannelContext;
import org.tio.utils.json.Json;

import java.io.UnsupportedEncodingException;

/**
 * Created by aiya on 2018/12/14 下午4:33
 */
public abstract class AbsHelloBaseHandler<T extends BaseBody> implements HelloBaseHandler {
    public abstract Class<T> bodyClass();
    @Override
    public Object handler(HelloPacket packet, ChannelContext channelContext) throws Exception {
        String jsonStr = null;
        T baseBody = null;
        if (packet.getBody() != null){
            jsonStr = new String(packet.getBody(), Const.CHARSET);
            baseBody = Json.toBean(jsonStr, bodyClass());
        }
        return handler(packet, baseBody, channelContext);
    }
    public abstract Object handler(HelloPacket packet, T baseBody, ChannelContext channelContext) throws UnsupportedEncodingException;
}
