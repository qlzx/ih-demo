import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import lombok.Data;

/**
 * @author lh0
 * @date 2021/8/6
 * @desc
 */
public class TestHum {

    public static void main(String[] args) throws IOException {

        char c = '1';
        System.out.println(c-48);

        byte[] buf = new byte[1024];

        //LinkedList list = new LinkedList();

        List<BigObject> list = new ArrayList<>();

        while (System.in.read(buf) != -1) {
            if ("exit".equals(new String(buf))) {
                break;
            }
            System.out.println(new String(buf));
            BigObject bigObject = new BigObject();
            Arrays.fill(bigObject.bytes, (byte) '\0');

            String s = new String("aaa");

            //
            BigObject bigObject2 = new BigObject();
            Arrays.fill(bigObject2.bytes, (byte) '\0');

            //
            list.add(bigObject);
            //
            //BigObject[] bigObjects = new BigObject[2];
            //bigObjects[0] = bigObject;
            //bigObjects[1] = bigObject2;

            System.out.println(bigObject.hashCode());
        }
    }


    @Data
    public static class BigObject{
        byte[] bytes = new byte[1024*1024*1];
    }

}
