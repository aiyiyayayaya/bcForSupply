package com.mindata.blockchain.SupplyChainManagement.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mindata.blockchain.SupplyChainManagement.service.UserService;

import com.mindata.blockchain.core.entity.SC.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aiya on 2019/3/20 上午10:15
 */

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "adduser",method = RequestMethod.POST)
    public Map<String, Object> addUser(@RequestBody User user){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success",userService.addUser(user));
        return modelMap;
    }
    //后台用RequestBody接受json数据
    @PostMapping(value = "login")
    @ResponseBody
    public boolean login(@RequestBody JSONObject object){
        String data = object.toJSONString();
        JSONObject json = JSON.parseObject(data);
        String usrid = json.getString("name");
        String psw = json.getString("psw");
        System.out.println("name = " + usrid);
        System.out.println("psw = " + psw);
        //数据库查找用户名密码是否正确
        if(userService.login(usrid , psw) == true)
            return true;
        else
            return false;
    }

}
