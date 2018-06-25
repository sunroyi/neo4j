package com.inesa.common.entity;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inesa.common.utils.IdGen;

public class BaseEntity {

	private static final long serialVersionUID = 1L;

	private String id;

	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;
	
	private String userId;
	
	private String delFlag = "0";
	
	private int pageNo;		// 页号
	private int pageSize;	// 页行数
	
	private String orderBy;
	
	private String appId;	// APPID
	private String orgId;	// 机构ID

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public void preInsert(){
		// 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
		if (StringUtils.isBlank(this.userId)){
			setId(IdGen.uuid());
		}
		
		if (StringUtils.isNotBlank(this.userId)){
			this.updateBy = this.userId;
			this.createBy = this.userId;
		}
		this.updateDate = new Date();
		this.createDate = this.updateDate;
	}
	
	public void preUpdate(){
		if (StringUtils.isNotBlank(this.userId)){
			this.updateBy = this.userId;
		}
		this.updateDate = new Date();
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
}
