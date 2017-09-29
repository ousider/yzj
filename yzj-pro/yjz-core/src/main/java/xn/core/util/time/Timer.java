package xn.core.util.time;

/**
 * @Description: 时间基类
 * @author Zhangjc
 * @date 2016年4月15日 下午3:49:41
 */
public class Timer {

    private long start;

    public Timer() {
        start = System.nanoTime();
    }

    public long getUseTime() {
        return System.nanoTime() - start;
    }

    public long getUseTimeInMillis() {
        return (System.nanoTime() - start) / 1000000L;
    }

    public long reset() {
        long end = System.nanoTime();
        long use = end - start;
        start = end;
        return use;
    }

    public long resetInMillis() {
        return reset() / 1000000L;
    }

}
