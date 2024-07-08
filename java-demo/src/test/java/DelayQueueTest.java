import com.example.concurrent.queue.delayqueue.DelayObject;
import com.example.concurrent.queue.delayqueue.DelayQueueConsumer;
import com.example.concurrent.queue.delayqueue.DelayQueueProducer;
import org.junit.Test;

import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;

public class DelayQueueTest {
    @Test
    public void givenDelayQueue_whenProduceElement_thenShouldConsumeAfterGivenDelay() throws InterruptedException {
        //given
        ExecutorService executor = Executors.newFixedThreadPool(2);
        BlockingQueue<DelayObject> queue = new DelayQueue<>();
        int numberOfElementsToProduce = 5;
        int delayOfEachProducedMessageMilliseconds = 1000;
        DelayQueueConsumer consumer = new DelayQueueConsumer(queue, numberOfElementsToProduce);
        DelayQueueProducer producer
                = new DelayQueueProducer(queue, numberOfElementsToProduce, delayOfEachProducedMessageMilliseconds);

        //when
        executor.submit(producer);
        executor.submit(consumer);

        //then
        executor.awaitTermination(10, TimeUnit.SECONDS);
        executor.shutdown();
        assertEquals(consumer.numberOfConsumeElements.get(), numberOfElementsToProduce);

    }
}
