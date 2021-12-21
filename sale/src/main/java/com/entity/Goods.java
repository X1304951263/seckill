package com.entity;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;


@Data
public class Goods {
    private String id;
    private String goodName;
    private String goodTitle;
    private String goodImg;
    private String goodDetail;
    private Double price;
    private Integer count;
    private Timestamp startTime;
}
