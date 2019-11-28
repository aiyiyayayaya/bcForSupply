package com.mindata.blockchain.socket.handler.abs;

import com.mindata.blockchain.socket.body.BaseBody;
import com.mindata.blockchain.socket.common.Const;
import com.mindata.blockchain.socket.packet.HelloPacket;
import org.tio.core.ChannelContext;
import org.tio.utils.json.Json;

/**
 * Created by aiya on 2019/1/9 上午10:58
 */
public abstract class AbsBlockHandler<T extends BaseBody>{
    public abstract Class<T> bodyClass();

    public AbsBlockHandler(){

    }
    public Object handler(HelloPacket packet, ChannelContext channelContext) throws Exception {
        String jsonStr;
        T baseBody = null;
        if (packet.getBody()!=null){
            jsonStr = new String(packet.getBody(), Const.CHARSET);
            baseBody = Json.toBean(jsonStr, bodyClass());
//            System.out.println("jsonStr========"+jsonStr);
//            System.out.println("baseBody========="+baseBody);
        }
        return handler(packet, baseBody, channelContext);
    }
    /**
     * 实际的handler处理
     * basebody：解析后的对象*/
    public abstract Object handler(HelloPacket packet, T baseBody, ChannelContext channelContext)throws Exception;
}
