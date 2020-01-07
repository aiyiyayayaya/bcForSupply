package com.bupt.aiya.blockchain.socket.pbft.msg;

/**
 * pbft算法传输prepare和commit消息的载体
 * @author wuweifeng wrote on 2018/4/23.
 */
public class VoteMsg {
    /**
     * 当前投票状态（Prepare，commit）
     */
    private int type;
    /**
     * 区块的hash
     */
    private String hash;
    /**
     * 区块的number
     */
    private int sequence_no;
    /**
     * 是哪个节点传来的
     */
    private String sendId;
    /**
     * 是否同意
     */
    private boolean result;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getSequence_no() {
        return sequence_no;
    }

    public void setSequence_no(int sequence_no) {
        this.sequence_no = sequence_no;
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String toString() {
        return "PbftMsg{"+
                "type = "+type +
                ", hash = " + hash+
                ", sequence_number = "+sequence_no+
                ", sendId = "+sendId+
                ", AgreeOrNot ? "+result+"}";
    }
}
