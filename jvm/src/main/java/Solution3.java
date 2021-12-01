import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author lh0
 * @date 2021/11/23
 * @desc
 */
public class Solution3 {

    public long delayPeriod;
    private ArrayBlockingQueue<Object> queue ;

    /**
     * 上一次pull时间
     */
    private long lastPullTime;

    /**
     * pull状态
     */
    private static final AtomicBoolean PULLING = new AtomicBoolean(false);

    /**
     * 消费累积
     *
     * @param data
     */
    public void push(Object data) {
        while (true) {
            // 如果正在pull 那么先等待
            if(PULLING.get()){
                continue;
            }
            while (!queue.offer(data)) {
                // 如果队列满了 则pull出来进行消费
                pullAll();
            }
            break;
        }
    }

    /**
     * 消费累积
     *
     * @param data
     */
    public void push(List<Object> data) {
        data.forEach(this::push);
    }

    private void pullAll() {
        try {
            while (PULLING.compareAndSet(false, true)) {
                lastPullTime = System.currentTimeMillis();
                Object data = null;
                while ((data = queue.poll()) != null) {
                    // consume
                    System.out.println(data);
                }
            }
        } finally {
            PULLING.set(false);
        }
    }

    public void start() {
        // 每隔一秒检测上次pull的时间
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                long current = System.currentTimeMillis();
                if (lastPullTime == 0 || current - lastPullTime >= delayPeriod) {
                    pullAll();
                }
            }
        }, 0, delayPeriod);
    }

    public Solution3(long delayMills,int queueSize){
        this.queue = new ArrayBlockingQueue<>(queueSize);
        this.delayPeriod = delayMills;
        this.start();
    }

    public static void main(String[] args) {
        Solution3 solution3 = new Solution3(1000, 100);
        solution3.push(new Object());
        System.out.println("push success");
    }
}
