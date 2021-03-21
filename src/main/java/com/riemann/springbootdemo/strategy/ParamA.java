package com.riemann.springbootdemo.strategy;

import lombok.Data;

@Data
public class ParamA implements Param {
    String msg;
}

class ParamA1 extends ParamA {}

class ParamA2 extends ParamA {}