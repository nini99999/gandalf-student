package com.poshist.soa.vo;

import lombok.Data;

import java.util.List;

/**
 * @author: tank
 * @Date: 2025/5/8
 */
@Data
public class HikCardVO {
    private String startDate;
    private String endDate;
    private List<Card> cardList;

    @Data
    public static class Card {
        private String cardNo;
        private String personId;
        private String orgIndexCode;
        private Integer cardType;
    }
}
