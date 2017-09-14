package com.bxt.sptask.common.vo;

/**
 * Description: ajax 返回结果封装对象
 * All Rights Reserved.
 * @version 1.0 14-4-23 上午10:29  by ff(ff@chinazrbc.com)创建
 */
public class AjaxSimpleResult {
	public final static int SUCCESS = 1;
	public final static int FAILED = 0;
	public final static int ERROR = -1;

	private String message;
	private int status = SUCCESS;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
