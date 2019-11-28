package com.mindata.blockchain.socket.pbft.queue;

import com.mindata.blockchain.ApplicationContextProvider;
import com.mindata.blockchain.socket.pbft.VoteType;
import com.mindata.blockchain.socket.pbft.msg.VoteMsg;
import org.springframework.stereotype.Component;

/**
 * @author wuweifeng wrote on 2018/4/25.
 */
@Component
public class MsgQueueManager {

    public void pushMsg(VoteMsg voteMsg) {
    	BaseMsgQueue baseMsgQueue = null;
        switch (voteMsg.getType()) {
            case VoteType
                    .PREPREPARE:
                baseMsgQueue = ApplicationContextProvider.getBean(PrePrepareMsgQueue.class);
                break;
            case VoteType.PREPARE:
                baseMsgQueue = ApplicationContextProvider.getBean(PrepareMsgQueue.class);
                break;
            case VoteType.COMMIT:
                baseMsgQueue = ApplicationContextProvider.getBean(CommitMsgQueue.class);
                break;
            default:
                break;
        }
        if(baseMsgQueue != null) {
        	baseMsgQueue.push(voteMsg);
        }
    }
}
