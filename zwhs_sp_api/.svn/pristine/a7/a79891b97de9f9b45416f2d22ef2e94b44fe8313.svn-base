package cn.org.citycloud.zwhs.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the material_warehouse database table.
 * 
 */
@Entity
@Table(name="material_warehouse")
@NamedQuery(name="MaterialWarehouse.findAll", query="SELECT m FROM MaterialWarehouse m")
@JsonIgnoreProperties(value="wechatKeys")
public class MaterialWarehouse implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="article_author")
	private String articleAuthor;

	@Column(name="article_class_id")
	private Integer articleClassId;

	@Column(name="article_class_name")
	private String articleClassName;

	@Lob
	@Column(name="article_comments")
	private String articleComments;

	@Column(name="article_state")
	private int articleState;
	
	@Column(name="article_title")
	private String articleTitle;

	@Column(name="cover_pic")
	private String coverPic;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ins_date")
	private Date insDate;

	@Column(name="material_desc")
	private String materialDesc;

	@Column(name="material_type")
	private int materialType;

	@Column(name="store_id")
	private int storeId;

	@Column(name="thumbnail_pic")
	private String thumbnailPic;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="upd_date")
	private Date updDate;
	
	@ManyToMany(cascade=CascadeType.ALL,mappedBy="materialWarehouses")
	private Set<WechatKey> wechatKeys;


	public MaterialWarehouse() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getArticleAuthor() {
		return this.articleAuthor;
	}

	public void setArticleAuthor(String articleAuthor) {
		this.articleAuthor = articleAuthor;
	}

	public Integer getArticleClassId() {
		return this.articleClassId;
	}

	public void setArticleClassId(Integer articleClassId) {
		this.articleClassId = articleClassId;
	}

	public String getArticleClassName() {
		return this.articleClassName;
	}

	public void setArticleClassName(String articleClassName) {
		this.articleClassName = articleClassName;
	}

	public String getArticleComments() {
		return this.articleComments;
	}

	public void setArticleComments(String articleComments) {
		this.articleComments = articleComments;
	}

	public String getArticleTitle() {
		return this.articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getCoverPic() {
		return this.coverPic;
	}

	public void setCoverPic(String coverPic) {
		this.coverPic = coverPic;
	}

	public Date getInsDate() {
		return this.insDate;
	}

	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}

	public String getMaterialDesc() {
		return this.materialDesc;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	public int getMaterialType() {
		return this.materialType;
	}

	public void setMaterialType(int materialType) {
		this.materialType = materialType;
	}

	public int getStoreId() {
		return this.storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getThumbnailPic() {
		return this.thumbnailPic;
	}

	public void setThumbnailPic(String thumbnailPic) {
		this.thumbnailPic = thumbnailPic;
	}

	public Date getUpdDate() {
		return this.updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public Set<WechatKey> getWechatKeys() {
		return wechatKeys;
	}

	public void setWechatKeys(Set<WechatKey> wechatKeys) {
		this.wechatKeys = wechatKeys;
	}

	public int getArticleState() {
		return articleState;
	}

	public void setArticleState(int articleState) {
		this.articleState = articleState;
	}

}