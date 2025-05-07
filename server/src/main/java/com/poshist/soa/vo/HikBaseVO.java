package com.poshist.soa.vo;

import lombok.Data;

/**
 * @author: tank
 * @Date: 2025/5/7
 */
@Data
public class HikBaseVO<T> {
    private String code;
    private String msg;
    private T data;
}
