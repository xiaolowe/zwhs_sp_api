package cn.org.citycloud.zwhs.bean;


import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;


public class Commdity {
	
	@Range(min=1,message="品牌未填写")
	private int brandNo=0;

	@Range(min=1,message="子分类未填写")
	private int gcLitId=0;
	
	@Range(min=1,message="分类未填写")
	private int gcNo=0;
	
	@NotBlank(message="产品介绍不能为空")
	private String goodsBody="";

	@NotBlank(message="商品图片不能为空")
	private String goodsImage="";
	
	private String goodsImageSmall="";

	@NotBlank(message="商品名称不能为空")
	private String goodsName="";
	@NotBlank(message="商品编号不能为空")
	private String goodsSn="";
	@NotBlank(message="商品规格不能为空")
	private String goodsSpec="";
	
	private byte goodsState=1;

	private byte goodsVerify=0;
	
	private int storeId=0;
	
	public int getBrandNo() {
		return brandNo;
	}
	public void setBrandNo(int brandNo) {
		this.brandNo = brandNo;
	}
	public int getGcLitId() {
		return gcLitId;
	}
	public void setGcLitId(int gcLitId) {
		this.gcLitId = gcLitId;
	}
	public int getGcNo() {
		return gcNo;
	}
	public void setGcNo(int gcNo) {
		this.gcNo = gcNo;
	}
	public String getGoodsBody() {
		return goodsBody;
	}
	public void setGoodsBody(String goodsBody) {
		this.goodsBody = goodsBody;
	}
	public String getGoodsImage() {
		return goodsImage;
	}
	public void setGoodsImage(String goodsImage) {
		this.goodsImage = goodsImage;
	}
	public String getGoodsImageSmall() {
		return goodsImageSmall;
	}
	public void setGoodsImageSmall(String goodsImageSmall) {
		this.goodsImageSmall = goodsImageSmall;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsSn() {
		return goodsSn;
	}
	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}
	public String getGoodsSpec() {
		return goodsSpec;
	}
	public void setGoodsSpec(String goodsSpec) {
		this.goodsSpec = goodsSpec;
	}
	public byte getGoodsState() {
		return goodsState;
	}
	public void setGoodsState(byte goodsState) {
		this.goodsState = goodsState;
	}
	public byte getGoodsVerify() {
		return goodsVerify;
	}
	public void setGoodsVerify(byte goodsVerify) {
		this.goodsVerify = goodsVerify;
	}
	public int getStoreId() {
		return storeId;
	}
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

}
