package com.example;

import com.example.design_patterns.strategy.AwardEnum;
import com.example.design_patterns.strategy.AwardStrategy;
import com.example.design_patterns.strategy.AwardStrategyFactory;

public class Main {
    public static void main(String[] args)  {
        String type = "wx";
        AwardStrategyFactory.getInstance().getAwardResult("1", AwardEnum.of(type));
        String type1="toutiao";
        AwardStrategy strategy = AwardStrategyFactory.getStrategy(AwardEnum.of(type1));
        strategy.awardStrategy("1");
    }
}