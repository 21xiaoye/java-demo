package com.example.design_patterns.strategy;



public abstract class BaseAwardTemplate {
    public Boolean awardTemplate(String userId){
        this.authentication(userId);
        this.risk(userId);
        return this.awardRecord(userId);
    }

    // 身份验证
    protected void authentication(String userId){
        System.out.println("执行身份验证"+userId);
    }
    // 风控
    protected void risk(String userId){
        System.out.println("执行风控检验!"+userId);
    }
    // 执行奖励发放
    protected abstract Boolean awardRecord(String userId);
}





















