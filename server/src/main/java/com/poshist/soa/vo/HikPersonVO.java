package com.poshist.soa.vo;

import lombok.Data;

import java.util.List;

/**
 * @author: tank
 * @Date: 2025/5/7
 */
@Data
public class HikPersonVO {
    private String personId;
    private String personName;
    private String gender;
    private String orgIndexCode;
    private String jobNo;
    private List<FaceData> faces;

    @Data
    public static class FaceData {
        private String faceData;
    }
}
