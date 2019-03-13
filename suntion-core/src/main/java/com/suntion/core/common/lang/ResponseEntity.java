package com.suntion.core.common.lang;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.suntion.core.common.constants.HttpConstants;
import net.sf.json.JSONObject;

import java.io.Serializable;

/**
 * 使用 建造者模式 构造返回对象
 * @author suntion
 * @since 2018-04-19 14:36:41
 *
 */
@JsonInclude(Include.NON_EMPTY)
public class ResponseEntity implements Serializable{
    private static final long serialVersionUID = 1091032671849867704L;
	
    public static final String CODE = "code";
    public static final String RESULT = "result";
    public static final String MESSAGE = "message";

	private String code;
	private Object result;
	private String message;

	public ResponseEntity() {
		this.code = HttpConstants.CODE_SUCCESS;
	}
	
	public ResponseEntity(String code, Object Result) {
		this.code = code;
		this.result = Result;
	}
	
	public ResponseEntity(String code, Object result, String message) {
		this.code = code;
		this.result = result;
		this.message = message;
	}
	
	public String getCode() {
		return code;
	}

	public Object getResult() {
		return result;
	}

	public String getMessage() {
		return message;
	}

	public ResponseEntity setCode(String code) {
		this.code = code;
		return this;
	}

	public ResponseEntity setResult(Object result) {
		this.result = result;
		return this;
	}

	public ResponseEntity setMessage(String message) {
		this.message = message;
		return this;
	}

	public static ResponseEntity SUCCESS() {
		return new ResponseEntity().setCode(HttpConstants.CODE_SUCCESS);
    }
	
	public static ResponseEntity SUCCESS(Object result) {
		return new ResponseEntity().setCode(HttpConstants.CODE_SUCCESS).setResult(result);
    }
	
	public static ResponseEntity SUCCESS(Object result, String message) {
		return new ResponseEntity().setCode(HttpConstants.CODE_SUCCESS).setResult(result).setMessage(message);
    }
	
	public static ResponseEntity FAILED() {
		return new ResponseEntity().setCode(HttpConstants.CODE_FAILED);
    }
	
	public static ResponseEntity FAILED(Object result) {
		return new ResponseEntity().setCode(HttpConstants.CODE_FAILED).setResult(result);
    }
	
	public static ResponseEntity FAILED(Object result, String message) {
		return new ResponseEntity().setCode(HttpConstants.CODE_FAILED).setResult(result).setMessage(message);
    }
		
	public JSONObject toJson() {
		return JSONObject.fromObject(this);
	}

	@Override
	public String toString() {
		return this.toJson().toString();
	}
}
