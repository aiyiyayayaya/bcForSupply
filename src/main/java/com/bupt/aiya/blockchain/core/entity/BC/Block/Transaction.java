package com.bupt.aiya.blockchain.core.entity.BC.Block;

/**
 * Created by aiya on 2018/11/15.
 */
public class Transaction {
    //private String transactionId;
    private String productType;
    private String productId;

    private String operation;

    private String responsible;

    private String state;
    private long timeStamp = System.currentTimeMillis();
    private long validPeriod;

    @Override
    public String toString() {
        return "Transaction{"+
                "productType = "+productType +'\n'+
                ", productId = "+productId + '\n'+
                ", operation = "+ operation+ '\n'+
                ", responsiblue = "+responsible+'\n'+
                ", timestamp = "+timeStamp+'\n'+
                ", state = " + state +'\n'+
                ", validPeriod = "+ validPeriod +'\n'+
                "}";
    }

    /**
     * 构建一条交易，传入各必要参数
     *
     * @return 用私钥签名后的交易
     * **/
//    public BaseData buildTransaction() {
//    }


//    public String getTransactionId() {
//        return transactionId;
//    }
//
//    public void setTransactionId(String transactionId) {
//        this.transactionId = transactionId;
//    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getValidPeriod() {
        return validPeriod;
    }

    public void setValidPeriod(long validPeriod) {
        this.validPeriod = validPeriod;
    }
}
