package com.fewok.biz.biz.exception;

/**
 * @author notreami on 18/3/25.
 */
public class ProcessExecuteException extends RuntimeException {
    public ProcessExecuteException(){
        super();
    }
    public ProcessExecuteException(String message) {
        super(message);
    }
    public ProcessExecuteException(String message, Exception e) {
        super(message, e);
    }

}
