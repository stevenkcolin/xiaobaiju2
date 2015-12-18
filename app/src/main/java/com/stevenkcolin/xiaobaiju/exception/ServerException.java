package com.stevenkcolin.xiaobaiju.exception;

/**
 * Created by Pengfei on 2015/12/10.
 */
public class ServerException extends Exception {
    public ServerException() {
        super();
    }

    public ServerException(String detailMessage) {
        super(detailMessage);
    }

    public ServerException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ServerException(Throwable throwable) {
        super(throwable);
    }
}
