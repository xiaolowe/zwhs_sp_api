package cn.org.citycloud.zwhs.bean;

import org.hibernate.validator.constraints.NotBlank;

public class Login {

	@NotBlank(message = "手机号不能为空")
	private String phone;

	@NotBlank(message = "密码不能为空")
	private String password;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
