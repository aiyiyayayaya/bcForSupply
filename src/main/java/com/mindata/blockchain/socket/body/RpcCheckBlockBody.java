package com.mindata.blockchain.socket.body;


import com.mindata.blockchain.core.entity.BC.Block.Block;

/**
 * Created by aiya on 2019/1/9 下午3:33
 * 校验Block是否合法
 * 同意/拒绝区块生成请求
 */
public class RpcCheckBlockBody extends RpcBlockBody {
    /**
     * 0是正常同意，-1区块number错误，-2没有权限，-3hash错误，-4时间错误，-10不合法的next block
     */
    private int code;
    /**
     * 附带的message
     */
    private String message;

    public RpcCheckBlockBody() {
    }

    public RpcCheckBlockBody(int code, String message) {
        this(code, message, null);
    }

    public RpcCheckBlockBody(int code, String message, Block block) {
        super(block);
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RpcCheckBlockBody{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
