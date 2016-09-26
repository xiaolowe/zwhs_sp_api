package cn.org.citycloud.zwhs.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the store_pay database table.
 * 
 */
@Entity
@Table(name="store_pay")
@NamedQuery(name="StorePay.findAll", query="SELECT s FROM StorePay s")
public class StorePay implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="pay_id", unique=true, nullable=false)
	private int payId;

	@Column(name="commis_amount")
	private int commisAmount;

	@Column(name="commis_rates")
	private float commisRates;

	@Column(name="company_name", length=50)
	private String companyName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ins_date")
	private Date insDate;

	@Column(name="order_sn", nullable=false, length=30)
	private String orderSn;

	@Column(name="pay_code", nullable=false, length=10)
	private String payCode;

	@Column(name="pay_member_id")
	private int payMemberId;

	@Column(name="pay_member_name", length=30)
	private String payMemberName;

	@Column(name="pay_member_phone", length=11)
	private String payMemberPhone;

	@Column(name="pay_money", precision=10)
	private BigDecimal payMoney;

	@Column(name="pay_sn", nullable=false, length=20)
	private String paySn;

	@Column(name="pay_state")
	private int payState;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="pay_time")
	private Date payTime;

	@Column(length=1000)
	private String remark;

	@Column(name="store_amount")
	private int storeAmount;

	@Column(name="store_id", nullable=false)
	private int storeId;

	@Column(name="store_no", length=50)
	private String storeNo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="upd_date")
	private Date updDate;

	public StorePay() {
	}

	public int getPayId() {
		return this.payId;
	}

	public void setPayId(int payId) {
		this.payId = payId;
	}

	public int getCommisAmount() {
		return this.commisAmount;
	}

	public void setCommisAmount(int commisAmount) {
		this.commisAmount = commisAmount;
	}

	public float getCommisRates() {
		return this.commisRates;
	}

	public void setCommisRates(float commisRates) {
		this.commisRates = commisRates;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Date getInsDate() {
		return this.insDate;
	}

	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}

	public String getOrderSn() {
		return this.orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getPayCode() {
		return this.payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public int getPayMemberId() {
		return this.payMemberId;
	}

	public void setPayMemberId(int payMemberId) {
		this.payMemberId = payMemberId;
	}

	public String getPayMemberName() {
		return this.payMemberName;
	}

	public void setPayMemberName(String payMemberName) {
		this.payMemberName = payMemberName;
	}

	public String getPayMemberPhone() {
		return this.payMemberPhone;
	}

	public void setPayMemberPhone(String payMemberPhone) {
		this.payMemberPhone = payMemberPhone;
	}

	public BigDecimal getPayMoney() {
		return this.payMoney;
	}

	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}

	public String getPaySn() {
		return this.paySn;
	}

	public void setPaySn(String paySn) {
		this.paySn = paySn;
	}

	public int getPayState() {
		return this.payState;
	}

	public void setPayState(int payState) {
		this.payState = payState;
	}

	public Date getPayTime() {
		return this.payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getStoreAmount() {
		return this.storeAmount;
	}

	public void setStoreAmount(int storeAmount) {
		this.storeAmount = storeAmount;
	}

	public int getStoreId() {
		return this.storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getStoreNo() {
		return this.storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public Date getUpdDate() {
		return this.updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

}