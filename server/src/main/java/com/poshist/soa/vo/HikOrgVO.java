package com.poshist.soa.vo;

import lombok.Data;

/**
 * @author: tank
 * @Date: 2025/5/7
 */
@Data
public class HikOrgVO {
    private String orgIndexCode;
    private String orgName;
    private String parentIndexCode;
    private Long clientId;
}
