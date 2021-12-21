package com.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Message implements Serializable {
    private String userId;
    private String goodId;
}
