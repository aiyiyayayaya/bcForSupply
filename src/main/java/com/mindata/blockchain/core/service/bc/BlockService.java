package com.mindata.blockchain.core.service.bc;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;

import com.mindata.blockchain.Util.MerkleTree;
import com.mindata.blockchain.Util.StringUtil;
import com.mindata.blockchain.common.LeaderConstant;
import com.mindata.blockchain.core.dao.BC.BCUserDao;
import com.mindata.blockchain.core.dao.BC.BlockDao;
import com.mindata.blockchain.core.dao.BC.NewBlockDetailDao;
import com.mindata.blockchain.core.entity.BC.BCUser;
import com.mindata.blockchain.core.entity.BC.Block.Block;
import com.mindata.blockchain.core.entity.BC.Block.BlockBody;
import com.mindata.blockchain.core.entity.BC.Block.BlockDetail;
import com.mindata.blockchain.core.entity.BC.Block.BlockHeader;
import com.mindata.blockchain.core.entity.BC.BlockIndexPojo;
import com.mindata.blockchain.core.manager.DbBlockManager;
import com.mindata.blockchain.socket.body.DeleteReqBody;
import com.mindata.blockchain.socket.body.RequestBlockBody;
import com.mindata.blockchain.socket.body.RpcBlockBody;
import com.mindata.blockchain.socket.client.PacketSender;
import com.mindata.blockchain.socket.message.MessageType;
import com.mindata.blockchain.socket.packet.HelloPacket;
import com.mindata.blockchain.socket.packet.PacketBuilder;
import com.mindata.blockchain.socket.packet.PacketType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by aiya on 2019/1/20 下午1:25
 */
@Service
public class BlockService {
    private Logger logger = LoggerFactory.getLogger(BlockService.class);

    @Autowired
    private BlockDao blockDao;

    @Autowired
    private NewBlockDetailDao newBlockDetailDao;

    @Autowired
    private BCUserDao bcUserDao;

    @Resource
    private DbBlockManager dbBlockManager;
    @Resource
    private PacketSender packetSender;


    public List<BlockHeader> getAllBlock() {
        ArrayList<BlockHeader> list;
        list = new ArrayList<BlockHeader>(blockDao.getAll());//new ArrayList<String>(bcUserDao.selectUserIpList());
        return list;
    }

    /**
     * 添加区块
     * Desc:这个时候只有请求区块体信息
     *
     * */
    public Block addBlock(RequestBlockBody requestBlockBody) throws Exception {
        BlockHeader blockHeader = new BlockHeader();
        BlockBody blockBody = requestBlockBody.getBlockBody();
        List<BlockDetail> transactions = blockBody.getBlockDetails();
        //得到区块号
        blockHeader.setBlockNumber(dbBlockManager.getLastBlockNumber()+1);
        long maxPeriod = 0;
        for(BlockDetail tran : transactions){
            tran.setBlockId(blockHeader.getBlockNumber());
            if(tran.getValidPeriod() > maxPeriod) {
                maxPeriod = tran.getValidPeriod();
            }
        }
        List<String> hashList = transactions.stream().map(BlockDetail::toString).collect(Collectors.toList());
        //set BlockHead
        //以毫秒为单位
        blockHeader.setTimeStamp(System.currentTimeMillis());
        blockHeader.setPubKey(requestBlockBody.getPublicKey());
        MerkleTree merkleTree = new MerkleTree(hashList);
        merkleTree.merkle_tree();
        blockHeader.setMerkleRoot(merkleTree.getRoot());
        blockHeader.setPreHash(dbBlockManager.getLastBlockHash());
        blockHeader.setMaxPeriod(maxPeriod);
        blockHeader.setTable("blockDetail");

        //这是区块体部分的hash值
        blockHeader.setMyHash(StringUtil.applySha256(blockBody.toString()));

        Block block = new Block();
        block.setBlockHeader(blockHeader);
        block.setBlockBody(blockBody);
        block.setHash(StringUtil.applySha256(blockHeader.toString() + blockBody.toString()));

        HelloPacket helloPacket = new PacketBuilder<>().setType(PacketType.GENERATE_BLOCK_REQUEST).setBody(new RpcBlockBody(block)).build();
        packetSender.sendGroup(helloPacket);
        System.out.println("End service...");
        return block;
    }

