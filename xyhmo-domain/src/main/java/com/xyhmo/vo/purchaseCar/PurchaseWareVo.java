package com.xyhmo.vo.purchaseCar;

import com.xyhmo.domain.WareInfo;

import java.io.Serializable;

public class PurchaseWareVo implements Serializable{

    private static final long serialVersionUID = -1363599281217657046L;

    /**
     * 是否选中
     *
     * */
    Boolean isSelect;
    /**
     * 选中数量
     *
     * */
    Integer selectNum;
    /**
     * 商品对象
     *
     * */
    WareInfo wareInfo;

    public Boolean getSelect() {
        return isSelect;
    }

    public void setSelect(Boolean select) {
        isSelect = select;
    }

    public Integer getSelectNum() {
        return selectNum;
    }

    public void setSelectNum(Integer selectNum) {
        this.selectNum = selectNum;
    }

    public WareInfo getWareInfo() {
        return wareInfo;
    }

    public void setWareInfo(WareInfo wareInfo) {
        this.wareInfo = wareInfo;
    }
}
