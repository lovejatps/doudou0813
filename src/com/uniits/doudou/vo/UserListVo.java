/**
 * 
 */
package com.uniits.doudou.vo;

/**
 * @author 成卫
 * 
 */
public class UserListVo {

	private long id;

	private String loginName;

	private String roleName;

	private String createDate;

	private String lastLoginDate;
	
	private Long serialNumber;
	
	private String produceName ;

	public String getProduceName() {
		return produceName;
	}

	public void setProduceName(String produceName) {
		this.produceName = produceName;
	}

	public UserListVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserListVo(long id, String loginName, String roleName,
			String createDate, String lastLoginDate, Long serialNumber) {
		super();
		this.id = id;
		this.loginName = loginName;
		this.roleName = roleName;
		this.createDate = createDate;
		this.lastLoginDate = lastLoginDate;
		this.serialNumber = serialNumber;
	}

	public UserListVo(long id, String loginName, String roleName,
			String createDate, String lastLoginDate, Long serialNumber,String produceName) {
		super();
		this.id = id;
		this.loginName = loginName;
		this.roleName = roleName;
		this.createDate = createDate;
		this.lastLoginDate = lastLoginDate;
		this.serialNumber = serialNumber;
		this.produceName = produceName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Long getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Long serialNumber) {
		this.serialNumber = serialNumber;
	}

}
