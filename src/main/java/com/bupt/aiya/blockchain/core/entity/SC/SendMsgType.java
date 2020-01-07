package com.bupt.aiya.blockchain.core.entity.SC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by aiya on 2019/11/24 下午5:05
 */
public enum SendMsgType {
    PRODUCT_SALE("ProductSale", "供应信息发布"),
    PRODUCT_BUY("ProductBuy","采买信息发布");

    private String type;
    private String name;

    SendMsgType(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<Map<String, String>> getOperateTypeEnums(){
        List<Map<String, String>> list = new ArrayList<>();
        for (SendMsgType msgType : SendMsgType.values()){
            Map<String, String> itemMap = new HashMap<>();
            itemMap.put("type", msgType.type);
            itemMap.put("text", msgType.name);
            list.add(itemMap);
        }
        return list;
    }
}
