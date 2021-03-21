package com.riemann.springbootdemo.strategy;

public interface StrategyHandler<T, R> {
    StrategyHandler DEFAULT = t -> null;

    /**
     * apply strategy
     *
     * @param param
     * @return
     */
    R apply(T param);
}
