package com.mindata.blockchain.SupplyChainManagement.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mindata.blockchain.SupplyChainManagement.service.BCUserService;
import com.mindata.blockchain.Util.Result;
import com.mindata.blockchain.Util.ResultEnums;
import com.mindata.blockchain.Util.ResultUtil;
import com.mindata.blockchain.core.entity.BC.BCUser;
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
    private String addUser(@RequestBody JSONObject jsonObject) throws Exception {
        logger.info("get a addUserMessage!");
        //自己设定公司名称和id号，service层自动获取mac地址进行判断
        int bcId = jsonObject.getInteger("id");
        String bcName = jsonObject.getString("name");
        Result state = bcuserService.addBCUser(bcId, bcName);
        return JSON.toJSONString(state);
    }

    //new node join to the chain
    @PostMapping(value = "/joinIn")
    @ResponseBody
    public String join2Chain(){
        String message = bcuserService.joinToBlockChain();
        if (message.equals("success")){
            return JSON.toJSONString(ResultUtil.success());
        }
        return JSON.toJSONString(ResultUtil.error(ResultEnums.INSERT_FAIL.getCode(), ResultEnums.INSERT_FAIL.getMsg()));
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
}
