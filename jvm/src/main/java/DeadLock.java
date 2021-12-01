import lombok.SneakyThrows;

/**
 * @author lh0
 * @date 2021/11/17
 * @desc
 */
public class DeadLock {

    private static final Foo a = new Foo("a");

    private static final Foo b = new Foo("b");



    public static void main(String[] args) {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                a.call(b);
            }
        }).start();

        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                b.call(a);
            }
        }).start();
    }


    static class Foo{
        private String name;

        public Foo(String name){
            this.name = name;
        }
        public synchronized void call(Foo other) throws InterruptedException {
            System.out.println(name + "lock");
            Thread.sleep(100);
            synchronized (other) {
                System.out.println("other lock");
            }
        }
    }

}
