package cn.org.citycloud.zwhs.bean;

import org.hibernate.validator.constraints.NotBlank;



public class Wechat {
	
	@NotBlank
	private String wecharEntId;
	
	@NotBlank
	private String wechatEntName;

	@NotBlank
	private String wechatId;
	
	private String wechatToken="";
	
	private String wechatUrl="";
	
	@NotBlank
	private String appId;

	@NotBlank
	private String appSecret;
	

	public String getWecharEntId() {
		return wecharEntId;
	}

	public void setWecharEntId(String wecharEntId) {
		this.wecharEntId = wecharEntId;
	}

	public String getWechatEntName() {
		return wechatEntName;
	}

	public void setWechatEntName(String wechatEntName) {
		this.wechatEntName = wechatEntName;
	}

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public String getWechatToken() {
		return wechatToken;
	}

	public void setWechatToken(String wechatToken) {
		this.wechatToken = wechatToken;
	}

	public String getWechatUrl() {
		return wechatUrl;
	}

	public void setWechatUrl(String wechatUrl) {
		this.wechatUrl = wechatUrl;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

}
