package cn.org.citycloud.zwhs.bean;

import java.math.BigDecimal;

import javax.validation.constraints.Min;

public class Pay {
	private int storeId=0;
	
	private int applyUserId=0;
	
	@Min(0)
	private BigDecimal applyMoney;
	
	private int applyType=1;
	
	private int confirmStat=0;

	public int getApplyType() {
		return applyType;
	}

	public void setApplyType(int applyType) {
		this.applyType = applyType;
	}

	public int getConfirmStat() {
		return confirmStat;
	}

	public void setConfirmStat(int confirmStat) {
		this.confirmStat = confirmStat;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public BigDecimal getApplyMoney() {
		return applyMoney;
	}

	public void setApplyMoney(BigDecimal applyMoney) {
		this.applyMoney = applyMoney;
	}

	public int getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(int applyUserId) {
		this.applyUserId = applyUserId;
	}

	
	
}
