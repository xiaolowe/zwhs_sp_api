package cn.org.citycloud.zwhs.bean;

import org.hibernate.validator.constraints.NotBlank;

public class StoreImage extends Page {
	
	@NotBlank
	private String image;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	
}
