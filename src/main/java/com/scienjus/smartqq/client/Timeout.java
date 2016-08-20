package com.scienjus.smartqq.client;

import java.util.concurrent.TimeUnit;

/**
 * @author Xianguang Zhou <xianguang.zhou@outlook.com>
 * @date 2016/08/02.
 */
public class Timeout {
    private long time;
    private TimeUnit unit;

    public Timeout() {
    }

    public Timeout(long time, TimeUnit unit) {
        this.time = time;
        this.unit = unit;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public void setUnit(TimeUnit unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Timeout{");
        sb.append("time=").append(time);
        sb.append(", unit=").append(unit);
        sb.append('}');
        return sb.toString();
    }
    
    public long toTime(TimeUnit timeUnit){
    	return timeUnit.convert(this.time, this.unit);
    }

}
