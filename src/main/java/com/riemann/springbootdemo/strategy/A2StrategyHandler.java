package com.riemann.springbootdemo.strategy;

public class A2StrategyHandler implements StrategyHandler<ParamA, Result> {
    @Override
    public Result apply(ParamA param) {
        return new Result(param.toString());
    }
}
