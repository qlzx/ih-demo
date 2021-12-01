/**
 * @author lh0
 * @date 2021/8/24
 * @desc
 */
public class StackoverflowTest {

    private static int stackLength = 1;

    public static void main(String[] args) {
        //stackLeak();

        for (int i = 0; i < 10240000; i++) {
            byte[] bytes = new byte[10];
            System.out.println(bytes);
        }
    }

    private static void stackLeak() {
        try {
            stackLength++;
            stackLeak();
        } catch (StackOverflowError e) {
            System.out.println("stack Length:" + stackLength);
            e.printStackTrace();
        }
    }
}
