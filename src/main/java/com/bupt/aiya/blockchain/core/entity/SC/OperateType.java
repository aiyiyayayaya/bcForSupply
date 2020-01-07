package com.bupt.aiya.blockchain.core.entity.SC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by aiya on 2019/8/27 下午3:51
 */
public enum OperateType {
    PRODUCT("Produce", "生产"),
    QUALITY("QualityInspection", "质检"),
    TANSPORT("Transport", "运输"),
    IN_STORAGE("IntoStorage","入库"),
    OUT_STORAGE("OutStorage", "出库"),
    SALE("Sale", "售卖");
    private String type;
    private String name;


    OperateType(String type, String name) {
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
        for (OperateType opt : OperateType.values()){
            Map<String, String> itemMap = new HashMap<>();
            itemMap.put("type", opt.type);
            itemMap.put("text", opt.name);
            list.add(itemMap);
        }
        return list;
    }
}
