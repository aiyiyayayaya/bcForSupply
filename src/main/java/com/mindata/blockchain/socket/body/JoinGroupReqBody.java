package com.mindata.blockchain.socket.body;

import com.mindata.blockchain.core.entity.BC.BCUser;

/**
 * Created by aiya on @date2018/12/14 下午4:16
 */
public class JoinGroupReqBody extends BaseBody {
    private BCUser bcUser;

    public JoinGroupReqBody() {
    }

    public JoinGroupReqBody(BCUser bcUser) {
        this.bcUser = bcUser;
    }

    public BCUser getBcUser() {
        return bcUser;
    }

    public void setBcUser(BCUser bcUser) {
        this.bcUser = bcUser;
    }
}
