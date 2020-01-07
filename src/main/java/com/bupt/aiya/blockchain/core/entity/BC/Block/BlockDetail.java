package com.bupt.aiya.blockchain.core.entity.BC.Block;

/**
 * Created by aiya on 2019/8/27 下午8:51
 */
public class BlockDetail {
    //自增主键
    private Integer id;
    //区块号
    private Integer blockId;
    //物资编号
    private String transId;
    //操作类型
    private String operateType;
    //
    private String responsible;
    private String blockDetail;
    private Long createTime;
    private Long validPeriod;

    public BlockDetail() {
    }
    public String toString(){
        return "BlockDetail = {" +
                "transId = " + transId +
                "operateType = " + operateType +
                "responsible = " + responsible +
                "blockDetail = " + blockDetail +
                "createTime = " + createTime +
                "validPeriod = " + validPeriod +
                "}";
    }

    public BlockDetail(String transId, String operateType, String responsible) {
        this.transId = transId;
        this.operateType = operateType;
        this.responsible = responsible;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBlockId() {
        return blockId;
    }

    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getOperateType() {
        return operateType;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getBlockDetail() {
        return blockDetail;
    }

    public void setBlockDetail(String blockDetail) {
        this.blockDetail = blockDetail;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getValidPeriod() {
        return validPeriod;
    }

    public void setValidPeriod(Long validPeriod) {
        this.validPeriod = validPeriod;
    }
}
