package com.sjtu.icare.modules.op.entity;

import java.io.Serializable;
import java.sql.Date;

public class ElderAudioRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private int recorderIdentity;		//录音人身份（护工1，医生2，家属3）
	private int recorderId;				//录音人ID
	private int listenerIdentity;		//收听人身份
	private int listenerId;				//收听人ID
	private int elderId;				//老人ID
	private Date recordTime;			//记录时间
	private String url;					//链接
	private int readTimes;				//播放次数
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRecorderIdentity() {
		return recorderIdentity;
	}
	public void setRecorderIdentity(int recorderIdentity) {
		this.recorderIdentity = recorderIdentity;
	}
	public int getRecorderId() {
		return recorderId;
	}
	public void setRecorderId(int recorderId) {
		this.recorderId = recorderId;
	}
	public int getListenerIdentity() {
		return listenerIdentity;
	}
	public void setListenerIdentity(int listenerIdentity) {
		this.listenerIdentity = listenerIdentity;
	}
	public int getListenerId() {
		return listenerId;
	}
	public void setListenerId(int listenerId) {
		this.listenerId = listenerId;
	}
	public int getElderId() {
		return elderId;
	}
	public void setElderId(int elderId) {
		this.elderId = elderId;
	}
	public Date getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getReadTimes() {
		return readTimes;
	}
	public void setReadTimes(int readTimes) {
		this.readTimes = readTimes;
	}
	
}
