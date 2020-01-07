package com.bupt.aiya.blockchain.socket.packet;

import com.bupt.aiya.blockchain.socket.common.Const;
import org.tio.core.intf.Packet;

import java.io.UnsupportedEncodingException;

/**
 * @author wuweifeng wrote on 2018/3/9.
 */
public class HelloPacket extends Packet {
    /**
     *  消息头的长度 1+4
     */
    public static final int HEADER_LENGTH = 5;
    /**
     * 消息类型，其值在Type中定义
     */
    private byte type;

    private byte[] body;

    public HelloPacket() {
        super();
    }

    /**
     * @param type type
     * @param body body
     * @author tanyaowu
     */
    public HelloPacket(byte type, byte[] body) {
        super();
        this.type = type;
        this.body = body;
    }

    public HelloPacket(byte type, String body) {
        super();
        this.type = type;
        setBody(body);
    }

    /**
     * @return the body
     */
    public byte[] getBody() {
        return body;
    }

    /**
     * @return the type
     */
    public byte getType() {
        return type;
    }

    @Override
    public String logstr() {
        return "" + type;
    }

    /**
     * @param body
     *         the body to set
     */
    public void setBody(byte[] body) {
        this.body = body;
    }

    public void setBody(String body) {
        try {
            this.body = body.getBytes(Const.CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param type
     *         the type to set
     */
    public void setType(byte type) {
        this.type = type;
    }
}
