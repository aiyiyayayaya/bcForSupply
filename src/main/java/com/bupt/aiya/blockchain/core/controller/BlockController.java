package com.bupt.aiya.blockchain.core.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bupt.aiya.blockchain.User.Myself;
import com.bupt.aiya.blockchain.Util.MessageSignature;
import com.bupt.aiya.blockchain.Util.ResultEnums;
import com.bupt.aiya.blockchain.Util.ResultUtil;
import com.bupt.aiya.blockchain.Util.SerializeUtils;
import com.bupt.aiya.blockchain.core.bean.BaseData;
import com.bupt.aiya.blockchain.core.bean.ResultGenerator;
import com.bupt.aiya.blockchain.core.entity.BC.Block.Block;
import com.bupt.aiya.blockchain.core.entity.BC.Block.BlockBody;
import com.bupt.aiya.blockchain.core.entity.BC.Block.BlockDetail;
import com.bupt.aiya.blockchain.core.entity.BC.Block.BlockHeader;
import com.bupt.aiya.blockchain.core.entity.BC.Block.Check.BlockChecker;
import com.bupt.aiya.blockchain.core.entity.SC.CompanyChoose;
import com.bupt.aiya.blockchain.core.entity.SC.OperateType;
import com.bupt.aiya.blockchain.core.entity.SC.SendMsgType;
import com.bupt.aiya.blockchain.core.manager.DbBlockManager;
import com.bupt.aiya.blockchain.core.service.InstructionService;
import com.bupt.aiya.blockchain.core.service.bc.BlockService;
import com.bupt.aiya.blockchain.socket.body.RequestBlockBody;
import com.bupt.aiya.blockchain.socket.body.RpcBlockBody;
import com.bupt.aiya.blockchain.socket.body.RpcSimpleBlockBody;
import com.bupt.aiya.blockchain.socket.client.PacketSender;
import com.bupt.aiya.blockchain.socket.packet.HelloPacket;
import com.bupt.aiya.blockchain.socket.packet.PacketBuilder;
import com.bupt.aiya.blockchain.socket.packet.PacketType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


import java.security.SignatureException;
import java.security.interfaces.ECPublicKey;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wuweifeng wrote on 2018/3/7.
 */
@RestController
@RequestMapping("/block")
public class BlockController {
    @Resource
    private BlockService blockService;
    @Resource
    private PacketSender packetSender;
    @Resource
    private DbBlockManager dbBlockManager;
    @Resource
    private InstructionService instructionService;
    @Resource
    private BlockChecker blockChecker;

    private Logger logger = LoggerFactory.getLogger(BlockController.class);



    @PostMapping("/toAddBlockHtml")
    @ResponseBody()
    public String toAddBlock(){
        Map<String, Object> map = new HashMap<>();
        map.put("operateList", OperateType.getOperateTypeEnums());
        return JSON.toJSONString(map);
    }

    @PostMapping("/toSendMsgHtml")
    @ResponseBody
    public String toSendMsg(){
        Map<String, Object> map = new HashMap<>();
        map.put("msgTypeList",SendMsgType.getOperateTypeEnums());
        return JSON.toJSONString(map);
    }


