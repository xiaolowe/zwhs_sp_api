package cn.org.citycloud.zwhs.bean;

import java.math.BigDecimal;

public class FirstPage {
	
	private BigDecimal accBal = new BigDecimal(0);
	
	private int totaMember=0;
	
	private int totalOrder=0;
	
	private BigDecimal totalAmount = new BigDecimal(0);
	
	private String companyAddress="";
	
	private int sgId;
	
	private String sgName="";

	public int getSgId() {
		return sgId;
	}

	public void setSgId(int sgId) {
		this.sgId = sgId;
	}

	public String getSgName() {
		return sgName;
	}

	public void setSgName(String sgName) {
		this.sgName = sgName;
	}

	public BigDecimal getAccBal() {
		return accBal;
	}

	public void setAccBal(BigDecimal accBal) {
		this.accBal = accBal;
	}

	public int getTotaMember() {
		return totaMember;
	}

	public void setTotaMember(int totaMember) {
		this.totaMember = totaMember;
	}

	public int getTotalOrder() {
		return totalOrder;
	}

	public void setTotalOrder(int totalOrder) {
		this.totalOrder = totalOrder;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}


	
}
