package com.seasy.interfaces.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.seasy.interfaces.AbstractBaseDTO;

@XmlRootElement
public class MessagesDTO extends AbstractBaseDTO {
	private static final long serialVersionUID = 6946555268323329764L;
	
	private Long id; 	//ID
	private String type; 	//类型.ALL, USERS, ROLES, ADMIN
	private String contents; 	//内容
	private Long sendUserid; 	//发送人
	private String receiveId; 	//接收用户ID
	private String receiveYes; 	//已接收用户ID
	private String deleteYes; 	//已删除用户ID，当type＝ALL是使用
	private String operator; 	//操作人
	private Date operateTime; 	//操作时间
	
	private boolean readed; //是否已读
	private String sendUserName; //发送人姓名
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getContents() {
		return contents;
	}
	
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	public Long getSendUserid() {
		return sendUserid;
	}
	
	public void setSendUserid(Long sendUserid) {
		this.sendUserid = sendUserid;
	}
	
	public String getReceiveId() {
		return receiveId;
	}
	
	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId;
	}
	
	public String getReceiveYes() {
		return receiveYes;
	}
	
	public void setReceiveYes(String receiveYes) {
		this.receiveYes = receiveYes;
	}
	
	public String getDeleteYes() {
		return deleteYes;
	}

	public void setDeleteYes(String deleteYes) {
		this.deleteYes = deleteYes;
	}

	public String getOperator() {
		return operator;
	}
	
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public Date getOperateTime() {
		return operateTime;
	}
	
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public boolean isReaded() {
		return readed;
	}

	public void setReaded(boolean readed) {
		this.readed = readed;
	}

	public String getSendUserName() {
		return sendUserName;
	}

	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}
	
}
