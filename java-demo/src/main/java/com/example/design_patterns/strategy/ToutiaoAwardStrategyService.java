package com.example.design_patterns.strategy;


public class ToutiaoAwardStrategyService extends BaseAwardTemplate implements AwardStrategy{
    @Override
    public Boolean awardStrategy(String userId) {
        return super.awardTemplate(userId);
    }

    @Override
    public AwardEnum getSource() {
        return AwardEnum.TOUTIAO;
    }

    @Override
    protected Boolean awardRecord(String userId) {
        System.out.println("头条发放奖励"+userId);
        return Boolean.TRUE;
    }
}
