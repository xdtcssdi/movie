package com.movie.util;

public class ResponseModel {
	private boolean success;
	private String msg;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * 默认成功
	 * @param msg
	 */
	public ResponseModel(String msg) {
		this.setSuccess(true);
		this.setMsg(msg);
	}
	public ResponseModel(boolean success,String msg) {
		this.setSuccess(success);
		this.setMsg(msg);
	}
}
