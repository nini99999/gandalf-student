package com.poshist.common;

public class Constant {
    //有效
    public static final Integer VALID=0;
    //无效
    public static final Integer INVALID=1;

    //图片类型
    public static final String PIC_TYPE_CAR="01";
    public static final String PIC_TYPE_STUDENT="02";

    //车辆状态
    public static final Long STAND_BY=102l;

    //角色
    //学院领导
    public static final Long ROLE_FACULTY_LEAD=3l;
    //安全保卫部
    public static final Long ROLE_SECURITY_LEAD=5l;
    //校领导
    public static final Long ROLE_SCHOOL_LEAD=2l;
    //司机
    public static final Long ROLE_DRIVER=11l;

    //部门
    //根部门
    public static final Long DEPARTMENT_ROOT_ID=0l;
    //安全保卫部
    public static final Long DEPARTMENT_SECURITY_ID=4l;
    //校办
    public static final Long DEPARTMENT_SCHOOL_ID=2l;
    //

    //汽车审批流程
    //未审批
    public static final Integer CAR_APPLICANT_INIT=0;
    //审批中
    public static final Integer CAR_APPLICANT_ING=1;
    //通过
    public static final Integer CAR_APPLICANT_PASS=2;
    //拒绝
    public static final Integer CAR_APPLICANT_REFUSE=3;
    //已派车
    public static final Integer CAR_APPLICANT_ALREADY=4;
    //超时
    public static final Integer CAR_APPLICANT_TIMEOUT=5;
    //撤销
    public static final Integer CAR_APPLICANT_CANCEL=7;
}
