package com.mindata.blockchain.Util;

import com.mindata.blockchain.User.Myself;
import com.mindata.blockchain.User.User;
import com.mindata.blockchain.core.entity.BC.Block.Block;
import com.mindata.blockchain.core.entity.BC.Block.BlockBody;
import com.mindata.blockchain.core.entity.BC.Block.BlockDetail;
import com.mindata.blockchain.core.entity.BC.Block.BlockHeader;
import com.mindata.blockchain.core.entity.BC.Block.Check.BlockCheckerImpl;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SignatureException;
import java.security.interfaces.ECPublicKey;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by aiya on 2019/11/24 下午11:45
 */
public class MessageSignatureTest {
    Logger logger = LoggerFactory.getLogger(MessageSignatureTest.class);

    @Test
    public void verify() {
        Block  block = new Block();
        BlockHeader bh = new BlockHeader();
        bh.setPubKey(User.Pub2String(Myself.getMyself().getPublicKey()));

        block.setBlockHeader(bh);
        BlockBody bb = new BlockBody();

        ArrayList<BlockDetail> list = new ArrayList<>();
        BlockDetail bd = new BlockDetail();
        list.add(bd);
        bb.setBlockDetails(list);

        try {
            bb.generateSign(list, Myself.getMyself().getPrivateKey());
        } catch (SignatureException e) {
            logger.error( "签名出错" ,e);
        }

        block.setBlockBody(bb);

        BlockCheckerImpl blockChecker = new BlockCheckerImpl();
        try {
            blockChecker.checkSignAndHash(block);
            //logger.info("" + ms.verify(SerializeUtils.serialize(list), bb.getSign(), (ECPublicKey) Myself.publicKey));
        } catch (Exception e) {
            logger.error("签名验证出错", e);
        }
    }
}