    /**根据物资id查到一堆blockDetail*/
    public List<BlockHeader> selectByTransId(String number){
        List<BlockDetail> list = newBlockDetailDao.selectByTransId(number);
        List<BlockHeader> result = new ArrayList<>();
        //遍历 得到所属的区块号->区块头
        for (BlockDetail bd : list){
            BlockHeader tmp = selectHeadByNum(bd.getBlockId());
            result.add(tmp);
        }
        return result;
    }

    /**
     * 按操作类型 & 业务id查区块详情
     * ---也会得到一堆blockDetail
     * */

    public List<BlockDetail> selectByOpTypeAndBid(String type, String bId){
        List<BlockDetail> list = newBlockDetailDao.selectByOpTypeAndBid(type, bId);
        return list;
    }

    //根据blockNum查区块
    public Block selectDetailByNum(int number){
        //1. 查区块头
        BlockHeader blockHeader = selectHeadByNum(number);
        if (blockHeader == null)
            return null;
        //2. 查区块内容
        List<BlockDetail> lists = new ArrayList<BlockDetail>(newBlockDetailDao.selectBlockDetailList(number));
                //blockDetailDao.getDetail(number));
        BlockBody blockBody = new BlockBody();
        blockBody.setBlockDetails(lists);
        blockBody.setSign(Base64.getDecoder().decode(blockHeader.getBodySign()));
        Block block = new Block();
        block.setBlockHeader(blockHeader);
        block.setBlockBody(blockBody);
        return block;
    }

    //根据区块哈希值分别查头部和body 拼装区块
    public Block selectDetailByHash(String hash){
        BlockHeader blockHeader = selectTableByHash(hash);
        List<BlockDetail> lists = new ArrayList<BlockDetail>(newBlockDetailDao.selectBlockDetailList(blockHeader.getBlockNumber()));
        //blockDetailDao.getDetailByHash(hash));
        BlockBody blockBody = new BlockBody();
        blockBody.setBlockDetails(lists);
        System.out.println("null???"+Base64.getDecoder().decode(blockHeader.getBodySign()));
        blockBody.setSign(Base64.getDecoder().decode(blockHeader.getBodySign()));
        Block block = new Block();
        block.setBlockHeader(blockHeader);
        block.setBlockBody(blockBody);
        return block;
    }

    //根据区块号查区块头
    public BlockHeader selectHeadByNum(int number){
        BlockHeader blockHeader = blockDao.getByNum(number);
        return blockHeader;
    }

    //根据hash值查区块头
    public BlockHeader selectTableByHash(String hash){
        BlockHeader blockHeader = blockDao.getByHash(hash);
        return blockHeader;
    }

    public BlockIndexPojo getLastBlock(){
        return blockDao.getLastBlock();
    }

    public void deleteDetail(){
        BlockIndexPojo lastBlock = getLastBlock();

        //从第一个区块开始清除信息
        for(int i = 1; i < lastBlock.getBlockNumber(); i++){
            BlockHeader tmp = selectHeadByNum(i);
            //没有被清除
            if(!tmp.isFlag()){
                String hash = blockDao.getByNum(i).getMyHash();
                //已经失效了
                if(tmp.getMaxPeriod()+tmp.getTimeStamp() > System.currentTimeMillis()){
                    //给leader节点发消息：
                    DeleteReqBody body = new DeleteReqBody();
                    body.setBlockNum(i);
                    body.setBlockHash(hash);
                    HelloPacket delReq = new PacketBuilder<>().setType(PacketType.DELETE_BLOCKBODY_INFO_REQUEST).setBody(body).build();
                    packetSender.send2Someone(delReq, LeaderConstant.LEADER_IP.getLeaderIp(), LeaderConstant.LEADER_IP.getSocket());
                }
            }
        }
    }


    public String check(RequestBlockBody requestBlockBody) {
        //TODO check if the publicKey is correct
        if (requestBlockBody == null || requestBlockBody.getBlockBody() == null || StrUtil.isEmpty(requestBlockBody.getPublicKey()))
            return "请求参数缺失";
        List<BlockDetail> transactions = requestBlockBody.getBlockBody().getBlockDetails();
        if (CollectionUtil.isEmpty(transactions))
            return "交易信息不能为空";
        return null;
    }
}
