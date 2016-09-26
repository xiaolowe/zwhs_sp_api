package cn.org.citycloud.zwhs.bean;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

public class WxKey {
	

	private int keywordSearch=1;

	@NotBlank(message="关键字不能为空")
	private String replyKeyword;
	
	private String replyText="";
	
	@Min(1)
	private int replyConType=0;

	@Min(1)
	private int replyType=2;

	@Min(1)
	private int storeId=0;
	
	private List<MaterialId> mids=new ArrayList<MaterialId>();

	public int getKeywordSearch() {
		return keywordSearch;
	}

	public void setKeywordSearch(int keywordSearch) {
		this.keywordSearch = keywordSearch;
	}

	public String getReplyKeyword() {
		return replyKeyword;
	}

	public void setReplyKeyword(String replyKeyword) {
		this.replyKeyword = replyKeyword;
	}

	public String getReplyText() {
		return replyText;
	}

	public void setReplyText(String replyText) {
		this.replyText = replyText;
	}

	public int getReplyType() {
		return replyType;
	}

	public void setReplyType(int replyType) {
		this.replyType = replyType;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public List<MaterialId> getMids() {
		return mids;
	}

	public void setMids(List<MaterialId> mids) {
		this.mids = mids;
	}

	public int getReplyConType() {
		return replyConType;
	}

	public void setReplyConType(int replyConType) {
		this.replyConType = replyConType;
	}



}
