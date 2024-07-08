import com.google.common.escape.Escapers;
import com.example.concurrent.synchronizeds.SynchronizedMethods;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class SynchronizedTest {
    @Test
    public void syncTest1(){
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        SynchronizedMethods synchronizedMethods = new SynchronizedMethods();
        IntStream.range(0,1000).forEach(
                count -> executorService.submit(synchronizedMethods::calculate)
        );
        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertEquals(1000, synchronizedMethods.getSum());
    }
    @Test
    public void syncTest2(){
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        IntStream.range(0, 1000).forEach(
                count -> executorService.submit(SynchronizedMethods::syncStaticCalculate)
        );
        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        assertEquals(1000, SynchronizedMethods.getStaticSum());
    }
    @Test
    public void syncTest3(){
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        SynchronizedMethods synchronizedMethods = new SynchronizedMethods();
        IntStream.range(0, 1000).forEach(
                count ->{
                    executorService.submit(synchronizedMethods::performSynchronisedTask);
                }
        );
        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        assertEquals(1000, synchronizedMethods.getSum());
    }
}
