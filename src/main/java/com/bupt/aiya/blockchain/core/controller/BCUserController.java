package com.bupt.aiya.blockchain.core.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bupt.aiya.blockchain.SupplyChainManagement.service.BCUserService;
import com.bupt.aiya.blockchain.Util.MacAndIP;
import com.bupt.aiya.blockchain.Util.Result;
import com.bupt.aiya.blockchain.Util.ResultEnums;
import com.bupt.aiya.blockchain.Util.ResultUtil;
import com.bupt.aiya.blockchain.core.entity.BC.BCUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


/**
 * Created by aiya on 2019/3/20 上午10:29
 */
@RestController
@RequestMapping("/bcuser")
public class BCUserController {

    @Autowired
    private BCUserService bcuserService;
    private static Logger logger = LoggerFactory.getLogger(BCUserController.class);

    @PostMapping(value = "/addbcuser")
    @ResponseBody
    public String addUser(@RequestBody JSONObject jsonObject) throws Exception {
        logger.info("get a addUserMessage!");
        //自己设定公司名称和id号，service层自动获取mac地址进行判断
        int bcId = jsonObject.getInteger("id");
        String bcName = jsonObject.getString("name");
        Result state = bcuserService.addBCUser(bcId, bcName);
        return JSON.toJSONString(state);
    }


    /**
     * 退出区块链逻辑：
     * 1. 删除本地BCUser
     * 2. 删除Member
     * */
    @PostMapping("/deleteBCUser")
    @ResponseBody
    public String deleteBCUser(){
        String mac = MacAndIP.getMacAddress();
        String ans = bcuserService.deleteBCUser(mac);
        return JSON.toJSONString(ResultUtil.success(ans));
    }

    @PostMapping("/getAllUser")
    @ResponseBody
    public String getAllUser(){
        ArrayList<BCUser> list = new ArrayList<>();
        try{
            list = new ArrayList<>(bcuserService.getIpList());

        }catch (Exception e){
            return JSON.toJSONString(ResultUtil.error(ResultEnums.RESULT_IS_NULL.getCode(), ResultEnums.RESULT_IS_NULL.getMsg()));
        }
        if (list.size() == 0) {
            return JSON.toJSONString(ResultUtil.error(ResultEnums.RESULT_IS_NULL.getCode(), ResultEnums.RESULT_IS_NULL.getMsg()));
        }else {
            return JSON.toJSONString(ResultUtil.success(list));
        }
    }

    @PostMapping("/getWaitingUser")
    @ResponseBody
    public String getWaitingUser(){
        ArrayList<BCUser> list = new ArrayList<>();
        try{
            list = new ArrayList<BCUser>(bcuserService.getWaitingList());

        }catch (Exception e){
            return JSON.toJSONString(ResultUtil.error(ResultEnums.RESULT_IS_NULL.getCode(), ResultEnums.RESULT_IS_NULL.getMsg()));
        }
        if (list.size() == 0) {
            return JSON.toJSONString(ResultUtil.error(ResultEnums.RESULT_IS_NULL.getCode(), ResultEnums.RESULT_IS_NULL.getMsg()));
        }else {
            return JSON.toJSONString(ResultUtil.success(list));
        }
    }

    /**
     * 申请逻辑：
     * 把节点信息发给leader节点做申请
     * */
    @PostMapping(value = "/doApply")
    @ResponseBody
    public String doApply(){
        String res = bcuserService.doApply();
        if (res.equals("success")){
            return JSON.toJSONString(ResultUtil.success());
        }
        return JSON.toJSONString(ResultUtil.error(ResultEnums.INSERT_FAIL.getCode(), ResultEnums.INSERT_FAIL.getMsg()));
    }

    /**
     * new node join to the chain
     * 处理请求的逻辑：
     * 1. 把要允许加入的id传进来
     * 2. 广播joinrequest
     * 3. 把这条记录删掉
     * */
    @PostMapping(value = "/joinIn")
    @ResponseBody
    public String join2Chain(@RequestBody JSONObject jsonObject){
        int bcId = jsonObject.getInteger("bcId");

        String message = bcuserService.joinToBlockChain(bcId);
        if (message.equals("success")){
            return JSON.toJSONString(ResultUtil.success());
        }
        return JSON.toJSONString(ResultUtil.error(ResultEnums.INSERT_FAIL.getCode(), ResultEnums.INSERT_FAIL.getMsg()));
    }
}
