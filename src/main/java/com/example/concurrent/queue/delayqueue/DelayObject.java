package com.example.concurrent.queue.delayqueue;

import com.google.common.primitives.Ints;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
public class DelayObject implements Delayed {
    private String data;
    private long startTime;
    public DelayObject(){}

    public DelayObject(String data, long startTime) {
        this.data = data;
        this.startTime = System.currentTimeMillis()+startTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long millis = startTime - System.currentTimeMillis();

        return unit.convert(millis,TimeUnit.MICROSECONDS);
    }

    /**
     * 最先过期的保持到队头
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Delayed o) {
        return Ints.saturatedCast(this.startTime- ((DelayObject)o).startTime);
    }

    @Override
    public String toString() {
        return "DelayObject{" +
                "data='" + data + '\'' +
                ", startTime=" + startTime +
                '}';
    }
}

































