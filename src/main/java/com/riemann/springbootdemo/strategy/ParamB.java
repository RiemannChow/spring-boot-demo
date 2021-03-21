package com.riemann.springbootdemo.strategy;

import lombok.Data;

@Data
public class ParamB implements Param {
    String msg;
}

class ParamB1 extends ParamB {}

class ParamB2 extends ParamB {}
