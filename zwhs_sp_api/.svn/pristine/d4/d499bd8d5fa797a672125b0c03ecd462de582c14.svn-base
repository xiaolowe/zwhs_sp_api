package cn.org.citycloud.zwhs.bean;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public class Coupon {
	
	@Range(min=1,message="优惠劵条件不能为空")
	private BigDecimal couponCondition;
	
	@Range(min=1,message="每人限领数量不能小于1")
	private int couponLimit;
	
	@Range(min=1,message="优惠劵额度不能小于1")
	private BigDecimal couponMoney;
	
	@NotBlank(message="优惠劵名称不能为空")
	private String couponName;
	
	@Range(min=1,message="优惠劵数量不能小于1")
	private int couponNumber;
	
	private String couponDesc="";

	private byte couponStatus=10;

	private String couponSymbol=">=";
	
	@NotNull(message="开始日期不能为空")
	private Date effectiveTime;
	@NotNull(message="结束日期不能为空")
	private Date expirationTime;

	private Date insDate;

	private int storeId;

	private Date updDate;

	public BigDecimal getCouponCondition() {
		return couponCondition;
	}

	public void setCouponCondition(BigDecimal couponCondition) {
		this.couponCondition = couponCondition;
	}

	public int getCouponLimit() {
		return couponLimit;
	}

	public void setCouponLimit(int couponLimit) {
		this.couponLimit = couponLimit;
	}

	public BigDecimal getCouponMoney() {
		return couponMoney;
	}

	public void setCouponMoney(BigDecimal couponMoney) {
		this.couponMoney = couponMoney;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public int getCouponNumber() {
		return couponNumber;
	}

	public void setCouponNumber(int couponNumber) {
		this.couponNumber = couponNumber;
	}

	public byte getCouponStatus() {
		return couponStatus;
	}

	public void setCouponStatus(byte couponStatus) {
		this.couponStatus = couponStatus;
	}

	public String getCouponSymbol() {
		return couponSymbol;
	}

	public void setCouponSymbol(String couponSymbol) {
		this.couponSymbol = couponSymbol;
	}

	public Date getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public Date getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}

	public Date getInsDate() {
		return insDate;
	}

	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public String getCouponDesc() {
		return couponDesc;
	}

	public void setCouponDesc(String couponDesc) {
		this.couponDesc = couponDesc;
	}

	
}
