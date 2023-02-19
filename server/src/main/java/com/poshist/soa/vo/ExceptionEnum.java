package com.poshist.soa.vo;


public enum ExceptionEnum {
    exceptionEnum_0000("0000","成功"),exceptionEnum_0001("0001","无法解密"),exceptionEnum_0002("0002","客户不存在")
    ,exceptionEnum_0003("0003","发送时间为空"),exceptionEnum_1001("1001","数据格式错误")
    ;
    private String msgCode;
    private String msg;

    private  ExceptionEnum(String code, String msg){
        this.msg=msg;
        this.msgCode=code;
    }
    public static ExceptionEnum getExceptionEnum(String code){
        for(ExceptionEnum exceptionEnum: ExceptionEnum.values()){
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