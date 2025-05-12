package com.poshist.soa.vo;

import lombok.Data;

import java.util.List;

/**
 * @author: tank
 * @Date: 2025/5/9
 */
@Data
public class HikDoorVO {
    private List<Door> list;

    @Data
    public static class Door {
        private String indexCode;
    }
}
