package com.mindata.blockchain.socket.body;

import com.mindata.blockchain.socket.pbft.msg.VoteMsg;

/**
 * pbft投票
 * @author wuweifeng wrote on 2018/4/25.
 */
public class PbftVoteBody extends BaseBody {
    private VoteMsg voteMsg;

    public PbftVoteBody() {
        super();
    }

    public PbftVoteBody(VoteMsg voteMsg) {
        super();
        this.voteMsg = voteMsg;
    }

    public VoteMsg getVoteMsg() {
        return voteMsg;
    }

    public void setVoteMsg(VoteMsg voteMsg) {
        this.voteMsg = voteMsg;
    }
}
