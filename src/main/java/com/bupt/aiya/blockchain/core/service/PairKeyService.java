package com.bupt.aiya.blockchain.core.service;

import com.bupt.aiya.blockchain.common.TrustSDK;
import com.bupt.aiya.blockchain.common.exception.TrustSDKException;
import com.bupt.aiya.blockchain.core.entity.BC.PairKey;
import org.springframework.stereotype.Service;

/**
 * @author wuweifeng wrote on 2018/3/7.
 */
@Service
public class PairKeyService {

    /**
     * 生成公私钥对
     * @return PairKey
     * @throws TrustSDKException TrustSDKException
     */
    public PairKey generate() throws TrustSDKException {
        return TrustSDK.generatePairKey(true);
    }
}
