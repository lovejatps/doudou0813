/**
 * @description doudou com.uniits.doudou.model
 */
package com.uniits.doudou.model;


import org.nutz.dao.DB;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.SQL;
import org.nutz.dao.entity.annotation.Table;

/**
 * @author liuchengwei
 * @since 2015-3-14 下午3:29:56
 * @description
 */
@Table("t_app")
public class App {
	
	@Id
    @Column("id")
    //@Prev({@SQL(db = DB.ORACLE,value="select seq_app.nextval from dual")})
    private Long id;
	
	@Column("app_name")
	private String appName;
	
	@Column("app_version")
	private String appVersion;
	
	@Column("pack_name")
	private String packName;
	
	@Column("file_name")
	private String fileName;
	
	@Column("app_classify")
	private Long appClassify;
	
	@Column("app_file_id")
	private Long appFileId;
	
	@Column("icon_id")
	private Long iconId;
	
	@Column("upgrade")
	private String upgrade;
	
	@Column("recommend")
	private String recommend;
	
	@Column("recommend_index")
	private Long recommendIndex;
	
	@Column("create_date")
	private String createDate;
	
	@Column("describes")
	private String describes;
	
	@Column("detail")
	private String detail;
	
	@Column("deleted")
	private Long deleted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getPackName() {
		return packName;
	}

	public void setPackName(String packName) {
		this.packName = packName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getAppClassify() {
		return appClassify;
	}

	public void setAppClassify(Long appClassify) {
		this.appClassify = appClassify;
	}

	public Long getAppFileId() {
		return appFileId;
	}

	public void setAppFileId(Long appFileId) {
		this.appFileId = appFileId;
	}

	public String getUpgrade() {
		return upgrade;
	}

	public void setUpgrade(String upgrade) {
		this.upgrade = upgrade;
	}

	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getDescribes() {
		return describes;
	}

	public void setDescribes(String describes) {
		this.describes = describes;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}

	public Long getIconId() {
		return iconId;
	}

	public void setIconId(Long iconId) {
		this.iconId = iconId;
	}

	public Long getRecommendIndex() {
		return recommendIndex;
	}

	public void setRecommendIndex(Long recommendIndex) {
		this.recommendIndex = recommendIndex;
	}
	
}
