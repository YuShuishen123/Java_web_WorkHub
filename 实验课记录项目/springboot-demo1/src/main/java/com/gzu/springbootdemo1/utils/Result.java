package com.gzu.springbootdemo1.utils;

import lombok.Data;

@Data
public class Result {
    private int code;
    private String msg;
    private Object data;
}
