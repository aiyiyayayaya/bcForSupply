package com.bupt.aiya.blockchain.core.entity.SC;

/**
 * Created by aiya on 2019/12/4 下午7:22
 */
public class CompanyChoose {
    private String companyName;
    private long saleSum;
    private long goodSum;
    private long qualitySum;

    public CompanyChoose() {
    }

    public CompanyChoose(String companyName, long saleSum, long goodSum, long qualitySum) {
        this.companyName = companyName;
        this.saleSum = saleSum;
        this.goodSum = goodSum;
        this.qualitySum = qualitySum;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public long getSaleSum() {
        return saleSum;
    }

    public void setSaleSum(long saleSum) {
        this.saleSum = saleSum;
    }

    public long getGoodSum() {
        return goodSum;
    }

    public void setGoodSum(long goodSum) {
        this.goodSum = goodSum;
    }

    public long getQualitySum() {
        return qualitySum;
    }

    public void setQualitySum(long qualitySum) {
        this.qualitySum = qualitySum;
    }
}
