package com.example.design_patterns.strategy;




public interface AwardStrategy {

    Boolean awardStrategy(String userId);
    AwardEnum getSource();
}
