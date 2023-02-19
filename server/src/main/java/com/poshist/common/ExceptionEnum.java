package com.poshist.common;

public enum ExceptionEnum {
    exceptionEnum_1001("1001","没有院系领导"),exceptionEnum_1002("1002","没有安全管理处领导"),exceptionEnum_1003("1003","没有校办领导")
    ,exceptionEnum_1004("1004","申请已超时")
    ;
    private String msgCode;
    private String msg;

    private  ExceptionEnum(String code, String msg){
        this.msg=msg;
        this.msgCode=code;
    }
    public static ExceptionEnum getExceptionEnum(String code){
        for(ExceptionEnum exceptionEnum:ExceptionEnum.values()){
            if (exceptionEnum.msgCode.equals(code)){
                return exceptionEnum;
            }
        }
        return  null;
    }
    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