    /**
     * 添加一个block，需要先在InstructionController构建1-N个instruction指令，然后调用该接口生成Block
     *         指令的集合
     * @return 结果
     */
    @PostMapping("/add")
    @ResponseBody
    public String  addBlock(HttpServletRequest request) throws Exception {
        //封装接收到的blockdetail
        BlockDetail blockDetail = new BlockDetail(
                request.getParameter("productId"),
                request.getParameter("operateType"),
                request.getParameter("responsible"));
        blockDetail.setCreateTime(System.currentTimeMillis());
        Date d = new Date(blockDetail.getCreateTime());
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, Integer.parseInt(request.getParameter("validPeriod")));
        blockDetail.setValidPeriod(c.getTimeInMillis());
        //封装blockBody
        Map<String, String> map = new HashMap<>();
        if (blockDetail.getOperateType().equals(OperateType.PRODUCT.getType())){
            map.put("productAddress", request.getParameter("productAddress"));
            map.put("rawMaterial", request.getParameter("rawMaterial"));
        }else if (blockDetail.getOperateType().equals(OperateType.QUALITY.getType())){
            map.put("qualityInstitute", request.getParameter("qualityInstitute"));
            map.put("qualityMsg", request.getParameter("qualityMsg"));
        }else if (blockDetail.getOperateType().equals(OperateType.TANSPORT.getType())){
            map.put("transAddress", request.getParameter("transAddress"));
        }else if (blockDetail.getOperateType().equals(SendMsgType.PRODUCT_SALE.getType()) ||
                blockDetail.getOperateType().equals(SendMsgType.PRODUCT_BUY.getType())
                ){
            map.put("callNum", request.getParameter("callNum"));
            map.put("productName", request.getParameter("productName"));
            map.put("requirement", request.getParameter("requirement"));
            map.put("num", request.getParameter("num"));
        }else {
            
        }
        blockDetail.setBlockDetail(JSON.toJSONString(map));
        RequestBlockBody requestBlockBody = new RequestBlockBody();
        requestBlockBody.setPublicKey(Base64.getEncoder().encodeToString(Myself.getMyself().getPublicKey().getEncoded()));

        BlockBody blockBody = new BlockBody();
        ArrayList<BlockDetail> blockDetails = new ArrayList<>();
        blockDetails.add(blockDetail);
        blockBody.setBlockDetails(blockDetails);
        blockBody.generateSign(blockDetails,Myself.getMyself().getPrivateKey());
        /**
         * 测试验证签名
         * */
        MessageSignature messageSignature = new MessageSignature();
        boolean res = messageSignature.verify(SerializeUtils.serialize(blockBody.getBlockDetails()),
                blockBody.getSign(),
                (ECPublicKey) Myself.getMyself().getPublicKey());
        requestBlockBody.setBlockBody(blockBody);

