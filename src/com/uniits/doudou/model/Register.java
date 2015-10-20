/**
 * @description doudou com.uniits.doudou.model
 */
package com.uniits.doudou.model;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * @author liuchengwei
 * @since 2015-3-14 下午3:29:36
 * @description
 */
@Table("t_regist_info")
public class Register {

	@Id
	@Column("id")
	private long id;

	/**
	 * CAR_MACHINE_ID 车机I
	 */
	@Column("car_machine_id")
	private String carMachineId;

	@Column("vin")
	private String VIN;

	@Column("username")
	private String username;

	@Column("pwd")
	private String pwd;

	@Column("role_id")
	private long roleId;

	@Column("create_date")
	private String createDate;

	@Column("last_login_date")
	private String lastLoginDate;
	
	private Long serialNumber;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCarMachineId() {
		return carMachineId;
	}

	public void setCarMachineId(String carMachineId) {
		this.carMachineId = carMachineId;
	}

	public String getVIN() {
		return VIN;
	}

	public void setVIN(String vIN) {
		VIN = vIN;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
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
