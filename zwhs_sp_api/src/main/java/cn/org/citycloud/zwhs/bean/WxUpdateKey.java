package cn.org.citycloud.zwhs.bean;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;

public class WxUpdateKey {
	
	@Min(1)
	private int keyId=0;
	
	@Min(1)
	private int replyConType=0;
	
	private List<MaterialId> mids=new ArrayList<MaterialId>();
	
	private String replyText="";

	public int getKeyId() {
		return keyId;
	}

	public void setKeyId(int keyId) {
		this.keyId = keyId;
	}

	public int getReplyConType() {
		return replyConType;
	}

	public void setReplyConType(int replyConType) {
		this.replyConType = replyConType;
	}

	public List<MaterialId> getMids() {
		return mids;
	}

	public void setMids(List<MaterialId> mids) {
		this.mids = mids;
	}

	public String getReplyText() {
		return replyText;
	}

	public void setReplyText(String replyText) {
		this.replyText = replyText;
	}

}