        System.out.println("测试验证签名结果" + res);
        System.out.println("before :");
        System.out.println("1. " + blockBody.getBlockDetails().toString());
        System.out.println("2. " + Base64.getEncoder().encodeToString(blockBody.getSign()));
        System.out.println("3. " + Myself.getMyself().getPublicKey());
        Block block = blockService.addBlock(requestBlockBody);
        logger.info("执行到最后了！");
        if(block == null)
            return JSON.toJSONString("error");
        return JSON.toJSONString( "生成区块成功！");
    }

    @PostMapping("/getAllBlock")
    @ResponseBody
    public String getAllBlock(){
        List<BlockHeader> list = new ArrayList<>();
        try{
            list = blockService.getAllBlock();
        }catch (Exception e){
            return JSON.toJSONString(ResultUtil.error(ResultEnums.RESULT_IS_NULL.getCode(), ResultEnums.RESULT_IS_NULL.getMsg()));
        }
        if (list.size() == 0){
            return JSON.toJSONString(ResultUtil.error(ResultEnums.RESULT_IS_NULL.getCode(), ResultEnums.RESULT_IS_NULL.getMsg()));
        }else {
            return JSON.toJSONString(ResultUtil.success(list));
        }
    }

    /**
     * 查询区块信息
     * */
    @PostMapping("/search")
    @ResponseBody
    public String searchBlock(@RequestBody JSONObject jsonObject) throws Exception {
        String type = jsonObject.getString("typeOfSelect");
        List<BlockHeader> list = new ArrayList<>();
        Map<String, Object> result = new HashMap<>();

        if(type.equals("block")) {
            int data = jsonObject.getInteger("blockNum");

            //根据区块号查询区块头信息
            BlockHeader blockHeader = null;
            try {
                blockHeader = blockService.selectHeadByNum(data);
                list.add(blockHeader);
                result.put("resultList", list);
                result.put("total", 1);
                return JSON.toJSONString(result);
            }catch (Exception e){
                logger.error("查询区块号:{}为空" +data, e);
                result.put("msg", "查询结果为空！");
            }
        }
        else{
            String data = jsonObject.getString("blockNum");
            //根据商品编号查询区块头信息；会查到多个区块
            list = blockService.selectByTransId(data);
            result.put("resultList", list);
            for (BlockHeader bh : list){
                Long timestamp = bh.getTimeStamp();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = format.format(timestamp);
                bh.setMerkleRoot(time);
            }
            result.put("total", list.size());
            return JSON.toJSONString(result);
        }
        return JSON.toJSONString(ResultEnums.RESULT_IS_NULL.getMsg());
    }

    @PostMapping("/querySupply")
    @ResponseBody
    public String findSupply(@RequestBody JSONObject jsonObject){
        String type = jsonObject.getString("typeOfSelect");
        String goodsType = jsonObject.getString("goodsType");

        Map<String, Object> result = new HashMap<>();
        List<BlockDetail> list = blockService.selectByOpTypeAndBid(type, goodsType);
        if (list != null && list.size() > 0){
            result.put("resultList", list);
            return JSON.toJSONString(result);
        }

        return JSON.toJSONString(ResultEnums.RESULT_IS_NULL.getMsg());
    }


    @PostMapping("/queryDetail")
    @ResponseBody
    public String queryDetail(@RequestBody JSONObject jsonObject){
        int blockNumber = jsonObject.getInteger("blockNum");
        Block block = blockService.selectDetailByNum(blockNumber);
        List<BlockDetail> list = block.getBlockBody().getBlockDetails();
        for (BlockDetail bd : list){
            Long timestamp = bd.getCreateTime();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = format.format(timestamp);
        }
        return JSON.toJSONString(ResultUtil.success(list));
    }

    //todo 区块删除逻辑
    @GetMapping
    public void deleteDetail(){
        //入口
        blockService.deleteDetail();
    }

    /**
     * 供应商对比
     * 1. 返回销售总量
     * 2. 返回质检总量&合格总量
     * */
    @PostMapping(value = "/chooseSupply")
    @ResponseBody
    public String chooseSupply(@RequestBody JSONObject jsonObject){
        String data = jsonObject.toJSONString();
        String companyN1 = jsonObject.getString("company1");
        String companyN2 = jsonObject.getString("company2");
        List<CompanyChoose> list = new ArrayList();
        CompanyChoose company1 = new CompanyChoose("北京市A公司", 1509, 399, 430);
        CompanyChoose company2 = new CompanyChoose("河北省B公司", 1394, 410, 455);
        list.add(company1);
        list.add(company2);
        return JSON.toJSONString(ResultUtil.success(list));
    }

    @PostMapping("/getFromOther")
    @ResponseBody
    public void getBlockFromOther(@RequestBody JSONObject jsonObject){
        String blockHash = jsonObject.getString("blockHash");
        HelloPacket packet = new PacketBuilder<RpcSimpleBlockBody>()
                .setType(PacketType.FETCH_BLOCK_INFO_REQUEST)
                .setBody(new RpcSimpleBlockBody(blockHash)).build();
        packetSender.sendGroup(packet);
    }
    /**
     * 测试生成一个update:Block，公钥私钥可以通过PairKeyController来生成
     * @param id 更新的主键
     * @param content
     * sql内容
     */
//    @GetMapping("testUpdate")
//    public BaseData testUpdate(String id,String content) throws Exception {
//    	if(StringUtils.isBlank(id)) ResultGenerator.genSuccessResult("主键不可为空");
//    	InstructionBody instructionBody = new InstructionBody();
//    	instructionBody.setOperation(Operation.UPDATE);
//    	instructionBody.setTable("message");
//    	instructionBody.setInstructionId(id);
//    	instructionBody.setJson("{\"content\":\"" + content + "\"}");
//    	instructionBody.setPublicKey("A8WLqHTjcT/FQ2IWhIePNShUEcdCzu5dG+XrQU8OMu54");
//    	instructionBody.setPrivateKey("yScdp6fNgUU+cRUTygvJG4EBhDKmOMRrK4XJ9mKVQJ8=");
//    	Instruction instruction = instructionService.build(instructionBody);
//
//    	RequestBlockBody blockRequestBody = new RequestBlockBody();
//    	blockRequestBody.setPublicKey(instructionBody.getPublicKey());
//    	com.mindata.blockchain.block.BlockBody blockBody = new com.mindata.blockchain.block.BlockBody();
//    	blockBody.setInstructions(CollectionUtil.newArrayList(instruction));
//
//    	blockRequestBody.setBlockBody(blockBody);
//
//    	return ResultGenerator.genSuccessResult(blockService.addBlock(blockRequestBody));
//    }
    
    /**
     * 测试生成一个delete:Block，公钥私钥可以通过PairKeyController来生成
     * @param id 待删除记录的主键
     * sql内容
     */
