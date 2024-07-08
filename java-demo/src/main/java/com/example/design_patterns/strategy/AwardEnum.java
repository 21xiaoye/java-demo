package com.example.design_patterns.strategy;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum AwardEnum {
    WX("wx","微信", WxAwardStrategyService.class),
    TOUTIAO("toutiao","头条", ToutiaoAwardStrategyService.class);
    private String type;
    private String source;
    private Class<? extends AwardStrategy> dataClass;

    AwardEnum(String type, String source, Class<? extends AwardStrategy> dataClass) {
        this.type = type;
        this.source = source;
        this.dataClass = dataClass;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Class<? extends AwardStrategy> getDataClass() {
        return dataClass;
    }

    public void setDataClass(Class<? extends AwardStrategy> dataClass) {
        this.dataClass = dataClass;
    }

    public static Map<String, AwardEnum> getCache() {
        return cache;
    }

    public static void setCache(Map<String, AwardEnum> cache) {
        AwardEnum.cache = cache;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    private static Map<String, AwardEnum> cache;
    static {
        cache = Arrays.stream(AwardEnum.values()).collect(Collectors.toMap(AwardEnum::getType, Function.identity()));
    }

    public static AwardEnum of(String type){
        return cache.get(type);
    }
}
