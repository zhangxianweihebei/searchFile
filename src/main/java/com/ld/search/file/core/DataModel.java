package com.ld.search.file.core;



import java.io.Serializable;
import java.util.Date;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.ld.lucenex.field.FieldKey;
import com.ld.lucenex.field.LDType;

/**
 * 数据模型
 * @author Administrator
 *
 */
public class DataModel extends RecursiveTreeObject<DataModel> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7772182792191659161L;
	@FieldKey(type=LDType.IntPoint)
	private int code;
	@FieldKey(type=LDType.String_TextField)
	private String name;
	@FieldKey(type=LDType.StringField)
	private String path;
	@FieldKey(type=LDType.StringField)
	private String type;
	@FieldKey(type=LDType.LongPoint)
	private long size;
	@FieldKey(type=LDType.DateField)
	private Date createTime;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "DataModel [code=" + code + ", name=" + name + ", path=" + path + ", type=" + type + ", size=" + size
				+ ", createTime=" + createTime + "]";
	}
	
}
