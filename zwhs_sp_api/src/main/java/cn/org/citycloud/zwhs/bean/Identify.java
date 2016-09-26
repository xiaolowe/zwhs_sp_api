package cn.org.citycloud.zwhs.bean;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public class Identify {

	@NotBlank(message = "开户行不能为空")
	private String bankAccName;

	@NotBlank(message = "银行账号不能为空")
	private String bankAccNumber;

	private String bankName = "";

	@NotBlank(message = "营业执照不能为空")
	private String businessLicence;

	@NotBlank(message = "门店地址不能为空")
	private String companyAddress;

	@NotBlank(message = "门店名称不能为空")
	private String companyName;

	@Range(min = 1, message = "区县级未填写")
	private Integer companyRegion = 0;

	@NotBlank(message = "组织代码不能为空")
	private String organizationCode;

	private String ownerCardBack = "";

	@NotBlank(message = "身份证图片不能为空")
	private String ownerCardFront;

	private String regionAreaName = "";

	@Range(min = 1, message = "市级未填写")
	private Integer regionCity = 0;

	private String regionCityName = "";

	@Range(min = 1, message = "省级未填写")
	private Integer regionProv = 0;

	private String regionProvName = "";

	@NotBlank(message = "服务商店照片不能为空")
	private String providerImage;

	@NotBlank(message = "法人姓名不能为空")
	private String providerOwner;

	@NotBlank(message = "身份证不能为空")
	private String providerOwnerCard;

	private int providerState = 0;

	@NotBlank(message = "税务登记证号不能为空")
	private String taxRegCert;

	@Min(1)
	private int userId = 0;

	private String joininState = "10";
	@NotBlank(message = "持卡人姓名不能为空")
	private String bankOwner;

	public String getJoininState() {
		return joininState;
	}

	public void setJoininState(String joininState) {
		this.joininState = joininState;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getBankAccName() {
		return bankAccName;
	}

	public void setBankAccName(String bankAccName) {
		this.bankAccName = bankAccName;
	}

	public String getBankAccNumber() {
		return bankAccNumber;
	}

	public void setBankAccNumber(String bankAccNumber) {
		this.bankAccNumber = bankAccNumber;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBusinessLicence() {
		return businessLicence;
	}

	public void setBusinessLicence(String businessLicence) {
		this.businessLicence = businessLicence;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getOwnerCardBack() {
		return ownerCardBack;
	}

	public void setOwnerCardBack(String ownerCardBack) {
		this.ownerCardBack = ownerCardBack;
	}

	public String getOwnerCardFront() {
		return ownerCardFront;
	}

	public void setOwnerCardFront(String ownerCardFront) {
		this.ownerCardFront = ownerCardFront;
	}

	public String getRegionAreaName() {
		return regionAreaName;
	}

	public void setRegionAreaName(String regionAreaName) {
		this.regionAreaName = regionAreaName;
	}

	public String getRegionCityName() {
		return regionCityName;
	}

	public void setRegionCityName(String regionCityName) {
		this.regionCityName = regionCityName;
	}

	public String getRegionProvName() {
		return regionProvName;
	}

	public void setRegionProvName(String regionProvName) {
		this.regionProvName = regionProvName;
	}

	public String getProviderImage() {
		return providerImage;
	}

	public void setProviderImage(String providerImage) {
		this.providerImage = providerImage;
	}

	public String getProviderOwner() {
		return providerOwner;
	}

	public void setProviderOwner(String providerOwner) {
		this.providerOwner = providerOwner;
	}

	public String getProviderOwnerCard() {
		return providerOwnerCard;
	}

	public void setProviderOwnerCard(String providerOwnerCard) {
		this.providerOwnerCard = providerOwnerCard;
	}

	public int getProviderState() {
		return providerState;
	}

	public void setProviderState(int providerState) {
		this.providerState = providerState;
	}

	public String getTaxRegCert() {
		return taxRegCert;
	}

	public void setTaxRegCert(String taxRegCert) {
		this.taxRegCert = taxRegCert;
	}

	public String getBankOwner() {
		return bankOwner;
	}

	public void setBankOwner(String bankOwner) {
		this.bankOwner = bankOwner;
	}

	public Integer getCompanyRegion() {
		return companyRegion;
	}

	public void setCompanyRegion(Integer companyRegion) {
		this.companyRegion = companyRegion;
	}

	public Integer getRegionCity() {
		return regionCity;
	}

	public void setRegionCity(Integer regionCity) {
		this.regionCity = regionCity;
	}

	public Integer getRegionProv() {
		return regionProv;
	}

	public void setRegionProv(Integer regionProv) {
		this.regionProv = regionProv;
	}

}
