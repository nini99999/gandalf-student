package com.poshist.soa.vo;

import lombok.Data;

import java.util.List;

/**
 * @author: tank
 * @Date: 2025/5/9
 */
@Data
public class HikLeaveVO {
    private List<PersonData> personDatas;
    private List<ResourceInfo> resourceInfos;
    private String startTime;
    private String endTime;

    @Data
    public static class PersonData {
        private String[] indexCodes;
        private String personDataType;
    }

    @Data
    public static class ResourceInfo {
        private String resourceIndexCode;
        private String resourceType;
    }
}
