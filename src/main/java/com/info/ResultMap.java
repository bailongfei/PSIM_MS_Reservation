package com.info;

/**
 * 存储信息 使用先判断消息代码
 * @resultCode 消息代码 1 成功 0 失败
 * @resultMessage 消息信息
 * @param --消息体
 */
public class ResultMap<T> {

    private String resultCode;

    private String resultMessage;

    private T param;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public T getParam() {
        return param;
    }

    public void setParam(T param) {
        this.param = param;
    }
}
