package com.riemann.springbootdemo.strategy;

public class AStrategyRouter extends AbstractStrategyRouter<ParamA, Result> {
    @Override
    protected StrategyMapper<ParamA, Result> registerStrategyMapper() {
        return new AStrategyMapper();
    }
}

class AStrategyMapper implements AbstractStrategyRouter.StrategyMapper<ParamA, Result> {
    /*@Autowired
    private FilterChain filterChain;*/

    @Override
    public StrategyHandler<ParamA, Result> get(ParamA param) {

        // 责任链优化
        // filterChain.addFilter()

        if (param instanceof ParamA1) {
            return new A1StrategyHandler();
        } else if (param instanceof ParamA2) {
            return new A2StrategyHandler();
        } else {
            return null;
        }
    }
}