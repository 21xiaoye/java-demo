import com.example.design_patterns.strategy.AwardEnum;
import com.example.design_patterns.strategy.AwardStrategy;
import com.example.design_patterns.strategy.AwardStrategyFactory;
import org.junit.Test;

public class designPatterns {
    @Test
    public void StrategyTest(){
        String type = "wx";
        AwardStrategyFactory.getInstance().getAwardResult("1", AwardEnum.of(type));
        String type1="toutiao";
        AwardStrategy strategy = AwardStrategyFactory.getStrategy(AwardEnum.of(type1));
        strategy.awardStrategy("1");
    }
}
