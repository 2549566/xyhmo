package com.xyhmo.commom.base;

import java.io.Serializable;
import java.util.List;

public class ResultOld implements Serializable{

	private static final long serialVersionUID = 2518905830224487545L;
	private int code; //返回码
	private String msg; //返回信息
	private List<Object> resultList;//实体类列表
	private Object modelInfo; //实体类
	private String other;//返回路径
	
	
	public ResultOld(){
		this.code = -1;
		this.msg = "";
	}
	public ResultOld(int code, String msg){
		this.code = code;
		this.msg = msg;
	}
	public ResultOld(int code, String msg, String other){
		this.code = code;
		this.msg = msg;
		this.other = other;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List getResultList() {
		return resultList;
	}
	public void setResultList(List resultList) {
		this.resultList = resultList;
	}
	public Object getModelInfo() {
		return modelInfo;
	}
	public void setModelInfo(Object modelInfo) {
		this.modelInfo = modelInfo;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	

}
