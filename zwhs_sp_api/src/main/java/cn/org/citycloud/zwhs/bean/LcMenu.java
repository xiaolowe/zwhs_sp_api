package cn.org.citycloud.zwhs.bean;

import org.hibernate.validator.constraints.NotBlank;

public class LcMenu {
	
	@NotBlank
	private String content;
	
	private int storeId=0;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
}
