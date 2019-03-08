package com.suntion.core.exception;

/**
 * BaseException
 * @author suns suntion@yeah.net
 * @since 2017年8月11日上午11:37:55
 */
public class SuntionException extends RuntimeException{
    private static final long serialVersionUID = 3590260860821215306L;

    protected String state;

    protected String type;
    
    protected String message;

    public SuntionException(Throwable cause) {
        super(cause);
    }
    
    public SuntionException(String message) {
        this.message = message;
    }
    
    public SuntionException(String message, Throwable cause) {
        super(cause);
        this.message = message;
    }

    public SuntionException(String state, String msg, String type) {
        this.state = state;
        this.type = type;
        this.message = msg;
    }
    
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
