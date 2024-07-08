package com.example.design_patterns.strategy;

public class WxAwardStrategyService extends BaseAwardTemplate implements AwardStrategy{
    @Override
    public Boolean awardStrategy(String userId) {
        return super.awardTemplate(userId);
    }

    @Override
    public AwardEnum getSource() {
        return AwardEnum.WX;
    }

    @Override
    protected Boolean awardRecord(String userId) {
        System.out.println("微信发放奖励"+userId);
        return Boolean.TRUE;
    }
}
