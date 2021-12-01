import java.util.ArrayList;
import java.util.List;

/**
 * @author lh0
 * @date 2021/11/17
 * @desc
 */
public class OOMTest {

    public static void main(String[] args) {
        List<byte[]> list = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            // 每次增加一个1M大小的数组对象
            list.add(new byte[1024 * 1024]);
        }
    }

}
