package com.bupt.aiya.blockchain.socket.packet;

import com.bupt.aiya.blockchain.socket.body.BaseBody;
import org.tio.utils.json.Json;

/**
 * @author wuweifeng wrote on 2018/3/12.
 */
public class PacketBuilder<T extends BaseBody> {
    /**
     * 消息类型，其值在Type中定义
     */
    private byte type;

    private T body;

    public PacketBuilder<T> setType(byte type) {
        this.type = type;
        return this;
    }

    public PacketBuilder<T> setBody(T body) {
        this.body = body;
        return this;
    }

    public HelloPacket build() {
        return new HelloPacket(type, Json.toJson(body));
    }
}