//    @GetMapping("testDel")
//    public BaseData testDel(String id) throws Exception {
//    	if(StringUtils.isBlank(id)) ResultGenerator.genSuccessResult("主键不可为空");
//    	InstructionBody instructionBody = new InstructionBody();
//    	instructionBody.setOperation(Operation.DELETE);
//    	instructionBody.setTable("message");
//    	instructionBody.setInstructionId(id);
//    	instructionBody.setPublicKey("A8WLqHTjcT/FQ2IWhIePNShUEcdCzu5dG+XrQU8OMu54");
//    	instructionBody.setPrivateKey("yScdp6fNgUU+cRUTygvJG4EBhDKmOMRrK4XJ9mKVQJ8=");
//    	Instruction instruction = instructionService.build(instructionBody);
//
//    	RequestBlockBody blockRequestBody = new RequestBlockBody();
//    	blockRequestBody.setPublicKey(instructionBody.getPublicKey());
//    	com.mindata.blockchain.block.BlockBody blockBody = new com.mindata.blockchain.block.BlockBody();
//    	blockBody.setInstructions(CollectionUtil.newArrayList(instruction));
//
//    	blockRequestBody.setBlockBody(blockBody);
//
//    	return ResultGenerator.genSuccessResult(blockService.addBlock(blockRequestBody));
//    }

    /**
     * 查询已落地的sqlite里的所有数据
     */
//    @GetMapping("sqlite")
//    public BaseData sqlite() {
//        return ResultGenerator.genSuccessResult(messageManager.findAll());
//    }

    /**
     * 查询已落地的sqlite里content字段
     */
//    @GetMapping("sqlite/content")
//    public BaseData content() {
//        return ResultGenerator.genSuccessResult(messageManager.findAllContent());
//    }

    /**
     * 获取最后一个block的信息
     */
//    @GetMapping("db")
//    public BaseData getRockDB() {
//        return ResultGenerator.genSuccessResult(dbBlockManager.getLastBlock());
//    }

    /**
     * 手工执行区块内sql落地到sqlite操作
     * @param pageable
     * 分页
     * @return
     * 已同步到哪块了的信息
     */
//    @GetMapping("sync")
//    public BaseData sync(@PageableDefault Pageable pageable) {
//        ApplicationContextProvider.publishEvent(new DbSyncEvent(""));
//        return ResultGenerator.genSuccessResult(syncManager.findAll(pageable));
//    }
    
    /**
     * 全量检测区块是否正常
     * @return
     * null - 通过
     * hash - 第一个异常hash
     */
    @GetMapping("/checkb")
    public BaseData checkAllBlock() {

        Block block = null;
        try {
            block = dbBlockManager.getFirstBlock();
        } catch (Exception e) {
            logger.error("检查出错,block = {}",block, e);
        }

        String hash = null;
    	while(block != null && hash == null) {
    	    //todo block hash == null
    		hash = blockChecker.checkBlock(block);
            try {
                block = dbBlockManager.getNextBlock(block);
                logger.info("检查成功,block = {}", block);
            } catch (Exception e) {
                logger.error("检查出错,blockHash = {}, block = {}",block.getHash(), block, e);
            }
        }
        if (hash == null) {
            hash = "您的信息已通过验证";
        }
    	return ResultGenerator.genSuccessResult(hash);
    }

    @GetMapping("/next")
    public BaseData nextBlock() {
        Block block = null;
        try {
            block = dbBlockManager.getFirstBlock();
            HelloPacket packet = new PacketBuilder<RpcBlockBody>()
                    .setType(PacketType.NEXT_BLOCK_INFO_REQUEST)
                    .setBody(new RpcBlockBody(block)).build();
            packetSender.sendGroup(packet);
            return null;
        } catch (SignatureException e) {
            return null;
        }
    }
}
