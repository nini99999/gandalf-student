package com.poshist.soa.vo;

import lombok.Data;

import java.util.List;

/**
 * @author: tank
 * @Date: 2025/5/9
 */
@Data
public class HikViaVO {
    private List<Via> list;
@Data
    public static class Via {
        private String personId;
        private String doorName;
        private Integer inAndOutType;
        private String receiveTime;
        private String doorIndexCode;
    }
}
