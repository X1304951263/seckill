package com.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class Order {
    private String id;
    private String userId;
    private String goodId;
    private String address;
    private String goodName;
    private Integer count;
    private Double price;
    private Integer status;
    private Date create_time;
    private Date pay_time;
}
