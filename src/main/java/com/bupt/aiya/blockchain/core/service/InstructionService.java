package com.bupt.aiya.blockchain.core.service;

import cn.hutool.core.bean.BeanUtil;
import com.bupt.aiya.blockchain.common.exception.TrustSDKException;

import org.springframework.stereotype.Service;

/**
 * 一条指令的service
 *
 * @author wuweifeng wrote on 2018/3/7.
 */
@Service
public class InstructionService {
    //TODO 校验
    /**
     * 校验公私钥是不是一对
     *
     * @param instructionBody
     *         instructionBody
     * @return boolean
     * @throws TrustSDKException
     *         TrustSDKException
     */
//    public boolean checkKeyPair(InstructionBody instructionBody) throws TrustSDKException {
//        return TrustSDK.checkPairKey(instructionBody.getPrivateKey(), instructionBody.getPublicKey());
//    }
//
//    /**
//     * 校验内容的合法性
//     * @param instructionBody instructionBody
//     * @return true false
//     */
//    public boolean checkContent(InstructionBody instructionBody) {
//        byte operation = instructionBody.getOperation();
//        if (operation != Operation.ADD && operation != Operation.DELETE && operation != Operation.UPDATE) {
//            return false;
//        }
//        //不是add时，必须要有id和json和原始json
//        return Operation.UPDATE != operation && Operation.DELETE != operation || instructionBody.getInstructionId()
//                != null && instructionBody.getJson() != null && instructionBody.getOldJson() != null;
//    }
//
//    /**
//     * 根据传来的body构建一条指令
//     *
//     * @param instructionBody
//     *         body
//     * @return Instruction
//     */
//    public Instruction build(InstructionBody instructionBody) throws Exception {
//        Instruction instruction = new Instruction();
//        BeanUtil.copyProperties(instructionBody, instruction);
//        if (Operation.ADD == instruction.getOperation()) {
//            instruction.setInstructionId(CommonUtil.generateUuid());
//        }
//        instruction.setTimeStamp(CommonUtil.getNow());
//        String buildStr = getSignString(instruction);
//        //设置签名，供其他人验证
//        instruction.setSign(TrustSDK.signString(instructionBody.getPrivateKey(), buildStr));
//        //设置hash，防止篡改
//        instruction.setHash(Sha256.sha256(buildStr));
//
//        return instruction;
//    }
//
//    private String getSignString(Instruction instruction) {
//    	return instruction.getOperation() + instruction.getTable() + instruction
//    			.getInstructionId() + (instruction.getJson()==null?"":instruction.getJson());
//    }
//
//
//    public boolean checkSign(Instruction instruction) throws TrustSDKException {
//        String buildStr = getSignString(instruction);
//        return TrustSDK.verifyString(instruction.getPublicKey(), buildStr, instruction.getSign());
//    }
//
//    public boolean checkHash(Instruction instruction) {
//        String buildStr = getSignString(instruction);
//        return Sha256.sha256(buildStr).equals(instruction.getHash());
//    }
}
