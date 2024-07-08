package com.example.design_patterns.strategy;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class AwardStrategyFactory implements org.springframework.context.ApplicationContextAware{
    /**
     * 创建映射表
     */
    private final static Map<AwardEnum, AwardStrategy> MAP = new HashMap<>();
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, AwardStrategy> beanTypeMap = applicationContext.getBeansOfType(AwardStrategy.class);
        beanTypeMap.values().forEach(strategyObj -> MAP.put(strategyObj.getSource(), strategyObj));
    }
    static {
        MAP.put(AwardEnum.WX,new WxAwardStrategyService());
        MAP.put(AwardEnum.TOUTIAO,new ToutiaoAwardStrategyService());
    }

    /**
     * 防止请求到未定义的策略枚举值
     * @param awardType
     * @return
     */



    /**
     * 延迟加载
     * 创建映射表
     */
    private static final Map<AwardEnum, Supplier<AwardStrategy>>  STRATEGY_SUPPLIERS = new HashMap<>();
    static {
        STRATEGY_SUPPLIERS.put(AwardEnum.WX, WxAwardStrategyService::new);
        STRATEGY_SUPPLIERS.put(AwardEnum.TOUTIAO,ToutiaoAwardStrategyService::new);
    }

    private static final Map<AwardEnum,AwardStrategy> STRATEGY_CACHE = new ConcurrentHashMap<>();
    public static AwardStrategy getStrategy(AwardEnum awardType) {
        return STRATEGY_CACHE.computeIfAbsent(awardType, k -> STRATEGY_SUPPLIERS.get(k).get());
    }
    /**
     * 对外暴露的工厂方法
     * @param userId
     * @param source
     * @return
     */
    public Boolean getAwardResult(String userId, AwardEnum source) {
        try {
            Constructor<? extends AwardStrategy> awardStrategy = source.getDataClass().getDeclaredConstructor();
            AwardStrategy strategy = awardStrategy.newInstance();
//            if (Objects.isNull(awardStrategy)) {
//                throw new RuntimeException("渠道异常");
//            }
            return strategy.awardStrategy(userId);
        }catch (Exception e){
            throw new RuntimeException("渠道异常");
        }
    }

    /**
     * 静态方法内部创建单例工厂对象
     */
    private static class CreateFactorySingleton{
        private static AwardStrategyFactory factory = new AwardStrategyFactory();

    }

    public static AwardStrategyFactory getInstance(){
        return CreateFactorySingleton.factory;
    }

}
