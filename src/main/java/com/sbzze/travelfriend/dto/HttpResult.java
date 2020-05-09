package com.sbzze.travelfriend.dto;

/**
 * 接口统一返回对象
 * @author yx
 *
 */
public class HttpResult {
	


	//返回数据
	private Object data;

	//提示消息
	private String message;
	
	//返回码 0表示成功  其他数字定义不同失败类型 暂定-1表示通常错误 
	private Integer code;
	
	public HttpResult() {
		
	}

	public HttpResult(Integer code, Object data) {
		this.data = data;
		this.code = code;
	}

	public HttpResult(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Object getData() {
		return this.data;
	}

	public void setData(Object data) {
		this.data = data;
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	
	//默认返回失败
	public HttpResult retError(Integer code, String message) {
		this.code = code;
		this.message = message;
		return this;
	}
	
	//默认返回成功
	public HttpResult retSuccess(Object data) {
		this.code = 0;
		this.message = "成功";
		this.data = data;
		return this;
	}
	
	
	
	
}

