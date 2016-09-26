package cn.org.citycloud.zwhs.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the evaluate_goods database table.
 * 
 */
@Entity
@Table(name="evaluate_goods")
@NamedQuery(name="EvaluateGood.findAll", query="SELECT e FROM EvaluateGood e")
public class EvaluateGood implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="geval_id", unique=true, nullable=false)
	private int gevalId;

	@Column(name="company_name", length=50)
	private String companyName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="geval_addtime", nullable=false)
	private Date gevalAddtime;

	@Column(name="geval_content", length=255)
	private String gevalContent;

	@Column(name="geval_explain", length=255)
	private String gevalExplain;

	@Column(name="geval_image", length=255)
	private String gevalImage;

	@Column(name="geval_isanonymous", nullable=false)
	private byte gevalIsanonymous;

	@Column(name="geval_remark", length=255)
	private String gevalRemark;

	@Column(name="geval_scores", nullable=false)
	private byte gevalScores;

	@Column(name="geval_state", nullable=false)
	private byte gevalState;

	@Column(name="goods_id", nullable=false)
	private int goodsId;

	@Column(name="goods_name", nullable=false, length=50)
	private String goodsName;

	@Column(name="goods_price", nullable=false, precision=10, scale=2)
	private BigDecimal goodsPrice;

	@Column(name="member_id")
	private int memberId;

	@Column(name="member_truename", length=20)
	private String memberTruename;

	@Column(name="order_goods_id", nullable=false)
	private int orderGoodsId;

	@Column(name="order_id", nullable=false)
	private int orderId;

	@Column(name="order_type")
	private int orderType;

	@Column(name="store_id", nullable=false)
	private int storeId;

	public EvaluateGood() {
	}

	public int getGevalId() {
		return this.gevalId;
	}

	public void setGevalId(int gevalId) {
		this.gevalId = gevalId;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Date getGevalAddtime() {
		return this.gevalAddtime;
	}

	public void setGevalAddtime(Date gevalAddtime) {
		this.gevalAddtime = gevalAddtime;
	}

	public String getGevalContent() {
		return this.gevalContent;
	}

	public void setGevalContent(String gevalContent) {
		this.gevalContent = gevalContent;
	}

	public String getGevalExplain() {
		return this.gevalExplain;
	}

	public void setGevalExplain(String gevalExplain) {
		this.gevalExplain = gevalExplain;
	}

	public String getGevalImage() {
		return this.gevalImage;
	}

	public void setGevalImage(String gevalImage) {
		this.gevalImage = gevalImage;
	}

	public byte getGevalIsanonymous() {
		return this.gevalIsanonymous;
	}

	public void setGevalIsanonymous(byte gevalIsanonymous) {
		this.gevalIsanonymous = gevalIsanonymous;
	}

	public String getGevalRemark() {
		return this.gevalRemark;
	}

	public void setGevalRemark(String gevalRemark) {
		this.gevalRemark = gevalRemark;
	}

	public byte getGevalScores() {
		return this.gevalScores;
	}

	public void setGevalScores(byte gevalScores) {
		this.gevalScores = gevalScores;
	}

	public byte getGevalState() {
		return this.gevalState;
	}

	public void setGevalState(byte gevalState) {
		this.gevalState = gevalState;
	}

	public int getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return this.goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public BigDecimal getGoodsPrice() {
		return this.goodsPrice;
	}

	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public int getMemberId() {
		return this.memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getMemberTruename() {
		return this.memberTruename;
	}

	public void setMemberTruename(String memberTruename) {
		this.memberTruename = memberTruename;
	}

	public int getOrderGoodsId() {
		return this.orderGoodsId;
	}

	public void setOrderGoodsId(int orderGoodsId) {
		this.orderGoodsId = orderGoodsId;
	}

	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getOrderType() {
		return this.orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public int getStoreId() {
		return this.storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

}