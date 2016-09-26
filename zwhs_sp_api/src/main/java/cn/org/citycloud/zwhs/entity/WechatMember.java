package cn.org.citycloud.zwhs.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the wechat_member database table.
 * 
 */
@Entity
@Table(name="wechat_member")
@NamedQuery(name="WechatMember.findAll", query="SELECT w FROM WechatMember w")
public class WechatMember implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="member_id")
	private int memberId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ins_date")
	private Date insDate;

	@Column(name="member_avatar")
	private String memberAvatar;

	@Column(name="member_email")
	private String memberEmail;

	@Column(name="member_login_ip")
	private String memberLoginIp;

	@Column(name="member_login_num")
	private int memberLoginNum;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="member_login_time")
	private Date memberLoginTime;

	@Column(name="member_old_login_ip")
	private String memberOldLoginIp;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="member_old_login_time")
	private Date memberOldLoginTime;

	@Column(name="member_phone")
	private String memberPhone;

	@Column(name="member_pwd")
	private String memberPwd;

	@Column(name="member_region")
	private int memberRegion;

	@Column(name="member_sex")
	private byte memberSex;

	@Column(name="member_state")
	private byte memberState;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="member_time")
	private Date memberTime;

	@Column(name="member_truename")
	private String memberTruename;

	@Column(name="open_id")
	private String openId;

	@Column(name="region_area_name")
	private String regionAreaName;

	@Column(name="region_city")
	private int regionCity;

	@Column(name="region_city_name")
	private String regionCityName;

	@Column(name="region_prov")
	private int regionProv;

	@Column(name="region_prov_name")
	private String regionProvName;

	@Column(name="union_id")
	private String unionId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="upd_date")
	private Date updDate;

	@Column(name="wechat_aliasname")
	private String wechatAliasname;

	@Column(name="wechat_no")
	private String wechatNo;

	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="store_member",joinColumns=@JoinColumn(name="member_id"),inverseJoinColumns=@JoinColumn(name="store_id"))
	private Set<Store> stores;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="member_id")
	private Set<WechatMemberAddr> wechatMemberAddrs;
	
	
	public WechatMember() {
	}

	public int getMemberId() {
		return this.memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public Date getInsDate() {
		return this.insDate;
	}

	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}

	public String getMemberAvatar() {
		return this.memberAvatar;
	}

	public void setMemberAvatar(String memberAvatar) {
		this.memberAvatar = memberAvatar;
	}

	public String getMemberEmail() {
		return this.memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public String getMemberLoginIp() {
		return this.memberLoginIp;
	}

	public void setMemberLoginIp(String memberLoginIp) {
		this.memberLoginIp = memberLoginIp;
	}

	public int getMemberLoginNum() {
		return this.memberLoginNum;
	}

	public void setMemberLoginNum(int memberLoginNum) {
		this.memberLoginNum = memberLoginNum;
	}

	public Date getMemberLoginTime() {
		return this.memberLoginTime;
	}

	public void setMemberLoginTime(Date memberLoginTime) {
		this.memberLoginTime = memberLoginTime;
	}

	public String getMemberOldLoginIp() {
		return this.memberOldLoginIp;
	}

	public void setMemberOldLoginIp(String memberOldLoginIp) {
		this.memberOldLoginIp = memberOldLoginIp;
	}

	public Date getMemberOldLoginTime() {
		return this.memberOldLoginTime;
	}

	public void setMemberOldLoginTime(Date memberOldLoginTime) {
		this.memberOldLoginTime = memberOldLoginTime;
	}

	public String getMemberPhone() {
		return this.memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public String getMemberPwd() {
		return this.memberPwd;
	}

	public void setMemberPwd(String memberPwd) {
		this.memberPwd = memberPwd;
	}

	public int getMemberRegion() {
		return this.memberRegion;
	}

	public void setMemberRegion(int memberRegion) {
		this.memberRegion = memberRegion;
	}

	public byte getMemberSex() {
		return this.memberSex;
	}

	public void setMemberSex(byte memberSex) {
		this.memberSex = memberSex;
	}

	public byte getMemberState() {
		return this.memberState;
	}

	public void setMemberState(byte memberState) {
		this.memberState = memberState;
	}

	public Date getMemberTime() {
		return this.memberTime;
	}

	public void setMemberTime(Date memberTime) {
		this.memberTime = memberTime;
	}

	public String getMemberTruename() {
		return this.memberTruename;
	}

	public void setMemberTruename(String memberTruename) {
		this.memberTruename = memberTruename;
	}

	public String getOpenId() {
		return this.openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getRegionAreaName() {
		return this.regionAreaName;
	}

	public void setRegionAreaName(String regionAreaName) {
		this.regionAreaName = regionAreaName;
	}

	public int getRegionCity() {
		return this.regionCity;
	}

	public void setRegionCity(int regionCity) {
		this.regionCity = regionCity;
	}

	public String getRegionCityName() {
		return this.regionCityName;
	}

	public void setRegionCityName(String regionCityName) {
		this.regionCityName = regionCityName;
	}

	public int getRegionProv() {
		return this.regionProv;
	}

	public void setRegionProv(int regionProv) {
		this.regionProv = regionProv;
	}

	public String getRegionProvName() {
		return this.regionProvName;
	}

	public void setRegionProvName(String regionProvName) {
		this.regionProvName = regionProvName;
	}

	public String getUnionId() {
		return this.unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public Date getUpdDate() {
		return this.updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public String getWechatAliasname() {
		return this.wechatAliasname;
	}

	public void setWechatAliasname(String wechatAliasname) {
		this.wechatAliasname = wechatAliasname;
	}

	public String getWechatNo() {
		return this.wechatNo;
	}

	public void setWechatNo(String wechatNo) {
		this.wechatNo = wechatNo;
	}

	public Set<Store> getStores() {
		return stores;
	}

	public void setStores(Set<Store> stores) {
		this.stores = stores;
	}

	public Set<WechatMemberAddr> getWechatMemberAddrs() {
		return wechatMemberAddrs;
	}

	public void setWechatMemberAddrs(Set<WechatMemberAddr> wechatMemberAddrs) {
		this.wechatMemberAddrs = wechatMemberAddrs;
	}

}