package com.poshist.soa.vo;

import lombok.Data;

import java.util.List;

/**
 * @author: tank
 * @Date: 2025/5/12
 */
@Data
public class HikDownloadVO {
    private Integer taskType;
    private List< HikLeaveVO.ResourceInfo> resourceInfos;

}
